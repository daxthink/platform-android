package com.ushahidi.android.data.pref;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * RxJava based application {@link android.content.SharedPreferences}
 *
 * @author Henry Addo
 */
public class RxSharedPreferences {

    public static RxSharedPreferences create(SharedPreferences sharedPreferences) {
        return new RxSharedPreferences(sharedPreferences);
    }

    private final SharedPreferences mSharedPreferences;

    private final Observable<String> mChangedKeys;

    @Inject
    public RxSharedPreferences(@NonNull SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        this.mChangedKeys = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                final SharedPreferences.OnSharedPreferenceChangeListener listener
                        = new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                            String key) {
                        subscriber.onNext(key);
                    }
                };

                Subscription subscription = Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
                    }
                });
                subscriber.add(subscription);

                sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
            }
        }).share();
    }

    public Observable<String> getString(String key) {
        return getString(key, null);
    }

    public Observable<String> getString(String key, final String defaultValue) {
        return mChangedKeys.filter(matchesKey(key))
                .startWith(key)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String changedKey) {
                        return mSharedPreferences.getString(changedKey, defaultValue);
                    }
                });
    }

    public Action1<String> setString(final String key) {
        return new Action1<String>() {
            @Override
            public void call(String value) {
                mSharedPreferences.edit().putString(key, value).apply();
            }
        };
    }

    public Observable<Boolean> getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Observable<Boolean> getBoolean(String key, final Boolean defaultValue) {
        return mChangedKeys.filter(matchesKey(key))
                .startWith(key)
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String changedKey) {
                        return mSharedPreferences.getBoolean(changedKey, defaultValue);
                    }
                });
    }

    public Action1<Boolean> setBoolean(final String key) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean value) {
                mSharedPreferences.edit().putBoolean(key, value).apply();
            }
        };
    }

    public Observable<Integer> getInt(String key) {
        return getInt(key, null);
    }

    public Observable<Integer> getInt(String key, final Integer defaultValue) {
        return mChangedKeys.filter(matchesKey(key))
                .startWith(key)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String changedKey) {
                        return mSharedPreferences.getInt(changedKey, defaultValue);
                    }
                });
    }

    private static Func1<String, Boolean> matchesKey(final String key) {
        return new Func1<String, Boolean>() {
            @Override
            public Boolean call(String value) {
                return key.equals(value);
            }
        };
    }
}
