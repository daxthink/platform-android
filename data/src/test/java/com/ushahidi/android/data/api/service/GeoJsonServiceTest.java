/*
 *  Copyright (c) 2015 Ushahidi.
 *
 *   This program is free software: you can redistribute it and/or modify it under
 *   the terms of the GNU Affero General Public License as published by the Free
 *   Software Foundation, either version 3 of the License, or (at your option)
 *   any later version.
 *
 *   This program is distributed in the hope that it will be useful, but WITHOUT
 *   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *   FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program in the file LICENSE-AGPL. If not, see
 *   https://www.gnu.org/licenses/agpl-3.0.html
 *
 */

package com.ushahidi.android.data.api.service;

import com.google.gson.JsonElement;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.ushahidi.android.data.BuildConfig;
import com.ushahidi.android.data.api.BaseApiTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import static com.ushahidi.android.data.TestHelper.getResource;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class GeoJsonServiceTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyFetchPostGeoJson() throws IOException {
        mMockWebServer.start();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setExecutors(httpExecutor, callbackExecutor)
                .setConverter(new GsonConverter(mGson))
                .setEndpoint(mMockWebServer.getUrl("/").toString()).build();

        final String postGeoJson = getResource("geojsons.json");
        GeoJsonService geoJsonService = restAdapter.create(GeoJsonService.class);
        mMockWebServer.enqueue(new MockResponse().setBody(postGeoJson));
        geoJsonService.getGeoJson(new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                assertNotNull(jsonElement);
                //TODO properly assert the value
            }

            @Override
            public void failure(RetrofitError error) {
                assertThat(error, nullValue());
            }
        });

        mMockWebServer.shutdown();
    }
}
