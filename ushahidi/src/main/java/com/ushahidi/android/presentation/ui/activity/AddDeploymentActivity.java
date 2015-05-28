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

package com.ushahidi.android.presentation.ui.activity;

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.addhen.android.raiburari.presentation.ui.activity.BaseActivity;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.AddDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.DaggerAddDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.DeleteDeploymentComponent;
import com.ushahidi.android.presentation.ui.fragment.AddDeploymentFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Renders {@link com.ushahidi.android.presentation.ui.fragment.AddDeploymentFragment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddDeploymentActivity extends BaseActivity
        implements HasComponent<AddDeploymentComponent> {

    private AddDeploymentComponent mAddDeploymentComponent;

    private DeleteDeploymentComponent mDeleteDeploymentComponent;

    public AddDeploymentActivity() {
        super(R.layout.activity_add_deployment, 0);
    }

    public static Intent getIntent(final Context context) {
        return new Intent(context, AddDeploymentActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        if (savedInstanceState == null) {
            addFragment(R.id.add_fragment_container, AddDeploymentFragment.newInstance(),
                    "addDeploymentFrag");
        }
    }

    private void injector() {
        mAddDeploymentComponent = DaggerAddDeploymentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public AddDeploymentComponent getComponent() {
        return mAddDeploymentComponent;
    }

    public DeleteDeploymentComponent getDeleteCompnent() {
        return mDeleteDeploymentComponent;
    }
}
