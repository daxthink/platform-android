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

package com.ushahidi.android.presentation.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.AddDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.presenter.AddDeploymentPresenter;
import com.ushahidi.android.presentation.ui.view.AddDeploymentView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Fragment for adding a new deployment
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddDeploymentFragment extends BaseFragment implements AddDeploymentView {

    @InjectView(R.id.add_deployment_title)
    EditText title;

    @InjectView(R.id.add_deployment_url)
    EditText url;

    private AddDeploymentListener mActionListener;

    @Inject
    AddDeploymentPresenter mAddDeploymentPresenter;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddDeploymentListener) {
            this.mActionListener = (AddDeploymentListener) activity;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        url.setOnTouchListener((view, event) -> {
            return setHttpProtocol();
        });
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

    @Override
    public void onDetach() {
        super.onDetach();
        mActionListener = null;
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
        DeploymentModel deploymentModel = new DeploymentModel();
        deploymentModel.setTitle(title.getText().toString());
        deploymentModel.setUrl(url.getText().toString());
        mAddDeploymentPresenter.addDeployment(deploymentModel);

    }

    @OnClick(R.id.add_deployment_cancel)
    public void onClickCancel() {
        if (mActionListener != null) {
            mActionListener.onCancelAdd();
        }
    }

    @Override
    public void onDeploymentSuccessfullyAdded(Long row) {
        if (mActionListener != null) {
            mActionListener.onAddNavigateOrReloadList();
        }
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
     * Listens for Add Deployment events
     */
    public interface AddDeploymentListener {

        /**
         * Executes when a button is pressed to either navigate away from the screen or reload an
         * existing list.
         */
        void onAddNavigateOrReloadList();

        /**
         * Executes when a button is pressed to either cancel.
         */
        void onCancelAdd();
    }
}
