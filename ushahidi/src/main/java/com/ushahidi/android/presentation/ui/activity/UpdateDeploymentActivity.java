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

package com.ushahidi.android.presentation.ui.activity;

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.DaggerUpdateDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.UpdateDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.ui.fragment.UpdateDeploymentFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Renders {@link UpdateDeploymentFragment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UpdateDeploymentActivity extends BaseAppActivity
        implements HasComponent<UpdateDeploymentComponent>,
        UpdateDeploymentFragment.UpdateDeploymentListener {

    private static final String INTENT_EXTRA_PARAM_DEPLOYMENT_MODEL
            = "com.ushahidi.android.INTENT_PARAM_DEPLOYMENT_MODEL";

    private static final String INTENT_STATE_PARAM_DEPLOYMENT
            = "com.ushahidi.android.STATE_PARAM_DEPLOYMENT_MODEL";

    private DeploymentModel mDeploymentModel;

    private UpdateDeploymentComponent mUpdateDeploymentComponent;

    private static final String FRAG_TAG = "update_deployment";

    private UpdateDeploymentFragment mUpdateDeploymentFragment;

    public UpdateDeploymentActivity() {
        super(R.layout.activity_update_deployment, 0);
    }

    public static Intent getIntent(final Context context, DeploymentModel deploymentModel) {
        Intent intent = new Intent(context, UpdateDeploymentActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_DEPLOYMENT_MODEL, deploymentModel);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        if (savedInstanceState == null) {
            mDeploymentModel = getIntent().getParcelableExtra(INTENT_EXTRA_PARAM_DEPLOYMENT_MODEL);
        } else {
            mDeploymentModel = savedInstanceState.getParcelable(INTENT_STATE_PARAM_DEPLOYMENT);
        }
        mUpdateDeploymentFragment = (UpdateDeploymentFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG);
        if (mUpdateDeploymentFragment == null) {
            mUpdateDeploymentFragment = UpdateDeploymentFragment.newInstance(mDeploymentModel);
            replaceFragment(R.id.update_fragment_container, mUpdateDeploymentFragment, FRAG_TAG);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(INTENT_EXTRA_PARAM_DEPLOYMENT_MODEL, mDeploymentModel);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUpdateDeploymentFragment.showDeployment(mDeploymentModel);
    }

    private void injector() {
        mUpdateDeploymentComponent = DaggerUpdateDeploymentComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public UpdateDeploymentComponent getComponent() {
        return mUpdateDeploymentComponent;
    }

    @Override
    public void onUpdateNavigateOrReloadList() {
        mUpdateDeploymentComponent.launcher().launchListDeployment();
        finish();
    }

    @Override
    public void onCancelUpdate() {
        finish();
    }
}
