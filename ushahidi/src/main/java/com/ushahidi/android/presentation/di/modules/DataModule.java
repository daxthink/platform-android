/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.di.modules;

import com.addhen.android.raiburari.data.pref.RxSharedPreferences;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.ushahidi.android.presentation.net.HttpClientWrap;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.client.OkClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Data related modules
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class DataModule {

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private static OkHttpClient createOkHttpClient(Context app) {
        OkHttpClient client = new OkHttpClient();

        File cacheDir = new File(app.getApplicationContext().getCacheDir(),
                "ushahidi-android-http-cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);
        return client;
    }

    @Provides
    @Singleton
    HttpClientWrap provideOkHttpClient(Context app) {
        final OkHttpClient okHttpClient = createOkHttpClient(app.getApplicationContext());
        okHttpClient.setCookieHandler(CookieHandler.getDefault());
        okHttpClient.setConnectTimeout(10, TimeUnit.MINUTES);
        return new HttpClientWrap(app, new OkClient(okHttpClient));
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("ushahidi-android-shared-prefs",
                MODE_PRIVATE);
    }

    @Provides
    @Singleton
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences sharedPreferences) {
        return new RxSharedPreferences(sharedPreferences);
    }
}
