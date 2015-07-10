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

import com.ushahidi.android.data.api.auth.AccessToken;
import com.ushahidi.android.data.api.auth.Payload;
import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.UserEntity;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;

/**
 * User API Services
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserApi {

    private final RestfulService mRestfulService;

    @Inject
    public UserApi(@NonNull RestfulService restfulService) {
        mRestfulService = restfulService;
    }

    public Observable<PlatformAuthToken> loginUserAccount(@NonNull Payload payload) {
        return Observable.create(subscriber -> {
            try {
                final AccessToken accessToken = mRestfulService.getAccessToken(payload);
                PlatformAuthToken platformAuthToken = new PlatformAuthToken(
                        accessToken.getAccessToken(), accessToken.getTokenType(),
                        accessToken.getRefreshToken(), accessToken.getExpires());
                subscriber.onNext(platformAuthToken);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public Observable<UserEntity> getUserProfile(String header) {
        return Observable.create(subscriber -> {
            try {
                UserEntity userAccountEntity = mRestfulService.getUser(header);
                subscriber.onNext(userAccountEntity);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
