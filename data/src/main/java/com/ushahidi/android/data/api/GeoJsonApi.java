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

package com.ushahidi.android.data.api;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;

import com.ushahidi.android.data.api.service.GeoJsonService;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.data.exception.NetworkConnectionException;

import android.content.Context;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonApi implements IGeoJsonApi {

    private final Context mContext;

    private final GeoJsonService mGeoJsonService;

    public GeoJsonApi(Context context, GeoJsonService geoJsonService) {
        mContext = Preconditions.checkNotNull(context, "Context cannot be null");
        mGeoJsonService = Preconditions
                .checkNotNull(geoJsonService, "GeoJsonSerivce cannot be null.");
    }

    @Override
    public void getGeoJsonList(final GeoJsonListCallback postGeoJsonListCallback) {
        Preconditions.checkNotNull(postGeoJsonListCallback);
        if (isDeviceConnectedToInternet(mContext)) {
            mGeoJsonService.getGeoJson(new Callback<JsonElement>() {
                @Override
                public void success(final JsonElement jsonElement, Response response) {

                    GeoJsonEntity geoJsonEntity = new GeoJsonEntity();
                    geoJsonEntity.setGeoJson(jsonElement.toString());
                    postGeoJsonListCallback.onGeoJsonListLoaded(geoJsonEntity);
                }

                @Override
                public void failure(RetrofitError error) {
                    postGeoJsonListCallback.onError(error);
                }
            });
        } else {
            postGeoJsonListCallback.onError(new NetworkConnectionException());
        }
    }

    // Workaround for testing the static method for checking status of data
    // connection on the device
    public boolean isDeviceConnectedToInternet(Context context) {
        return ApiUtil.isDeviceConnectedToInternet(context);
    }
}
