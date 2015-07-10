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

import com.ushahidi.android.data.api.Constant;
import com.ushahidi.android.data.api.PlatformAuthConfig;
import com.ushahidi.android.data.api.PlatformAuthToken;
import com.ushahidi.android.data.api.UserApi;
import com.ushahidi.android.data.api.auth.Payload;
import com.ushahidi.android.data.entity.UserAccountEntity;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserAccountApiDataSource implements UserAccountDataSource {

    private final UserApi mUserApi;

    private PlatformAuthConfig mPlatformAuthConfig;

    public UserAccountApiDataSource(@NonNull PlatformAuthConfig platformAuthConfig,
            @NonNull UserApi userApi) {
        mUserApi = userApi;
        mPlatformAuthConfig = platformAuthConfig;
    }

    @Override
    public Observable<PlatformAuthToken> loginUserAccountEntity(
            UserAccountEntity userAccountEntity) {
        Payload payload = new Payload(userAccountEntity.getAccountName(),
                userAccountEntity.getPassword(), Constant.USHAHIDI_AUTHTOKEN_PASSWORD_TYPE,
                mPlatformAuthConfig.clientId, mPlatformAuthConfig.clientSecret,
                mPlatformAuthConfig.scope);
        return mUserApi.loginUserAccount(payload);
    }
}
