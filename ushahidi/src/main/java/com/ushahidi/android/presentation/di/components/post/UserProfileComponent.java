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

package com.ushahidi.android.presentation.di.components.post;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.di.components.AppActivityComponent;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;
import com.ushahidi.android.presentation.view.ui.fragment.UserProfileFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components to {@link UserProfileFragment} and the host
 * activity
 * {@link PostActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface UserProfileComponent extends AppActivityComponent {

    /**
     * Injects {@link PostActivity}
     *
     * @param postActivity The post activity
     */
    void inject(PostActivity postActivity);

    /**
     * Injects {@link UserProfileFragment}
     *
     * @param userProfileFragment The user profile fragment
     */
    void inject(UserProfileFragment userProfileFragment);
}
