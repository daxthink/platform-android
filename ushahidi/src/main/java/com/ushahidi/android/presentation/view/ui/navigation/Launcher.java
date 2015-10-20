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

package com.ushahidi.android.presentation.view.ui.navigation;

import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.view.ui.activity.AboutActivity;
import com.ushahidi.android.presentation.view.ui.activity.AddDeploymentActivity;
import com.ushahidi.android.presentation.view.ui.activity.AddPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.DetailPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.FeedbackActivity;
import com.ushahidi.android.presentation.view.ui.activity.ListDeploymentActivity;
import com.ushahidi.android.presentation.view.ui.activity.LoginActivity;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;
import com.ushahidi.android.presentation.view.ui.activity.QrcodeReaderActivity;
import com.ushahidi.android.presentation.view.ui.activity.SearchPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.UpdateDeploymentActivity;

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

    /**
     * Displays a logout dialog where the user can choose to logout or cancel the action
     */
    public void launchLogout() {
        ((PostActivity) mActivity).launchLogout();
    }

    /**
     * Launches details post
     *
     * @param postId    The post id to be passed to the post details activity
     * @param postTitle The post title to be passed to the post details activity
     */
    public void launchDetailPost(Long postId, String postTitle) {
        mActivity.startActivity(DetailPostActivity.getIntent(mActivity, postId, postTitle));
    }

    /**
     * Launches form add
     *
     * @param formId    form Id
     * @param formTitle form title
     */
    public void launchAddPost(Long formId, String formTitle) {
        mActivity.startActivity(
                AddPostActivity.getIntent(mActivity, formId, formTitle));
    }

    /**
     * Launches search posts
     */
    public void launchSearchPost() {
        mActivity.startActivity(SearchPostActivity.getIntent(mActivity));
    }
}
