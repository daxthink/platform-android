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
import com.ushahidi.android.data.api.ushoauth2.UshAccessTokenManager;
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
    HttpClientWrap httpClientWrap();

    SessionManager<PlatformSession> platformSessionManager();

    AppState appState();

    UserState userState();

    DeploymentRepository deploymentRepository();

    PostRepository postRepository();

    GeoJsonRepository geoJsonRepsitory();

    UserAccountRepository userAccountRepository();

    UserProfileRepository userProfileRepository();

    PrefsFactory prefsFactory();

    PlatformService platformService();

    UshAccessTokenManager ushahidiTokenManager();


    final class Initializer {

        private Initializer() {
        } // No instances.

        public static AppComponent init(UshahidiApplication app) {
            return DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .appModule(new AppModule())
                    .build();
        }
    }
}
