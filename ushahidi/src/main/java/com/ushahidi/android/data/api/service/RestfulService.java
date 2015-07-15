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

import com.ushahidi.android.data.api.auth.AccessTokenRequestBody;
import com.ushahidi.android.data.api.auth.RefreshTokenRequestBody;
import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessToken;
import com.ushahidi.android.data.api.model.Posts;
import com.ushahidi.android.data.api.model.Tags;
import com.ushahidi.android.data.entity.UserEntity;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

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
    @GET(POSTS)
    Observable<Posts> posts(@Header("Authorization") String authorizationHeader);

    // Tags related APIs
    @GET(TAGS)
    Observable<Tags> getTags(@Header("Authorization") String authorizationHeader);

    //User/Authentication related APIs
    @POST("/oauth/token")
    Observable<OAuth2AccessToken> grantNewAccessToken(@Body AccessTokenRequestBody body);

    @POST("/oauth/token")
    Observable<OAuth2AccessToken> refreshAccessToken(@Body RefreshTokenRequestBody body);

    @GET(USERS_ME)
    Observable<UserEntity> getUser(@Header("Authorization") String authorizationHeader);

    // GeoJSON related APIs
    @GET(GEOJSON)
    Observable<JsonElement> getGeoJson(@Header("Authorization") String authorizationHeader);
}
