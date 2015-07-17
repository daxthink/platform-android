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

package com.ushahidi.android.data.api.account;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Represents a Platform session that is associated with a {@link PlatformAuthToken}.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PlatformSession extends Session {

    @SerializedName("user_name")
    private final String userName;

    /**
     * @param userId   User ID
     * @param userName User Name
     * @throws {@link java.lang.IllegalArgumentException} if token argument is null
     */
    public PlatformSession(@NonNull long userId, @NonNull String userName,
            long deploymentId) {
        super(userId, deploymentId);
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final PlatformSession that = (PlatformSession) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public static class Serializer implements SerializationStrategy<PlatformSession> {

        private final Gson gson;

        public Serializer() {
            this.gson = new Gson();
        }

        @Override
        public String serialize(PlatformSession session) {
            if (session != null) {
                try {
                    return gson.toJson(session);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }

        @Override
        public PlatformSession deserialize(String serializedSession) {
            if (!TextUtils.isEmpty(serializedSession)) {
                try {
                    return gson.fromJson(serializedSession, PlatformSession.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
