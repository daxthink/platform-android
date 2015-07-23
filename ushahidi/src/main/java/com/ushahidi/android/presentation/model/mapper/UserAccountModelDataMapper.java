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

import com.ushahidi.android.domain.entity.UserAccount;
import com.ushahidi.android.presentation.model.UserAccountModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Maps {@link UserAccount} onto {@link UserAccountModel}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserAccountModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public UserAccountModelDataMapper() {
        // Do nothing
    }


    /**
     * Maps {@link UserAccount} to {@link UserAccountModel}
     *
     * @param userAccount The {@link UserAccount} to be
     *                    mapped
     * @return The {@link UserAccountModel} entityModel
     */
    public UserAccountModel map(@NonNull UserAccount userAccount) {
        UserAccountModel userAccountModel = new UserAccountModel();
        userAccountModel._id = userAccount._id;
        userAccountModel.setAccountName(userAccount.getAccountName());
        userAccountModel.setPassword(userAccount.getPassword());
        return userAccountModel;
    }

    /**
     * Maps {@link UserAccountModel} to {@link UserAccount}
     *
     * @param userAccountModel The {@link UserAccountModel} to be
     *                         mapped
     * @return The {@link UserAccount} object
     */
    public UserAccount map(@NonNull UserAccountModel userAccountModel) {
        UserAccount userAccount = new UserAccount();
        userAccount._id = userAccountModel._id;
        userAccount.setAccountName(userAccountModel.getAccountName());
        userAccount.setPassword(userAccountModel.getPassword());
        return userAccount;
    }

    /**
     * Maps a list {@link UserAccount} into a list of {@link
     * UserAccountModel}.
     *
     * @param userAccountList List to be mapped.
     * @return {@link UserAccountModel}
     */
    public List<UserAccountModel> map(@NonNull List<UserAccount> userAccountList) {
        List<UserAccountModel> userAccountModelList = new ArrayList<>();
        UserAccountModel userAccountModel;
        for (UserAccount userAccount : userAccountList) {
            userAccountModel = map(userAccount);
            if (userAccount != null) {
                userAccountModelList.add(userAccountModel);
            }
        }
        return userAccountModelList;
    }
}
