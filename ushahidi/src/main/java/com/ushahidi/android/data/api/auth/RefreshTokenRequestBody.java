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

package com.ushahidi.android.data.api.auth;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.android.data.api.qualifier.ClientId;
import com.ushahidi.android.data.api.qualifier.ClientSecret;
import com.ushahidi.android.data.api.qualifier.GrantType;
import com.ushahidi.android.data.api.qualifier.Scope;

import java.io.Serializable;

/**
 * Body object used to exchange a code with an access token.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class RefreshTokenRequestBody implements Serializable {

    @SerializedName("grant_type")
    private final String grantType;

    @SerializedName("client_id")
    private final String clientId;

    @SerializedName("client_secret")
    private final String clientSecret;

    private final String scope;

    public RefreshTokenRequestBody(@GrantType String grantType, @ClientId String clientId,
            @ClientSecret String clientSecret, @Scope String scope) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "Payload{" +
                ", grantType='" + grantType + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
