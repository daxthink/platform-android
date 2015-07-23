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
import com.ushahidi.android.presentation.di.components.DaggerFeedbackComponent;
import com.ushahidi.android.presentation.di.components.FeedbackComponent;
import com.ushahidi.android.presentation.ui.fragment.FeedbackFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Henry Addo
 */
public class FeedbackActivity extends BaseAppActivity implements HasComponent<FeedbackComponent> {

    private FeedbackComponent mFeedbackComponent;

    /**
     * Default constructor
     */
    public FeedbackActivity() {
        super(R.layout.activity_feedback, 0);
    }

    /**
     * Provides {@link Intent} launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context) {
        return new Intent(context, FeedbackActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        final String feedbackTag = "feedback_tag";
        FeedbackFragment feedbackFragment = (FeedbackFragment) getSupportFragmentManager()
                .findFragmentByTag(feedbackTag);
        if (feedbackFragment == null) {
            feedbackFragment = FeedbackFragment.newInstance();
        }

        replaceFragment(R.id.feedback_fragment_container, feedbackFragment, feedbackTag);
    }

    private void injector() {
        mFeedbackComponent = DaggerFeedbackComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public FeedbackComponent getComponent() {
        return mFeedbackComponent;
    }
}
