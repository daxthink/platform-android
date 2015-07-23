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

package com.ushahidi.android.data.entity;

/**
 * Platform auth token
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserAuthTokenEntity {

    private String mAccessToken;

    private String mTokenType;

    private String mRefreshToken;

    private long mExpires;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param deploymentId The deployment id
     * @param accessToken  The access token
     * @param tokenType    The access token type
     * @param refreshToken The refresh token
     * @param expires      The date it expires
     */
    public UserAuthTokenEntity(Long deploymentId, String accessToken, String tokenType,
            String refreshToken,
            long expires) {
        mDeploymentId = deploymentId;
        mAccessToken = accessToken;
        mTokenType = tokenType;
        mRefreshToken = refreshToken;
        mExpires = expires;
    }


    public String getAccessToken() {
        return mAccessToken;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public long getExpires() {
        return mExpires;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }
}
