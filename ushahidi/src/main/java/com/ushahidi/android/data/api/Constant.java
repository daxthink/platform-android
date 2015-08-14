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
 * Defines API resource paths
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class Constant {

    // TODO: Put these in the build script

    /**
     * OAuth client secret
     */
    public static final String OAUTH_CLIENT_SECRET = "35e7f0bca957836d05ca0492211b0ac707671261";

    /**
     * OAuth client id
     */
    public static final String OAUTH_CLIENT_ID = "ushahidiui";

    /**
     * OAuth scope
     */
    public static final String SCOPE
            = "posts media forms api tags sets users stats layers config messages dataproviders";

    /**
     * API version
     */
    public static final String API_PATH = "/api/v3";

    /**
     * Posts resources
     */
    public static final String POSTS = API_PATH + "/posts";

    /**
     * Tags resources
     */
    public static final String TAGS = API_PATH + "/tags";

    /**
     * Users resources
     */
    public static final String USERS_ME = API_PATH + "/users/me";

    /**
     * Geojson resources
     */
    public static final String GEOJSON = POSTS + "/geojson";

    private Constant() {
        // No instance
    }
}
