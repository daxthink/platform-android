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

package com.ushahidi.android.presentation.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ushahidi.android.data.api.ApiHeader;
import com.ushahidi.android.data.api.Date;
import com.ushahidi.android.data.api.DateDeserializer;
import com.ushahidi.android.data.api.ValueDeserializer;
import com.ushahidi.android.data.entity.PostValueEntity;
import com.ushahidi.android.presentation.exception.UnauthorizedAccessErrorHandler;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Http Client for accessing the API. It provides a generic method for creating a {@link
 * retrofit.RestAdapter} to work with the various API service interfaces
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ApiHttpClient {

    private UnauthorizedAccessErrorHandler mUnauthorizedAccessErrorHandler;

    private HttpClientWrap mHttpClientWrap;

    @Inject
    public ApiHttpClient(HttpClientWrap httpClientWrap,
            UnauthorizedAccessErrorHandler unauthorizedAccessErrorHandler) {
        mHttpClientWrap = httpClientWrap;
        mUnauthorizedAccessErrorHandler = unauthorizedAccessErrorHandler;
    }

    public <T> T createService(@NonNull Class<T> serviceClass, String baseUrl,
            final String accessToken) {
        ApiHeader header = new ApiHeader(accessToken);
        Endpoint endpoint = Endpoints.newFixedEndpoint(baseUrl);
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        builder.registerTypeAdapter(PostValueEntity.class, new ValueDeserializer());
        Gson gson = builder.create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setClient(mHttpClientWrap)
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(mUnauthorizedAccessErrorHandler)
                .setRequestInterceptor(header)
                .build();

        return restAdapter.create(serviceClass);
    }
}
