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

package com.ushahidi.android.data.api.service;

import com.google.gson.JsonElement;

import com.ushahidi.android.data.api.model.Forms;
import com.ushahidi.android.data.api.model.Posts;
import com.ushahidi.android.data.api.model.Tags;
import com.ushahidi.android.data.api.oauth.AccessTokenRequestBody;
import com.ushahidi.android.data.api.oauth.RefreshTokenRequestBody;
import com.ushahidi.android.data.entity.UserEntity;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

import static com.ushahidi.android.data.api.Constant.FORMS;
import static com.ushahidi.android.data.api.Constant.GEOJSON;
import static com.ushahidi.android.data.api.Constant.POSTS;
import static com.ushahidi.android.data.api.Constant.TAGS;
import static com.ushahidi.android.data.api.Constant.USERS_ME;

/**
 * This interface aas all the Ushahidi Platform API services currently being used by the app
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface RestfulService {

    // Post related APIs

    /**
     * Fetches posts. Returns an observable that emits {@link Posts}
     *
     * @param authorizationHeader The access token header
     * @return Posts
     */
    @GET(POSTS)
    Observable<Posts> posts(@Header("Authorization") String authorizationHeader);

    // Tags related APIs

    /**
     * Fetches Tags. Returns an observable that emits {@link Tags}
     *
     * @param authorizationHeader The access token header
     * @return Tags
     */
    @GET(TAGS)
    Observable<Tags> getTags(@Header("Authorization") String authorizationHeader);

    //User/Authentication related APIs

    /**
     * Fetches access token. Returns an observable that emits {@link OAuth2AccessToken}
     *
     * @param body The request body
     * @return The access token
     */
    @POST("/oauth/token")
    Observable<OAuth2AccessToken> grantNewAccessToken(@Body AccessTokenRequestBody body);

    /**
     * Refreshes the access token. Returns an observable that emits {@link OAuth2AccessToken}
     *
     * @param body The request body
     * @return The access token
     */
    @POST("/oauth/token")
    Observable<OAuth2AccessToken> refreshAccessToken(@Body RefreshTokenRequestBody body);

    /**
     * Gets logged in user's profile
     *
     * @param authorizationHeader The access token header
     * @return An observable that emits {@link UserEntity}
     */
    @GET(USERS_ME)
    Observable<UserEntity> getUser(@Header("Authorization") String authorizationHeader);

    // GeoJSON related APIs

    /**
     * Fetches posts with GEOJSON data
     *
     * @param authorizationHeader The access token header
     * @return An JsonElement that contains the raw json string
     */
    @GET(GEOJSON)
    Observable<JsonElement> getGeoJson(@Header("Authorization") String authorizationHeader);

    // Form related APIs

    /**
     * Fetches forms associated with a deployment
     *
     * @param authorizationHeader The access token header
     * @return Forms
     */
    @GET(FORMS)
    Observable<Forms> getForms(@Header("Authorization") String authorizationHeader);
}
