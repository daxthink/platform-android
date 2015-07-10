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

/**
 * Base class for authentication tokens
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class AuthToken implements AuthHeaders {

    /**
     * Unit time or epoch time when the token was created (always in UTC). The
     * time may be 0 if the token is deserialized from data missing the field.
     */
    protected final long createdAt;

    public AuthToken() {
        createdAt = System.currentTimeMillis();
    }

    protected AuthToken(long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Determines whether a token is known to have expired.
     *
     * @return true if the token is known to have expired, otherwise false to indicate the token
     * may or may not be considered expired by the server.
     */
    public abstract boolean isExpired();
}
