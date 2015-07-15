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

package com.ushahidi.android.data.api;

import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessToken;
import com.ushahidi.android.data.api.ushoauth2.UshAccessTokenManager;
import com.ushahidi.android.data.entity.UserAccountEntity;
import com.ushahidi.android.data.entity.UserEntity;

import rx.Observable;

/**
 * User API Services
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserApi {

    private final UshAccessTokenManager mUshAccessTokenManager;

    public UserApi(UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    public Observable<OAuth2AccessToken> loginUserAccount(UserAccountEntity userAccountEntity) {
        return mUshAccessTokenManager.login(userAccountEntity).grantNewAccessToken()
                .doOnNext(oAuth2AccessToken -> mUshAccessTokenManager.getStorage()
                        .storeAccessToken(oAuth2AccessToken));
    }

    public Observable<UserEntity> getUserProfile() {
        return mUshAccessTokenManager.getValidAccessToken()
                .concatMap(
                        authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                                .getUser(authorizationHeader));
    }
}
