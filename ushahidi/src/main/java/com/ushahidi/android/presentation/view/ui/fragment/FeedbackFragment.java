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
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.FeedbackComponent;
import com.ushahidi.android.presentation.presenter.FeedbackPresenter;
import com.ushahidi.android.presentation.view.FeedbackView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Henry Addo
 */
public class FeedbackFragment extends BaseFragment implements FeedbackView {

    private static FeedbackFragment mFeedbackFragment;

    @Inject
    FeedbackPresenter mSendFeedbackPresenter;

    @Bind(R.id.feedback_message)
    AppCompatEditText mFormEditText;

    @Bind(R.id.feedback_device_info)
    AppCompatTextView mDeviceInfo;

    @Bind(R.id.select_subject)
    Spinner mSubject;

    public FeedbackFragment() {
        super(R.layout.fragment_feedback, 0);
    }

    public static FeedbackFragment newInstance() {
        if (mFeedbackFragment == null) {
            mFeedbackFragment = new FeedbackFragment();
        }
        return mFeedbackFragment;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initialize();
    }

    private void initialize() {
        getComponent(FeedbackComponent.class).inject(this);
        mSendFeedbackPresenter.setView(this);
        final String deviceInfo = mSendFeedbackPresenter.getDeviceInfo();
        mDeviceInfo.setText(deviceInfo);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @OnClick(R.id.feedback_send)
    public void onClickSubmit() {
        final String subject = mSubject.getSelectedItem().toString();
        final String deviceInfo = mDeviceInfo.getText().toString();
        final String message = mFormEditText.getText().toString();
        mSendFeedbackPresenter.send(subject, deviceInfo, message);
    }

    @Override
    public Context getAppContext() {
        return getActivity();
    }

}
