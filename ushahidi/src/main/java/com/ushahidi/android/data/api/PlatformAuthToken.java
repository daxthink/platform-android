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

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;

/**
 * Platform auth token
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PlatformAuthToken extends AuthToken implements Parcelable {

    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("token_type")
    private String mTokenType;

    @SerializedName("refresh_token")
    private String mRefreshToken;

    @SerializedName("expires")
    private long mExpires;

    public PlatformAuthToken(String accessToken, String tokenType, String refreshToken,
            long expires) {
        mAccessToken = accessToken;
        mTokenType = tokenType;
        mRefreshToken = refreshToken;
        mExpires = expires;
    }

    @Override
    public boolean isExpired() {
        return mExpires > SystemClock.currentThreadTimeMillis();
    }

    @Override
    public Map<String, String> getAuthHeaders() {
        final Map<String, String> headers = new HashMap<>(1);
        String authorizationHeader = getTokenType() + " " + mAccessToken;
        headers.put(HEADER_AUTHORIZATION, authorizationHeader);
        return headers;
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

    protected PlatformAuthToken(Parcel in) {
        mAccessToken = in.readString();
        mTokenType = in.readString();
        mRefreshToken = in.readString();
        mExpires = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAccessToken);
        dest.writeString(mTokenType);
        dest.writeString(mRefreshToken);
        dest.writeLong(mExpires);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlatformAuthToken> CREATOR
            = new Parcelable.Creator<PlatformAuthToken>() {
        @Override
        public PlatformAuthToken createFromParcel(Parcel in) {
            return new PlatformAuthToken(in);
        }

        @Override
        public PlatformAuthToken[] newArray(int size) {
            return new PlatformAuthToken[size];
        }
    };


}
