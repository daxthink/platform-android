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

import com.addhen.android.raiburari.data.pref.LongPreference;
import com.addhen.android.raiburari.data.pref.StringPreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Since the base library doesn't allow the different Type SharedPreferences to be injectable, use
 * {@link PrefsFactory} to create new instances of them which are specific to this app's
 * SharedPreferences
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PrefsFactory {

    private SharedPreferences mSharedPreferences;

    @Inject
    public PrefsFactory(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * Gets the value of the stored active deployment URL in the {@link SharedPreferences}. This
     * makes it easier to get the active URL without making a query to the database.
     *
     * @return A string type preference.
     */
    public StringPreference getActiveDeploymentUrl() {
        return new StringPreference(getSharedPreferences(), "active_deployment_url", null);
    }

    /**
     * Gets the value of the stored accessToken in the {@link SharedPreferences}.
     * An easier and faster way to retrieve the accessToken without making unecessary
     * database calls.
     *
     * @return A string type preference
     */
    public StringPreference getAccessToken() {
        return new StringPreference(getSharedPreferences(), "access_token", null);
    }

    /**
     * Gets the value of the stored active deployment ID in the {@link SharedPreferences}. This
     * makes it easier to get the active deployment ID without making a query to the database.
     *
     * @return A Long type preference
     */
    public LongPreference getActiveDeploymentId() {
        return new LongPreference(getSharedPreferences(), "active_deployment_id", 0);
    }

    /**
     * Gets the application's {@link SharedPreferences}
     *
     * @return The SharedPreference being used by the application.
     */
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
