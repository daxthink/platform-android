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

package com.ushahidi.android.presentation.di.components.deployment;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.di.components.AppActivityComponent;
import com.ushahidi.android.presentation.di.modules.deployment.UpdateDeploymentModule;
import com.ushahidi.android.presentation.presenter.deployment.UpdateDeploymentPresenter;
import com.ushahidi.android.presentation.ui.activity.UpdateDeploymentActivity;
import com.ushahidi.android.presentation.ui.fragment.UpdateDeploymentFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components to {@link UpdateDeploymentFragment} and the host
 * activity {@link UpdateDeploymentActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class,
        UpdateDeploymentModule.class})
public interface UpdateDeploymentComponent extends AppActivityComponent {

    /**
     * Injects {@link UpdateDeploymentActivity}
     *
     * @param updateDeploymentActivity The update deployment activity
     */
    void inject(UpdateDeploymentActivity updateDeploymentActivity);

    /**
     * Injects {@link UpdateDeploymentFragment}
     *
     * @param updateDeploymentFragment The update deployment fragment
     */
    void inject(UpdateDeploymentFragment updateDeploymentFragment);

    /**
     * Provides {@link UpdateDeploymentPresenter} to the sub-graph
     *
     * @return The update deployment presenter
     */
    UpdateDeploymentPresenter updateDeploymentPresenter();
}
