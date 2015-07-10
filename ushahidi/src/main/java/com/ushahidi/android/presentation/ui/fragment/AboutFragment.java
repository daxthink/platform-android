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

package com.ushahidi.android.presentation.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.R;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;

/**
 * @author Henry Addo
 */
public class AboutFragment extends BaseFragment {

    @Bind(R.id.app_version)
    AppCompatTextView version;

    @Bind(R.id.build_date)
    AppCompatTextView buildDateView;

    private static AboutFragment mAboutFragment;

    public AboutFragment() {
        super(R.layout.fragment_about, 0);
    }

    public static AboutFragment newInstance() {
        if (mAboutFragment == null) {
            mAboutFragment = new AboutFragment();
        }
        return mAboutFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        final DateFormat DATE_DISPLAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        StringBuilder sBuilder = new StringBuilder("v");
        sBuilder.append(BuildConfig.VERSION_NAME);
        sBuilder.append("-");
        sBuilder.append(String.valueOf(BuildConfig.VERSION_CODE));
        if (BuildConfig.DEBUG) {
            sBuilder.append("-");
            sBuilder.append(BuildConfig.GIT_SHA);
        }
        version.setText(sBuilder.toString());

        StringBuilder bBuilder = new StringBuilder(getString(R.string.build_date));
        bBuilder.append(" ");

        try {
            // Parse ISO8601-format time into local time.
            DateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date buildTime = inFormat.parse(BuildConfig.BUILD_TIME);
            bBuilder.append(DATE_DISPLAY_FORMAT.format(buildTime));
            buildDateView.setText(bBuilder.toString());
        } catch (ParseException e) {
            throw new RuntimeException("Unable to decode build time: " + BuildConfig.BUILD_TIME,
                    e);
        }
    }

}
