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

import com.ushahidi.android.data.entity.UserAccountEntity;
import com.ushahidi.android.domain.entity.UserAccount;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class UserAccountEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public UserAccountEntityDataMapper() {
        // Do nothing
    }


    /**
     * Maps {@link UserAccountEntity} to {@link UserAccount}
     *
     * @param userAccountEntity The {@link UserAccountEntity} to be
     *                          mapped
     * @return The {@link UserAccount} entity
     */
    public UserAccount map(UserAccountEntity userAccountEntity) {
        UserAccount userAccount = new UserAccount();
        if (userAccountEntity != null) {
            userAccount._id = userAccountEntity._id;
            userAccount.setAccountName(userAccountEntity.getAccountName());
            userAccount.setPassword(userAccountEntity.getPassword());

        }
        return userAccount;
    }

    /**
     * Maps a list {@link UserAccount} into a list of {@link
     * UserAccountEntity}.
     *
     * @param userAccount to be mapped.
     * @return {@link UserAccountEntity}
     */
    public UserAccountEntity map(UserAccount userAccount) {
        UserAccountEntity userAccountEntity = new UserAccountEntity();

        if (userAccount != null) {
            userAccountEntity._id = userAccount._id;
            userAccountEntity.setAccountName(userAccount.getAccountName());
            userAccountEntity.setPassword(userAccount.getPassword());
        }
        return userAccountEntity;
    }

    /**
     * Maps a list {@link UserAccountEntity} into a list of {@link
     * UserAccountEntity}.
     *
     * @param userAccountEntityList List to be mapped.
     * @return {@link UserAccountEntity}
     */
    public List<UserAccount> map(List<UserAccountEntity> userAccountEntityList) {
        List<UserAccount> userAccountList = new ArrayList<>();
        UserAccount userAccount;
        for (UserAccountEntity userAccountEntity : userAccountEntityList) {
            userAccount = map(userAccountEntity);
            if (userAccount != null) {
                userAccountList.add(userAccount);
            }
        }

        return userAccountList;
    }
}
