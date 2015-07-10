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

package com.ushahidi.android.presentation.presenter;

import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.view.FeedbackView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * @author Henry Addo
 */
public class FeedbackPresenter implements Presenter {

    FeedbackView mFeedbackView;

    @Inject
    public FeedbackPresenter() {
    }

    public void setView(FeedbackView feedbackView) {
        mFeedbackView = feedbackView;
    }

    public void send(String subject, String deviceInfo, String message) {
        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            String[] recipients = new String[]{BuildConfig.FEEDBACK_EMAIL_ADDRESS};
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "Ushahidi Android App Feedback");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    "Subject: " + subject +
                            "\n\nMessage:\n" + message + // Enter description
                            "\n\n" + deviceInfo);
            emailIntent.setType(
                    "plain/text"); // This is an incorrect MIME, but Gmail is one of the only apps that responds to it
            final PackageManager pm = mFeedbackView.getAppContext().getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            ResolveInfo best = null;
            int count = matches.size();
            for (int i = 0; i < count; i++) {
                if (matches.get(i).activityInfo.packageName.endsWith(".gm") ||
                        matches.get(i).activityInfo.name.toLowerCase(Locale.ENGLISH)
                                .contains("gmail")) {
                    best = matches.get(i);
                }
            }
            if (best != null) {
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            }
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mFeedbackView.getAppContext().startActivity(emailIntent);
            mFeedbackView.showError(
                    mFeedbackView.getAppContext().getString(R.string.launching_email_client));

        } catch (Exception e) {
            mFeedbackView.showError(
                    mFeedbackView.getAppContext()
                            .getString(R.string.failed_launching_email_client));
        }
    }

    public String getDeviceInfo() {
        String deviceInfo = null;
        try {
            PackageManager manager = mFeedbackView.getAppContext().getPackageManager();
            PackageInfo packageInfo = manager
                    .getPackageInfo(mFeedbackView.getAppContext().getPackageName(), 0);

            deviceInfo = mFeedbackView.getAppContext().getString(
                    R.string.device_model) + ": " + Build.MODEL + " ("
                    + Build.PRODUCT + ")\n" + // Model
                    mFeedbackView.getAppContext().getString(R.string.device_manufacturer) + ": "
                    + Build.MANUFACTURER
                    + "\n" + // Manufacturer
                    mFeedbackView.getAppContext().getString(R.string.device_brand) + ": "
                    + Build.BRAND + "\n" + // Brand
                    mFeedbackView.getAppContext().getString(R.string.device_os) + ": v"
                    + Build.VERSION.RELEASE + " ("
                    + Build.VERSION.INCREMENTAL + ")\n" + // OS
                    mFeedbackView.getAppContext().getString(R.string.app_name) + ": "
                    + packageInfo.versionName;
        } catch (Exception e) {
            mFeedbackView.showError(mFeedbackView.getAppContext()
                    .getString(R.string.could_not_get_device_info));
        }

        return deviceInfo;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
