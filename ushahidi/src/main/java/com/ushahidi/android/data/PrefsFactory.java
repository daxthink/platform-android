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

package com.ushahidi.android.data;

import com.addhen.android.raiburari.data.pref.BooleanPreference;
import com.addhen.android.raiburari.data.pref.LongPreference;
import com.addhen.android.raiburari.data.pref.StringPreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PrefsFactory {

    private SharedPreferences mSharedPreferences;

    @Inject
    public PrefsFactory(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public StringPreference getActiveDeploymentUrl() {
        return new StringPreference(getSharedPreferences(), "active_deployment_url", null);
    }

    public StringPreference getAccessToken() {
        return new StringPreference(getSharedPreferences(), "access_token", null);
    }

    public LongPreference getActiveDeploymentId() {
        return new LongPreference(getSharedPreferences(), "active_deployment_id", 0);
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public BooleanPreference enableAutoSync() {
        return new BooleanPreference(getSharedPreferences(), "AutoSync", false);
    }

    public StringPreference getActiveUserAccount() {
        return new StringPreference(getSharedPreferences(), "active_user_account", null);
    }
}
