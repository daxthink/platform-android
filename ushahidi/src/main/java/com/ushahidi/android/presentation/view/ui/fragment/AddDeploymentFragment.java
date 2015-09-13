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

package com.ushahidi.android.presentation.view.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.R;
import com.ushahidi.android.data.api.service.SiteConfigAPI;
import com.ushahidi.android.domain.entity.Config;
import com.ushahidi.android.presentation.di.components.deployment.AddDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.presenter.deployment.AddDeploymentPresenter;
import com.ushahidi.android.presentation.validator.UrlValidator;
import com.ushahidi.android.presentation.view.deployment.AddDeploymentView;
import com.ushahidi.android.presentation.view.ui.navigation.Launcher;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Fragment for adding a new deployment
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddDeploymentFragment extends BaseFragment implements AddDeploymentView {

    @Bind(R.id.add_deployment_url)
    EditText url;

    ProgressDialog mProgressDialog;

    @Inject
    AddDeploymentPresenter mAddDeploymentPresenter;

    @Inject
    Launcher mLauncher;

    /**
     * Add Deployment  Fragment
     */
    public AddDeploymentFragment() {
        super(R.layout.fragment_add_deployment, R.menu.add_deployment);
    }

    public static AddDeploymentFragment newInstance() {
        AddDeploymentFragment addDeploymentFragment = new AddDeploymentFragment();
        return addDeploymentFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        url.setOnTouchListener((view, event) -> setHttpProtocol());
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
        mAddDeploymentPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAddDeploymentPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddDeploymentPresenter.destroy();
    }

    private void initialize() {
        getComponent(AddDeploymentComponent.class).inject(this);
        mAddDeploymentPresenter.setView(this);
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplication();
    }

    @Override
    public void showError(String message) {
        showToast(message);
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
        // Make an api call to <url>api/v3/config/site to get the deployment info and use
        // the title from the response data
        showLoading();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url.getText().toString()).build();
        SiteConfigAPI siteConfigApi = restAdapter.create(SiteConfigAPI.class);
        siteConfigApi.getConfig(mSiteConfigCallback);
    }

    private Callback<Config> mSiteConfigCallback = new Callback<Config>() {

        @Override
        public void success(Config config, Response response) {
            DeploymentModel deploymentModel = new DeploymentModel();
            deploymentModel.setTitle(config.getName());
            deploymentModel.setUrl(url.getText().toString());
            mAddDeploymentPresenter.addDeployment(deploymentModel);
            hideLoading();
        }

        @Override
        public void failure(RetrofitError error) {
            hideLoading();
            url.setError(getString(R.string.validation_message_invalid_url));
        }

    };

    @OnClick(R.id.add_deployment_cancel)
    public void onClickCancel() {
        getActivity().finish();
    }

    @OnClick(R.id.qr_code_scanner)
    public void onQrCodeScannerClick() {
        mLauncher.launchQrcodeReader();
    }

    public void setDeployment(@NonNull DeploymentModel deploymentModel) {
        url.setText(deploymentModel.getUrl());
    }

    @Override
    public void onDeploymentSuccessfullyAdded(Long row) {
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(getString(R.string.site_config_retrieve_progress));
        }
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showRetry() {
        // Do nothing
    }

    @Override
    public void hideRetry() {
        // Do nothing
    }

}