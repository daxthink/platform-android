/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.domain.repository;

import com.ushahidi.android.domain.entity.UserProfile;

import rx.Observable;

/**
 * Repository for manipulating {@link com.ushahidi.android.domain.entity.UserProfile}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface UserProfileRepository {

    /**
     * Gets user profile
     *
     * @param deploymentId  The deploymentId to be used for fetching the API
     * @param userProfileId The ID of the user
     * @return The user profile
     */
    Observable<UserProfile> getUserProfile(Long deploymentId, Long userProfileId);

    /**
     * Add / Update an {@link UserProfile} to/in a storage.
     *
     * @param userProfile The entity to be added.
     * @return The user profile
     */
    Observable<Long> putUserProfile(UserProfile userProfile);

    /**
     * Delete an existing {@link UserProfile} in a storage.
     *
     * @param userProfile The user profile to be deleted.
     * @return True if successfully deleted
     */
    Observable<Boolean> deleteUserProfile(UserProfile userProfile);

    /**
     * Fetches user profile via the API
     *
     * @param deploymentId The deployment ID
     * @return The user profile
     */
    Observable<UserProfile> fetchUserProfile(Long deploymentId);
}
