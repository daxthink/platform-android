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
import com.addhen.android.raiburari.presentation.ui.activity.BaseActivity;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.DaggerDeleteDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.DaggerListDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.DeleteDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.ListDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.ui.fragment.ListDeploymentFragment;
import com.ushahidi.android.presentation.ui.navigation.Launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListDeploymentActivity extends BaseActivity
        implements HasComponent<ListDeploymentComponent>,
        ListDeploymentFragment.DeploymentListListener {

    private ListDeploymentComponent mListDeploymentComponent;

    private DeleteDeploymentComponent mDeleteDeploymentComponent;

    @Inject
    Launcher mLauncher;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    public ListDeploymentActivity() {
        super(R.layout.activity_list_deployment, R.menu.list_deployment);
    }

    public static Intent getIntent(final Context context) {
        return new Intent(context, ListDeploymentActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        injector();
        if (savedInstanceState == null) {
            addFragment(R.id.add_fragment_container, ListDeploymentFragment.newInstance());
        }
    }

    private void injector() {
        mDeleteDeploymentComponent = DaggerDeleteDeploymentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        mListDeploymentComponent = DaggerListDeploymentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ListDeploymentComponent getComponent() {
        return mListDeploymentComponent;
    }

    @SuppressWarnings("unchecked")
    public DeleteDeploymentComponent getDeleteDeploymentComponent() {
        return mDeleteDeploymentComponent;
    }

    @Override
    public void onDeploymentClicked(DeploymentModel deploymentModel) {

    }
}