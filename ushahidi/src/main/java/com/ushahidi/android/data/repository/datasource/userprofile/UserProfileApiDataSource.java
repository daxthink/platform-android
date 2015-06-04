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

    public UserProfileApiDataSource(@NonNull UserApi userApi,
            @NonNull UserDatabaseHelper userDatabaseHelper) {
        mUserApi = userApi;
        mUserDatabaseHelper = userDatabaseHelper;
    }

    @Override
    public Observable<UserEntity> getUserEntity(Long deploymentId, Long userEntityId) {
        return mUserApi.getUserProfile();
    }

    @Override
    public Observable<List<UserEntity>> getUserEntityList(Long deploymentId) {
        return null;
    }

    @Override
    public Observable<Long> putUserEntity(List<UserEntity> userEntities) {
        return null;
    }

    @Override
    public Observable<Boolean> deleteUserEntity(UserEntity userEntity) {
        return null;
    }
}
