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
import com.ushahidi.android.data.database.UserDatabaseHelper;
import com.ushahidi.android.data.entity.UserEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileApiDataSource implements UserProfileDataSource {

    private final UserApi mUserApi;

    private final UserDatabaseHelper mUserDatabaseHelper;

    /**
     * Default constructor
     *
     * @param userApi            The user API
     * @param userDatabaseHelper The user database helper
     */
    public UserProfileApiDataSource(@NonNull UserApi userApi,
            @NonNull UserDatabaseHelper userDatabaseHelper) {
        mUserApi = userApi;
        mUserDatabaseHelper = userDatabaseHelper;
    }

    @Override
    public Observable<UserEntity> getUserEntity(Long deploymentId, Long userEntityId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> putUserEntity(UserEntity userEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> deleteUserEntity(UserEntity userEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<UserEntity>> getUserEntityList(Long deploymentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<UserEntity> fetchUserProfile(Long deploymentId) {
        return mUserApi.getUserProfile().doOnNext(userEntity -> mUserDatabaseHelper
                .put(setDeploymentId(deploymentId, userEntity)));
    }

    private UserEntity setDeploymentId(Long deploymentId, UserEntity userEntity) {
        userEntity.setDeploymentId(deploymentId);
        return userEntity;
    }
}