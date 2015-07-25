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
import com.ushahidi.android.presentation.ui.activity.AboutActivity;
import com.ushahidi.android.presentation.ui.activity.AddDeploymentActivity;
import com.ushahidi.android.presentation.ui.activity.DetailPostActivity;
import com.ushahidi.android.presentation.ui.activity.FeedbackActivity;
import com.ushahidi.android.presentation.ui.activity.ListDeploymentActivity;
import com.ushahidi.android.presentation.ui.activity.LoginActivity;
import com.ushahidi.android.presentation.ui.activity.QrcodeReaderActivity;
import com.ushahidi.android.presentation.ui.activity.UpdateDeploymentActivity;

import android.app.Activity;

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
        mActivity.startActivity(AddDeploymentActivity.getIntent(mActivity));
    }

    public void launchListDeployment() {
        mActivity.startActivity(ListDeploymentActivity.getIntent(mActivity));
    }

    /**
     * Launches update deployment activity for editing
     *
     * @param deploymentModel The deployment model to be edited
     */
    public void launchUpdateDeployment(DeploymentModel deploymentModel) {
        mActivity.startActivity(UpdateDeploymentActivity.getIntent(mActivity, deploymentModel));
    }

    /**
     * Launches the barcode reader
     */
    public void launchQrcodeReader() {
        mActivity.startActivityForResult(QrcodeReaderActivity.getIntent(mActivity),
                QrcodeReaderActivity.QRCODE_READER_REQUEST_CODE);
    }

    /**
     * Launches login activity
     */
    public void launchLogin() {
        mActivity.startActivity(LoginActivity.getIntent(mActivity));
    }

    /**
     * Launches about activity
     */
    public void launchAbout() {
        mActivity.startActivity(AboutActivity.getIntent(mActivity));
    }

    /**
     * Launches feedback activity
     */
    public void launchFeedback() {
        mActivity.startActivity(FeedbackActivity.getIntent(mActivity));
    }

    public void launchDetailPost(Long postId, String postTitle) {
        mActivity.startActivity(DetailPostActivity.getIntent(mActivity, postId, postTitle));
    }
}
