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

import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.entity.UserAccountEntity;
import com.ushahidi.android.data.entity.UserEntity;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import rx.Observable;

/**
 * User API Services
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserApi {

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param ushAccessTokenManager The access token manager
     */
    public UserApi(UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Retrieves an {@link rx.Observable} which will emit an {@link OAuth2AccessToken}.
     *
     * @param userAccountEntity The user account entity to get it's access token
     * @return The OAuth access token to be emitted
     */
    public Observable<OAuth2AccessToken> loginUserAccount(UserAccountEntity userAccountEntity) {
        return mUshAccessTokenManager.login(userAccountEntity).grantNewAccessToken()
                .doOnNext(oAuth2AccessToken -> mUshAccessTokenManager.getStorage()
                        .storeAccessToken(oAuth2AccessToken));
    }

    /**
     * Retrieves an {@link rx.Observable} which will emit an {@link UserEntity}.
     *
     * @return The user entity to be emitted
     */
    public Observable<UserEntity> getUserProfile() {
        return mUshAccessTokenManager.getValidAccessToken()
                .concatMap(authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getUser(authorizationHeader));
    }
}
