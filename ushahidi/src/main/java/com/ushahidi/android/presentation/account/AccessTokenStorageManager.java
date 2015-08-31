package com.ushahidi.android.presentation.account;

import com.google.gson.Gson;

import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.state.NoAccessTokenEvent;

import android.content.SharedPreferences;

import java.util.Calendar;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import de.rheinfabrik.heimdall.OAuth2AccessTokenStorage;
import rx.Observable;

/**
 * A simple storage that saves the access token as plain text in the passed shared preferences.
 * It is recommend to set the access mode to MODE_PRIVATE. It uses {@link Gson} as the
 * serialization
 * / deserialization class.
 *
 * @param <T> The access token type.
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AccessTokenStorageManager<T extends OAuth2AccessToken> implements
        OAuth2AccessTokenStorage<T> {
    // Constants

    private static final String ACCESS_TOKEN_PREFERENCES_KEY = "OAuth2AccessToken";

    // Members

    private final SharedPreferences mSharedPreferences;

    private final Class mTokenClass;

    // Constructor

    /**
     * Designated constructor.
     *
     * @param sharedPreferences The shared preferences used for saving the access token.
     * @param tokenClass        The actual class of the access token.
     */
    public AccessTokenStorageManager(SharedPreferences sharedPreferences, Class tokenClass) {
        super();

        if (tokenClass == null) {
            throw new RuntimeException("TokenClass MUST NOT be null.");
        }

        if (sharedPreferences == null) {
            throw new RuntimeException("SharedPreferences MUST NOT be null.");
        }

        mTokenClass = tokenClass;
        mSharedPreferences = sharedPreferences;
    }

    // OAuth2AccessTokenStorage

    @SuppressWarnings("unchecked")
    @Override
    public Observable<T> getStoredAccessToken() {
        return Observable
                .just(mSharedPreferences.getString(ACCESS_TOKEN_PREFERENCES_KEY, null))
                .filter(accessToken -> {
                    if (accessToken == null) {
                        // Send an event that there is no valid access token
                        UshahidiApplication.getRxEventBusInstance().send(new NoAccessTokenEvent());
                    }
                    return true;
                })
                .map(json -> (T) new Gson().fromJson(json, mTokenClass));
    }

    @Override
    public void storeAccessToken(T accessToken) {
        setExpiringDate(accessToken);
        mSharedPreferences.edit().putString(ACCESS_TOKEN_PREFERENCES_KEY,
                new Gson().toJson(accessToken)).apply();
    }

    @Override
    public Observable<Boolean> hasAccessToken() {
        return Observable.just(mSharedPreferences.contains(ACCESS_TOKEN_PREFERENCES_KEY));
    }

    @Override
    public void removeAccessToken() {
        mSharedPreferences.edit().remove(ACCESS_TOKEN_PREFERENCES_KEY).apply();
    }

    private void setExpiringDate(T accessToken) {
        if (accessToken.expiresIn != null) {
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.add(Calendar.SECOND, accessToken.expiresIn);
            accessToken.expirationDate = expirationDate;
        }
    }
}
