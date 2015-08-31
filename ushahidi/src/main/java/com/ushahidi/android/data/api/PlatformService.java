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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.PostValueEntity;
import com.ushahidi.android.data.exception.RetrofitErrorHandler;
import com.ushahidi.android.presentation.net.HttpClientWrap;

import javax.inject.Inject;

import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Creates a Restful Service based on the active
 * {@link com.ushahidi.android.domain.entity.Deployment} Active deployment is the one currently
 * selected and its data being viewed.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PlatformService {

    private RetrofitErrorHandler mUnauthorizedAccessErrorHandler;

    private HttpClientWrap mClient;

    private PrefsFactory mPrefsFactory;

    private GsonConverter mGsonConverter;

    /**
     * Default constructor
     *
     * @param client                         The http client
     * @param unauthorizedAccessErrorHandler The unauthorized access error handler
     * @param prefsFactory                   The SharedPreference factory
     */
    @Inject
    public PlatformService(HttpClientWrap client,
            RetrofitErrorHandler unauthorizedAccessErrorHandler,
            PrefsFactory prefsFactory) {
        mClient = client;
        mUnauthorizedAccessErrorHandler = unauthorizedAccessErrorHandler;
        mPrefsFactory = prefsFactory;
        initializeGson();
    }

    private void initializeGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        builder.registerTypeAdapter(PostValueEntity.class, new ValueDeserializer());
        Gson gson = builder.create();
        mGsonConverter = new GsonConverter(gson);
    }

    /**
     * Creates {@link RestfulService} using the active deployments URL and the accessToken
     * currently saved in the SharedPreferences.
     *
     * @return The {@link RestfulService}
     */
    public RestfulService getService() {
        Endpoint endpoint = Endpoints
                .newFixedEndpoint(mPrefsFactory.getActiveDeploymentUrl().get());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(mGsonConverter)
                .setClient(mClient)
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(mUnauthorizedAccessErrorHandler)
                .build();
        return restAdapter.create(RestfulService.class);
    }
}
