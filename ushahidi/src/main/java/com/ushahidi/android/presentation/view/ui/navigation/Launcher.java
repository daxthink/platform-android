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

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.ushahidi.android.R;
import com.ushahidi.android.data.api.account.SessionManager;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.model.FormModel;
import com.ushahidi.android.presentation.state.LoadUserProfileEvent;
import com.ushahidi.android.presentation.view.ui.activity.AboutActivity;
import com.ushahidi.android.presentation.view.ui.activity.AddDeploymentActivity;
import com.ushahidi.android.presentation.view.ui.activity.AddPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.BaseAppActivity;
import com.ushahidi.android.presentation.view.ui.activity.DetailPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.FeedbackActivity;
import com.ushahidi.android.presentation.view.ui.activity.ListDeploymentActivity;
import com.ushahidi.android.presentation.view.ui.activity.LoginActivity;
import com.ushahidi.android.presentation.view.ui.activity.QrcodeReaderActivity;
import com.ushahidi.android.presentation.view.ui.activity.SearchPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.UpdateDeploymentActivity;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(R.string.dialog_logout_message)
                .setCancelable(false)
                .setIcon(R.drawable.ic_action_globe)
                .setPositiveButton(R.string.dialog_logout_btn_postive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Clear active session
                        SessionManager sessionManager =
                                ((BaseAppActivity) mActivity).getAppComponent().platformSessionManager();
                        sessionManager.clearActiveSession();
                        // Clear access token
                        UshAccessTokenManager ushAccessTokenManager =
                                ((BaseAppActivity) mActivity).getAppComponent().ushahidiTokenManager();
                        ushAccessTokenManager.getStorage().removeAccessToken();
                        // Send an event
                        UshahidiApplication.getRxEventBusInstance()
                                .send(new LoadUserProfileEvent(null));
                    }
                })
                .setNegativeButton(R.string.dialog_logout_btn_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
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
     * Launches add post
     *
     * @param formModel The selected form model
     */
    public void launchAddPost(FormModel formModel) {
        mActivity.startActivity(AddPostActivity.getIntent(mActivity, formModel));
    }

    /**
     * Launches search posts
     */
    public void launchSearchPost() {
        mActivity.startActivity(SearchPostActivity.getIntent(mActivity));
    }
}
