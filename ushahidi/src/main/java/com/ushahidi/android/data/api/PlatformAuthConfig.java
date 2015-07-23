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

import android.os.Parcel;
import android.os.Parcelable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Platform auth configuration class
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PlatformAuthConfig implements Parcelable {

    /**
     * Parcelable creator
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlatformAuthConfig> CREATOR
            = new Parcelable.Creator<PlatformAuthConfig>() {
        @Override
        public PlatformAuthConfig createFromParcel(Parcel in) {
            return new PlatformAuthConfig(in);
        }

        @Override
        public PlatformAuthConfig[] newArray(int size) {
            return new PlatformAuthConfig[size];
        }
    };

    private final String clientId;

    private final String clientSecret;

    private final String scope;

    /**
     * Default constructor
     *
     * @param clientId     The OAuth client id
     * @param clientSecret The OAuth client secret
     * @param scope        The OAuth scope
     */
    @Inject
    public PlatformAuthConfig(String clientId, String clientSecret, String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    /**
     * Initializes member variables with values from the parcel object
     *
     * @param in The {@link Parcel}
     */
    protected PlatformAuthConfig(Parcel in) {
        clientId = in.readString();
        clientSecret = in.readString();
        scope = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientId);
        dest.writeString(clientSecret);
        dest.writeString(scope);
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
}
