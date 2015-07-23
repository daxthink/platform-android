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

import com.ushahidi.android.data.entity.UserEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface UserProfileDataSource {

    /**
     * @param deploymentId The ID of the deployment associated with the user
     * @param userEntityId The user id to be used for getting the user entity
     * @return The user entity
     */
    Observable<UserEntity> getUserEntity(Long deploymentId, Long userEntityId);


    /**
     * Add / Update an {@link UserEntity} to/in a storage.
     *
     * @param userEntities The entity to be added.
     * @return The row affected
     */
    Observable<Long> putUserEntity(UserEntity userEntities);

    /**
     * Delete an existing {@link UserEntity} in a storage.
     *
     * @param userEntity The user profile to be deleted.
     * @return True if successfully deleted otherwise false
     */
    Observable<Boolean> deleteUserEntity(UserEntity userEntity);

    /**
     * Gets a list of {@link UserEntity}
     *
     * @param deploymentId The deploymentId to be used for fetching the API
     * @return list of user entities
     */
    Observable<List<UserEntity>> getUserEntityList(Long deploymentId);

    /**
     * Fetches user profile based on the ID provided
     *
     * @param deploymentId The ID to fetch user profile by
     * @return The user entity
     */
    Observable<UserEntity> fetchUserProfile(Long deploymentId);
}
