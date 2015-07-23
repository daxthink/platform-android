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
import com.facebook.stetho.okhttp.StethoInterceptor;
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
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
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
import de.rheinfabrik.heimdall.OAuth2AccessToken;
import de.rheinfabrik.heimdall.extras.SharedPreferencesOAuth2AccessTokenStorage;
import retrofit.client.OkClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Dagger modules that provides objects that lives the entire lifecycle of the application
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

    /**
     * Provides {@link PlatformAuthConfig} object
     *
     * @return The platform config object
     */
    @Provides
    @Singleton
    PlatformAuthConfig providePlatformAuthConfig() {
        // TODO: Get these values from build script
        return new PlatformAuthConfig(Constant.OAUTH_CLIENT_ID, Constant.OAUTH_CLIENT_SECRET,
                Constant.SCOPE);
    }

    /**
     * Provides {@link SessionManager} object
     *
     * @param sharedPreferences The sharedPreferences for storing user's session details
     * @return The session manager object
     */
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

    /**
     * Provides {@link Session} object
     *
     * @param sessionManager The session manager
     * @return The session object
     */
    @Provides
    @Nullable
    @Singleton
    Session provideSession(SessionManager<PlatformSession> sessionManager) {
        return sessionManager.getActiveSession();
    }

    /**
     * Provides {@link HttpClientWrap} object
     *
     * @param context The calling context
     * @return The http client wrap object
     */
    @Provides
    @Singleton
    HttpClientWrap provideOkHttpClient(Context context) {
        final OkHttpClient okHttpClient = createOkHttpClient(context.getApplicationContext());
        okHttpClient.setCookieHandler(CookieHandler.getDefault());
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.networkInterceptors().add(new StethoInterceptor());
        return new HttpClientWrap(context, new OkClient(okHttpClient));
    }

    /**
     * Provides {@link SharedPreferences} object
     *
     * @param context The calling context
     * @return The shared preferences
     */
    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("ushahidi-android-shared-prefs",
                MODE_PRIVATE);
    }

    /**
     * Provides {@link RxSharedPreferences} object
     *
     * @param sharedPreferences The RxSharedPreferences
     * @return The shared preferences
     */
    @Provides
    @Singleton
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences sharedPreferences) {
        return new RxSharedPreferences(sharedPreferences);
    }

    /**
     * Provides {@link DeploymentRepository} object
     *
     * @param deploymentDataRepository The deployment data repository which is an implementation of
     *                                 {@link DeploymentRepository}
     * @return the deployment repository
     */
    @Provides
    @Singleton
    DeploymentRepository provideDeploymentRepository(
            DeploymentDataRepository deploymentDataRepository) {
        return deploymentDataRepository;
    }

    /**
     * Provides {@link PostRepository} object
     *
     * @param postDataRepository post data repository
     * @return The post repository
     */
    @Provides
    @Singleton
    PostRepository providePostRepository(
            PostDataRepository postDataRepository) {
        return postDataRepository;
    }

    /**
     * Provides {@link GeoJsonRepository} object
     *
     * @param geoJsonDataRepository The GeoJson data repository
     * @return The GeoJson
     */
    @Provides
    @Singleton
    GeoJsonRepository provideGeoJsonRepository(
            GeoJsonDataRepository geoJsonDataRepository
    ) {
        return geoJsonDataRepository;
    }

    /**
     * Provides {@link UserAccountRepository}
     *
     * @param userAccountRepository The user data repository
     * @return The user account repository
     */
    @Provides
    @Singleton
    UserAccountRepository providesUserAccountRepository(
            UserAccountDataRepository userAccountRepository) {
        return userAccountRepository;
    }

    /**
     * Provides {@link UserProfileRepository}
     *
     * @param userProfileDataRepository The user profile data repository
     * @return The user profile repository
     */
    @Provides
    @Singleton
    UserProfileRepository provideUserRepository(
            UserProfileDataRepository userProfileDataRepository) {
        return userProfileDataRepository;
    }

    /**
     * Provides {@link AppState} object
     *
     * @param bus The bus object
     * @return The constructed app state
     */
    @Provides
    @Singleton
    public AppState provideApplicationState(Bus bus) {
        return new AppState(bus);
    }

    /**
     * Provides {@link UserState} object
     *
     * @param appState The AppState which is an implementation of {@link UserState}
     * @return The user state object
     */
    @Provides
    @Singleton
    UserState provideUserState(AppState appState) {
        return appState;
    }

    /**
     * Provides {@link PlatformService} object
     *
     * @param httWrap      The http client wrap
     * @param handler      The unauthorized access error handler
     * @param prefsFactory The preference factory
     * @return The constructed platform service object
     */
    @Provides
    @Singleton
    PlatformService provideApiServiceFactory(HttpClientWrap httWrap,
            UnauthorizedAccessErrorHandler handler, PrefsFactory prefsFactory
    ) {
        return new PlatformService(httWrap, handler, prefsFactory);
    }

    /**
     * Provides {@link UshAccessTokenManager} object
     *
     * @param context            The calling context
     * @param platformService    The platform service
     * @param platformAuthConfig The platform auth config
     * @return The access token manager
     */
    @Provides
    @Singleton
    UshAccessTokenManager provideUshAccessTokenManager(Context context,
            PlatformService platformService, PlatformAuthConfig platformAuthConfig) {
        // Define the shared preferences where we will save the access token
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "UshahidiAccessTokenStorage", Context.MODE_PRIVATE);
        // Define the storage using the the previously defined preferences
        SharedPreferencesOAuth2AccessTokenStorage<OAuth2AccessToken> tokenStorage
                = new SharedPreferencesOAuth2AccessTokenStorage<>(sharedPreferences,
                OAuth2AccessToken.class);
        return new UshAccessTokenManager(tokenStorage, platformService, platformAuthConfig);
    }
}
