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

package com.ushahidi.android.data.repository.datasource.useraccount;

import com.ushahidi.android.data.api.UserApi;
import com.ushahidi.android.data.api.auth.Payload;
import com.ushahidi.android.data.entity.UserAccountEntity;

import android.support.annotation.NonNull;

import rx.Observable;

import static com.ushahidi.android.data.api.Constant.OAUTH_CLIENT_ID;
import static com.ushahidi.android.data.api.Constant.OAUTH_CLIENT_SECRET;
import static com.ushahidi.android.data.api.Constant.SCOPE;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserAccountApiDataSource implements UserAccountDataSource {

    private final UserApi mUserApi;

    public UserAccountApiDataSource(@NonNull UserApi userApi) {
        mUserApi = userApi;
    }

    @Override
    public Observable<UserAccountEntity> loginUserAccountEntity(
            UserAccountEntity userAccountEntity) {
        Payload payload = new Payload(userAccountEntity.getAccountName(),
                userAccountEntity.getPassword(), userAccountEntity.getAuthTokenType(),
                OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, SCOPE);
        return Observable.create(subscriber -> mUserApi.loginUserAccount(payload).map(
                accessToken -> {
                    userAccountEntity.setAuthToken(accessToken.getAccessToken());
                    userAccountEntity.setAuthTokenType(accessToken.getTokenType());
                    return userAccountEntity;
                }
        ));
    }
}
