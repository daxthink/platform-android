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

package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.domain.entity.UserProfile;
import com.ushahidi.android.presentation.model.UserProfileModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileModelDataMapper {

    @Inject
    public UserProfileModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link UserProfile} to {@link UserProfileModel}
     *
     * @param userProfile The {@link UserProfile} to be
     *                    mapped
     * @return The {@link UserProfileModel}
     */
    public UserProfileModel map(@NonNull UserProfile userProfile) {
        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel._id = userProfile._id;
        userProfileModel.setEmail(userProfile.getEmail());
        userProfileModel.setRealName(userProfile.getRealName());
        userProfileModel.setUsername(userProfile.getUsername());
        userProfileModel.setRole(UserProfileModel.Role.valueOf(userProfile.getRole().name()));
        userProfileModel.setUpdated(userProfile.getUpdated());
        userProfileModel.setCreated(userProfile.getCreated());
        userProfileModel.setDeploymentId(userProfile.getDeploymentId());
        return userProfileModel;
    }

    public UserProfile unmap(@NonNull UserProfileModel userProfileModel) {
        UserProfile userProfile = new UserProfile();
        userProfile._id = userProfileModel._id;
        userProfile.setEmail(userProfileModel.getEmail());
        userProfile.setRealName(userProfileModel.getRealName());
        userProfile.setUsername(userProfileModel.getUsername());
        userProfile.setRole(UserProfile.Role.valueOf(userProfileModel.getRole().name()));
        userProfile.setUpdated(userProfileModel.getUpdated());
        userProfile.setCreated(userProfileModel.getCreated());
        userProfile.setDeploymentId(userProfileModel.getDeploymentId());
        return userProfile;
    }

    /**
     * Maps a list {@link UserProfile} into a list of {@link UserProfileModel}.
     *
     * @param userProfileList List to be mapped.
     * @return {@link UserProfileModel}
     */
    public List<UserProfileModel> map(@NonNull List<UserProfile> userProfileList) {
        List<UserProfileModel> userProfileModelList = new ArrayList<>();
        UserProfileModel userProfileModel;
        for (UserProfile userProfile : userProfileList) {
            userProfileModel = map(userProfile);
            if (userProfileModel != null) {
                userProfileModelList.add(userProfileModel);
            }
        }
        return userProfileModelList;
    }
}