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

import com.ushahidi.android.data.database.UserDatabaseHelper;
import com.ushahidi.android.data.entity.UserEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileDatabaseSource implements UserProfileDataSource {

    private UserDatabaseHelper mUserDatabaseHelper;

    /**
     * Default constructor
     *
     * @param userDatabaseHelper The user database helper
     */
    public UserProfileDatabaseSource(@NonNull UserDatabaseHelper userDatabaseHelper) {
        mUserDatabaseHelper = userDatabaseHelper;
    }

    @Override
    public Observable<UserEntity> getUserEntity(Long deploymentId, Long userEntityId) {
        return mUserDatabaseHelper.getUserProfile(deploymentId, userEntityId);
    }

    @Override
    public Observable<Long> putUserEntity(UserEntity userEntity) {
        return mUserDatabaseHelper.putUser(userEntity);
    }

    @Override
    public Observable<Boolean> deleteUserEntity(UserEntity userEntity) {
        return mUserDatabaseHelper.deleteUserProfile(userEntity);
    }

    @Override
    public Observable<List<UserEntity>> getUserEntityList(Long deploymentId) {
        return mUserDatabaseHelper.getUserProfiles(deploymentId);
    }

    @Override
    public Observable<UserEntity> fetchUserProfile(Long deploymentId) {
        throw new UnsupportedOperationException();
    }
}
