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

package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.UserProfileEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.userprofile.UserProfileDataSource;
import com.ushahidi.android.data.repository.datasource.userprofile.UserProfileDataSourceFactory;
import com.ushahidi.android.domain.entity.UserProfile;
import com.ushahidi.android.domain.repository.UserProfileRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Implements {@link UserProfileDataRepository} to provide user data related manipulations
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class UserProfileDataRepository implements UserProfileRepository {

    private final UserProfileEntityDataMapper mUserProfileEntityDataMapper;

    private final UserProfileDataSourceFactory mUserProfileDataSourceFactory;

    /**
     * Default constructor
     *
     * @param userProfileDataSourceFactory The user profile data source factory
     * @param userProfileEntityDataMapper  The user profile entity data mapper
     */
    @Inject
    public UserProfileDataRepository(UserProfileDataSourceFactory userProfileDataSourceFactory,
            UserProfileEntityDataMapper userProfileEntityDataMapper) {
        mUserProfileDataSourceFactory = userProfileDataSourceFactory;
        mUserProfileEntityDataMapper = userProfileEntityDataMapper;
    }

    @Override
    public Observable<UserProfile> getUserProfile(Long deploymentId, Long userProfileId) {
        UserProfileDataSource userProfileDataSource = mUserProfileDataSourceFactory
                .createDatabaseSource();
        return userProfileDataSource.getUserEntity(deploymentId, userProfileId)
                .map(mUserProfileEntityDataMapper::map);
    }

    @Override
    public Observable<Long> putUserProfile(UserProfile userProfiles) {
        final UserProfileDataSource userProfileDataSource = mUserProfileDataSourceFactory
                .createDatabaseSource();
        return userProfileDataSource
                .putUserEntity(mUserProfileEntityDataMapper.unmap(userProfiles));
    }

    @Override
    public Observable<Boolean> deleteUserProfile(UserProfile userProfile) {
        final UserProfileDataSource userProfileDataSource = mUserProfileDataSourceFactory
                .createDatabaseSource();
        return userProfileDataSource
                .deleteUserEntity(mUserProfileEntityDataMapper.unmap(userProfile));
    }

    @Override
    public Observable<UserProfile> fetchUserProfile(Long deploymentId) {
        UserProfileDataSource userProfileDataSource = mUserProfileDataSourceFactory
                .createApiDataSource();

        return userProfileDataSource.fetchUserProfile(deploymentId)
                .map(mUserProfileEntityDataMapper::map);
    }
}
