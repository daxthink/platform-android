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

package com.ushahidi.android.presentation.view.ui.activity;

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.AddDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.DaggerAddDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.view.ui.fragment.AddDeploymentFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Renders {@link com.ushahidi.android.presentation.view.ui.fragment.AddDeploymentFragment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddDeploymentActivity extends BaseAppActivity
        implements HasComponent<AddDeploymentComponent> {

    private static final String FRAG_TAG = "add_deployment";

    private AddDeploymentComponent mAddDeploymentComponent;

    private AddDeploymentFragment mAddDeploymentFragment;

    /**
     * Default constructor
     */
    public AddDeploymentActivity() {
        super(R.layout.activity_add_deployment, 0);
    }

    /**
     * Provides {@link Intent} launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context) {
        return new Intent(context, AddDeploymentActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        mAddDeploymentFragment = (AddDeploymentFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG);
        if (mAddDeploymentFragment == null) {
            mAddDeploymentFragment = AddDeploymentFragment.newInstance();
            replaceFragment(R.id.add_fragment_container, mAddDeploymentFragment, FRAG_TAG);
        }
    }

    private void injector() {
        mAddDeploymentComponent = DaggerAddDeploymentComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public AddDeploymentComponent getComponent() {
        return mAddDeploymentComponent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QrcodeReaderActivity.QRCODE_READER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final DeploymentModel deploymentModel = data.getParcelableExtra(
                        QrcodeReaderActivity.INTENT_EXTRA_PARAM_BARCODE_DEPLOYMENT_MODEL);
                mAddDeploymentFragment.setDeployment(deploymentModel);
            } else {
                showToast(R.string.no_qrcode_found);
            }
        }
    }
}
