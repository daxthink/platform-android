/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.di.modules;

import com.addhen.android.raiburari.data.pref.RxSharedPreferences;
import com.addhen.android.raiburari.presentation.di.module.ApplicationModule;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.data.api.Constant;
import com.ushahidi.android.data.api.PlatformAuthConfig;
import com.ushahidi.android.data.api.PlatformService;
import com.ushahidi.android.data.api.account.PersistedSessionManager;
import com.ushahidi.android.data.api.account.PlatformSession;
import com.ushahidi.android.data.api.account.Session;
import com.ushahidi.android.data.api.account.SessionManager;
import com.ushahidi.android.data.repository.DeploymentDataRepository;
import com.ushahidi.android.data.repository.GeoJsonDataRepository;
import com.ushahidi.android.data.repository.PostDataRepository;
import com.ushahidi.android.data.repository.UserAccountDataRepository;
import com.ushahidi.android.data.repository.UserProfileDataRepository;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import com.ushahidi.android.domain.repository.GeoJsonRepository;
import com.ushahidi.android.domain.repository.PostRepository;
import com.ushahidi.android.domain.repository.UserAccountRepository;
import com.ushahidi.android.domain.repository.UserProfileRepository;
import com.ushahidi.android.presentation.exception.UnauthorizedAccessErrorHandler;
import com.ushahidi.android.presentation.net.HttpClientWrap;
import com.ushahidi.android.presentation.state.AppState;
import com.ushahidi.android.presentation.state.UserState;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.io.File;
import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.client.OkClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Reusable Dagger modules for the entire app
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module(includes = {ApplicationModule.class})
public class AppModule {

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    static final String PREF_KEY_ACTIVE_PLATFORM_SESSION = "active_platform_session";

    private static final String PREF_KEY_PLATFORM_SESSION = "platform_session";

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
    PlatformAuthConfig providePlatformAuthConfig() {
        // TODO: Get these values from build script
        return new PlatformAuthConfig(Constant.OAUTH_CLIENT_ID, Constant.OAUTH_CLIENT_SECRET,
                Constant.SCOPE);
    }

    @Provides
    @Singleton
    SessionManager<PlatformSession> providePlatformSessionManager(
            SharedPreferences sharedPreferences) {
        SessionManager<PlatformSession> sessionSessionManager = new PersistedSessionManager<>(
                sharedPreferences, new PlatformSession.Serializer(),
                PREF_KEY_ACTIVE_PLATFORM_SESSION,
                PREF_KEY_PLATFORM_SESSION);

        return sessionSessionManager;
    }

    @Provides
    @Nullable
    @Singleton
    Session provideSession(SessionManager<PlatformSession> sessionManager) {
        return sessionManager.getActiveSession();
    }

    @Provides
    @Singleton
    HttpClientWrap provideOkHttpClient(Context app, @Nullable Session session) {
        final OkHttpClient okHttpClient = createOkHttpClient(app.getApplicationContext());
        okHttpClient.setCookieHandler(CookieHandler.getDefault());
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        return new HttpClientWrap(session, app, new OkClient(okHttpClient));
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

    @Provides
    @Singleton
    DeploymentRepository provideDeploymentRepository(
            DeploymentDataRepository deploymentDataRepository) {
        return deploymentDataRepository;
    }

    @Provides
    @Singleton
    PostRepository providePostRepository(
            PostDataRepository postDataRepository) {
        return postDataRepository;
    }

    @Provides
    @Singleton
    GeoJsonRepository provideGeoJsonRepository(
            GeoJsonDataRepository geoJsonDataRepository
    ) {
        return geoJsonDataRepository;
    }

    @Provides
    @Singleton
    UserAccountRepository providesUserAccountRepository(
            UserAccountDataRepository userAccountRepository) {
        return userAccountRepository;
    }

    @Provides
    @Singleton
    UserProfileRepository provideUserRepository(
            UserProfileDataRepository userProfileDataRepository) {
        return userProfileDataRepository;
    }

    @Provides
    @Singleton
    public AppState provideApplicationState(Bus bus) {
        return new AppState(bus);
    }

    @Provides
    @Singleton
    UserState provideUserState(AppState appState) {
        return appState;
    }

    @Provides
    @Singleton
    PlatformService provideApiServiceFactory(HttpClientWrap httWrap,
            UnauthorizedAccessErrorHandler handler, PrefsFactory prefsFactory
    ) {
        return new PlatformService(httWrap, handler, prefsFactory);
    }
}
