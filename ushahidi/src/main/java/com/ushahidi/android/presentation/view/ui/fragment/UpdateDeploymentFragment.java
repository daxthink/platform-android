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

package com.ushahidi.android.presentation.view.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.addhen.android.raiburari.presentation.ui.widget.FontSupportedTextView;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.UpdateDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.presenter.deployment.UpdateDeploymentPresenter;
import com.ushahidi.android.presentation.validator.UrlValidator;
import com.ushahidi.android.presentation.view.deployment.UpdateDeploymentView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Fragment for updating a existing deployment
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UpdateDeploymentFragment extends BaseFragment implements UpdateDeploymentView {

    private static final String ARGUMENT_KEY_DEPLOYMENT_MODEL
            = "com.ushahidi.android.ARGUMENT_DEPLOYMENT_MODEL";

    @Bind(R.id.add_deployment_url)
    EditText url;

    @Bind(R.id.textview_deployment_description)
    FontSupportedTextView mHeader;

    @Inject
    UpdateDeploymentPresenter mUpdateDeploymentPresenter;

    private UpdateDeploymentListener mActionListener;

    private DeploymentModel mDeploymentModel;

    /**
     * Update Deployment  Fragment
     */
    public UpdateDeploymentFragment() {
        super(R.layout.fragment_update_deployment, R.menu.add_deployment);
    }

    public static UpdateDeploymentFragment newInstance(DeploymentModel deployment) {
        UpdateDeploymentFragment updateDeploymentFragment = new UpdateDeploymentFragment();
        Bundle argumentBundle = new Bundle();
        argumentBundle.putParcelable(ARGUMENT_KEY_DEPLOYMENT_MODEL, deployment);
        updateDeploymentFragment.setArguments(argumentBundle);
        return updateDeploymentFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof UpdateDeploymentListener) {
            mActionListener = (UpdateDeploymentListener) activity;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        url.setOnTouchListener((view, event) -> setHttpProtocol());
        mHeader.setText(R.string.update_deployment_description_hint);
    }

    private boolean setHttpProtocol() {
        if (TextUtils.isEmpty(url.getText().toString())) {
            url.setText("http://");
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUpdateDeploymentPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUpdateDeploymentPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUpdateDeploymentPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActionListener = null;
    }

    private void initialize() {
        getComponent(UpdateDeploymentComponent.class).inject(this);
        mUpdateDeploymentPresenter.setView(this);
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplication();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @OnClick(R.id.add_deployment_cancel)
    public void onClickCancel() {
        if (mActionListener != null) {
            mActionListener.onCancelUpdate();
        }
    }

    @Override
    public void onDeploymentSuccessfullyUpdated(Long row) {
        if (mActionListener != null) {
            mActionListener.onUpdateNavigateOrReloadList();
        }
    }

    @Override
    public void showDeployment(@NonNull DeploymentModel deploymentModel) {
        mDeploymentModel = deploymentModel;
        url.setText(deploymentModel.getUrl());
    }

    @OnClick(R.id.add_deployment_add)
    public void onClickValidate() {
        submit();
    }

    @OnEditorAction(R.id.add_deployment_url)
    boolean onEditorAction(TextView textView, int actionId) {
        if (textView == url) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    submit();
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private void submit() {
        url.setError(null);
        if (!(new UrlValidator().isValid(url.getText().toString()))) {
            url.setError(getString(R.string.validation_message_invalid_url));
            return;
        }
        mDeploymentModel.setUrl(url.getText().toString());
        mUpdateDeploymentPresenter.updateDeployment(mDeploymentModel);
    }

    @Override
    public void showLoading() {
        // Do nothing
    }

    @Override
    public void hideLoading() {
        // Do nothing
    }

    @Override
    public void showRetry() {
        // Do nothing
    }

    @Override
    public void hideRetry() {
        // Do nothing
    }

    /**
     * Listens for Update Deployment events
     */
    public interface UpdateDeploymentListener {

        /**
         * Executes when a button is pressed to either navigate away from the screen or reload an
         * existing list.
         */
        void onUpdateNavigateOrReloadList();

        /**
         * Executes when a button is pressed to either cancel.
         */
        void onCancelUpdate();
    }
}
