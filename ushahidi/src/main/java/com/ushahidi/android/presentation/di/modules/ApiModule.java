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

package com.ushahidi.android.presentation.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ushahidi.android.data.api.ApiHeader;
import com.ushahidi.android.data.api.Date;
import com.ushahidi.android.data.api.DateDeserializer;
import com.ushahidi.android.data.api.ValueDeserializer;
import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.PostValueEntity;
import com.ushahidi.android.presentation.exception.UnauthorizedAccessErrorHandler;
import com.ushahidi.android.presentation.net.HttpClientWrap;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class ApiModule {

    String mBaseUrl;

    String mAccessToken;

    public ApiModule() {
    }

    public ApiModule(String baseUrl, String accessToken) {
        mBaseUrl = baseUrl;
        mAccessToken = accessToken;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(HttpClientWrap httpClientWrap,
            UnauthorizedAccessErrorHandler unauthorizedAccessErrorHandler) {
        ApiHeader header = new ApiHeader(mAccessToken);
        Endpoint endpoint = Endpoints.newFixedEndpoint(mBaseUrl);
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        builder.registerTypeAdapter(PostValueEntity.class, new ValueDeserializer());
        Gson gson = builder.create();
        return new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setClient(httpClientWrap)
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(unauthorizedAccessErrorHandler)
                .setRequestInterceptor(header)
                .build();

    }

    @Provides
    @Singleton
    RestfulService provideRestfulService(RestAdapter restAdapter) {
        return restAdapter.create(RestfulService.class);
    }
}
