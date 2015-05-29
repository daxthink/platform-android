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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.UserEntity;
import com.ushahidi.android.domain.entity.UserProfile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileEntityDataMapper {

    @Inject
    public UserProfileEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link UserEntity} to {@link UserProfile}
     *
     * @param userEntity The {@link UserProfile} to be
     *                   mapped
     * @return The {@link UserProfile} entity
     */
    public UserProfile map(UserEntity userEntity) {
        UserProfile user = new UserProfile();
        if (userEntity != null) {
            user._id = userEntity.getId();
            user.setEmail(userEntity.getEmail());
            user.setRealName(userEntity.getRealName());
            user.setUsername(userEntity.getUsername());
            user.setRole(UserProfile.Role.valueOf(userEntity.getRole().name()));
            user.setUpdated(userEntity.getUpdated());
            user.setCreated(userEntity.getCreated());
            user.setDeploymentId(userEntity.getDeployment());
        }

        return user;
    }

    public UserEntity unmap(UserProfile user) {
        UserEntity userEntity = new UserEntity();
        if (user != null) {
            userEntity.setId(user._id);
            userEntity.setEmail(user.getEmail());
            userEntity.setRealName(user.getRealName());
            userEntity.setUsername(user.getUsername());
            userEntity.setRole(UserEntity.Role.valueOf(user.getRole().name()));
            userEntity.setUpdated(user.getUpdated());
            userEntity.setCreated(user.getCreated());
            userEntity.setDeployment(user.getDeploymentId());
        }
        return userEntity;
    }

    /**
     * Maps a list {@link UserEntity} into a list of {@link UserProfile}.
     *
     * @param userEntityList List to be mapped.
     * @return {@link UserProfile}
     */
    public List<UserProfile> map(List<UserEntity> userEntityList) {
        List<UserProfile> userList = new ArrayList<>();
        UserProfile user;
        for (UserEntity userEntity : userEntityList) {
            user = map(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }
}