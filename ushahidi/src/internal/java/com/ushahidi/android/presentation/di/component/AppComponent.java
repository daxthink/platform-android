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

package com.ushahidi.android.presentation.di.component;

import com.addhen.android.raiburari.presentation.di.component.ApplicationComponent;
import com.addhen.android.raiburari.presentation.di.module.ApplicationModule;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.data.api.PlatformService;
import com.ushahidi.android.data.api.account.PlatformSession;
import com.ushahidi.android.data.api.account.SessionManager;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import com.ushahidi.android.domain.repository.GeoJsonRepository;
import com.ushahidi.android.domain.repository.PostRepository;
import com.ushahidi.android.domain.repository.UserAccountRepository;
import com.ushahidi.android.domain.repository.UserProfileRepository;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.modules.AppModule;
import com.ushahidi.android.presentation.net.HttpClientWrap;
import com.ushahidi.android.presentation.state.AppState;
import com.ushahidi.android.presentation.state.UserState;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Provides Application specific dependencies including
 * all internal releases specific dependencies.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent extends ApplicationComponent {

    // Provide these to the sub-graph

    /**
     * Provide {@link HttpClientWrap} to the sub-graph
     *
     * @return The Http client
     */
    HttpClientWrap httpClientWrap();

    /**
     * Provide @{@link SessionManager} to the sub-graph
     *
     * @return The session manager
     */
    SessionManager<PlatformSession> platformSessionManager();

    /**
     * Provide {@link AppState} to the sub-graph
     *
     * @return The app state
     */
    AppState appState();

    /**
     * Provide {@link UserState} to the sub-graph
     *
     * @return The user state
     */
    UserState userState();

    /**
     * Provide {@link DeploymentRepository} to the sub-graph
     *
     * @return The deployment repository
     */
    DeploymentRepository deploymentRepository();

    /**
     * Provide {@link PostRepository} to the sub-graph
     *
     * @return The post repository
     */
    PostRepository postRepository();

    /**
     * Provide {@link GeoJsonRepository} to the sub-graph
     *
     * @return The geojson repository
     */
    GeoJsonRepository geoJsonRepsitory();

    /**
     * Provide {@link UserAccountRepository} to the sub-graph
     *
     * @return The user account repository
     */
    UserAccountRepository userAccountRepository();

    /**
     * Provide {@link UserProfileRepository} to the sub-graph
     *
     * @return The user profile repository
     */
    UserProfileRepository userProfileRepository();

    /**
     * Provide {@link PrefsFactory} to the sub-graph
     *
     * @return The prefs factory
     */
    PrefsFactory prefsFactory();

    /**
     * Provide {@link PlatformService} to the sub-graph
     *
     * @return The platform service
     */
    PlatformService platformService();

    /**
     * Provide {@link UshAccessTokenManager} to the sub-graph
     *
     * @return the ushahidi access token manager
     */
    UshAccessTokenManager ushahidiTokenManager();

    /**
     * Initializes Application's level dagger component
     */
    final class Initializer {

        private Initializer() {
        } // No instances.

        /**
         * Initializes Application's level dagger component
         *
         * @param app The {@link UshahidiApplication}
         * @return The dagger component
         */
        public static AppComponent init(UshahidiApplication app) {
            return DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .appModule(new AppModule())
                    .build();
        }
    }
}
