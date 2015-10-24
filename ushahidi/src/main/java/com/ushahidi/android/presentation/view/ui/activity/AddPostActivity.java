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
import com.ushahidi.android.presentation.di.components.post.AddPostComponent;
import com.ushahidi.android.presentation.di.components.post.DaggerAddPostComponent;
import com.ushahidi.android.presentation.model.FormStageModel;
import com.ushahidi.android.presentation.view.ui.adapter.AddPostFragmentStatePageAdapter;
import com.ushahidi.android.presentation.view.ui.fragment.AddPostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

/**
 * Renders {@link AddPostFragment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostActivity extends BaseAppActivity
        implements HasComponent<AddPostComponent> {

    private static final String INTENT_EXTRA_PARAM_FORM_ID
            = "com.ushahidi.android.INTENT_PARAM_FORM_ID";

    private static final String INTENT_EXTRA_PARAM_FORM_TITLE
            = "com.ushahidi.android.INTENT_PARAM_FORM_TITLE";

    private static final String BUNDLE_STATE_PARAM_FORM_ID
            = "com.ushahidi.android.STATE_PARAM_FORM_ID";

    private static final String BUNDLE_STATE_PARAM_FORM_TITLE
            = "com.ushahidi.android.STATE_PARAM_FORM_TITLE";

    private static final String FRAG_TAG = "add_post";

    private AddPostComponent mAddPostComponent;

    private AddPostFragment mAddPostFragment;

    private Long mFormId;

    private List<FormStageModel> mFormStages;

    private String mFormTitle;

    private AddPostFragmentStatePageAdapter mAddPostFragmentStatePageAdapter;

    /**
     * Default constructor
     */
    public AddPostActivity() {
        super(R.layout.activity_add_post, 0);
    }

    /**
     * Provides {@link Intent} for launching this activity
     *
     * @param context The calling context
     * @return The intent to launch this activity
     */
    public static Intent getIntent(final Context context, Long formId, String formTitle) {
        Intent intent = new Intent(context, AddPostActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_FORM_ID, formId);
        intent.putExtra(INTENT_EXTRA_PARAM_FORM_TITLE, formTitle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        initialize(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(BUNDLE_STATE_PARAM_FORM_ID, mFormId);
        savedInstanceState.putString(BUNDLE_STATE_PARAM_FORM_TITLE, mFormTitle);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void injector() {
        mAddPostComponent = DaggerAddPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFormId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_FORM_ID, 0l);
            mFormTitle = getIntent().getStringExtra(INTENT_EXTRA_PARAM_FORM_TITLE);
            mAddPostFragment = (AddPostFragment) getSupportFragmentManager().findFragmentByTag(
                    FRAG_TAG);
            if (mAddPostFragment == null) {
                mAddPostFragment = AddPostFragment.newInstance(mFormId, mFormId);
                replaceFragment(R.id.add_post_fragment_container, mAddPostFragment, FRAG_TAG);
            }

        } else {
            mFormId = savedInstanceState.getLong(BUNDLE_STATE_PARAM_FORM_ID);
            mFormTitle = savedInstanceState.getString(BUNDLE_STATE_PARAM_FORM_TITLE);
        }
        getSupportActionBar().setTitle(mFormTitle);
    }

    @Override
    public AddPostComponent getComponent() {
        return mAddPostComponent;
    }

}
