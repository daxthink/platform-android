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

package com.ushahidi.android.data.repository.datasource.userprofile;

import com.ushahidi.android.data.api.UserApi;
import com.ushahidi.android.data.api.service.UserService;
import com.ushahidi.android.data.database.UserDatabaseHelper;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileDataSourceFactory {

    private final Context mContext;

    private final UserDatabaseHelper mUserDatabaseHelper;

    private UserService mUserService;

    @Inject
    public UserProfileDataSourceFactory(@NonNull Context context,
            @NonNull UserDatabaseHelper userDatabaseHelper) {
        mContext = context;
        mUserDatabaseHelper = userDatabaseHelper;
    }

    /**
     * Sets the API services
     *
     * @param userService The user API service
     */
    public void setUserService(@NonNull UserService userService) {
        mUserService = userService;
    }

    public UserProfileDataSource createApiDataSource() {
        if (mUserService == null) {
            throw new RuntimeException("Please call setUserService(...)");
        }
        final UserApi userApi = new UserApi(mContext, mUserService);
        return new UserProfileApiDataSource(userApi, mUserDatabaseHelper);
    }

    public UserProfileDataSource createDatabaseSource() {
        return new UserProfileDatabaseSource(mUserDatabaseHelper);
    }
}
