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

import com.ushahidi.android.data.entity.UserAccountEntity;
import com.ushahidi.android.domain.entity.UserAccount;
import com.ushahidi.android.presentation.model.UserAccountModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserAccountModelDataMapper {

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
        userAccountModel.setAuthToken(userAccount.getAuthToken());
        userAccountModel.setAuthTokenType(userAccount.getAuthTokenType());
        userAccountModel.setAccountName(userAccount.getAccountName());
        userAccountModel.setPassword(userAccount.getPassword());
        return userAccountModel;
    }

    public UserAccount map(@NonNull UserAccountModel userAccountModel) {
        UserAccount userAccount = new UserAccount();
        userAccount._id = userAccountModel._id;
        userAccount.setAuthToken(userAccountModel.getAuthToken());
        userAccount.setAuthTokenType(userAccountModel.getAuthTokenType());
        userAccount.setAccountName(userAccountModel.getAccountName());
        userAccount.setPassword(userAccountModel.getPassword());
        return userAccount;
    }

    /**
     * Maps a list {@link UserAccountEntity} into a list of {@link
     * UserAccountEntity}.
     *
     * @param userAccountList List to be mapped.
     * @return {@link UserAccountEntity}
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
