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

package com.ushahidi.android.presentation.ui.navigation;

import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.ui.activity.AddDeploymentActivity;
import com.ushahidi.android.presentation.ui.activity.ListDeploymentActivity;
import com.ushahidi.android.presentation.ui.activity.UpdateDeploymentActivity;

import android.app.Activity;
import android.content.Intent;

import javax.inject.Inject;

/**
 * Launches Activities
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Launcher {

    private final Activity mActivity;

    @Inject
    public Launcher(Activity activity) {
        mActivity = activity;
    }

    public void launchAddDeployment() {
        final Intent intent = AddDeploymentActivity.getIntent(mActivity);
        mActivity.startActivity(intent);
    }

    public void launchListDeployment() {
        Intent intent = ListDeploymentActivity.getIntent(mActivity);
        mActivity.startActivity(intent);
    }

    /**
     * Launches update deployment activity for editing
     *
     * @param deploymentModel The deployment model to be edited
     */
    public void launchUpdateDeployment(DeploymentModel deploymentModel) {
        final Intent intent = UpdateDeploymentActivity.getIntent(mActivity, deploymentModel);
        mActivity.startActivity(intent);
    }
}
