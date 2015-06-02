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

import com.google.gson.JsonElement;

import com.ushahidi.android.data.api.service.GeoJsonService;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.data.exception.NetworkConnectionException;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;

/**
 * GeoJson API related activities
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonApi {

    private final Context mContext;

    private final GeoJsonService mGeoJsonService;

    @Inject
    public GeoJsonApi(@NonNull Context context, @NonNull GeoJsonService geoJsonService) {
        mContext = context;
        mGeoJsonService = geoJsonService;
    }

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link GeoJsonEntity}.
     *
     * @param deploymentId The ID of the deployment the GeoJson is fetched from.
     */
    public Observable<GeoJsonEntity> getGeoJson(Long deploymentId) {
        return Observable.create((subscriber) -> {
            if (isDeviceConnectedToInternet(mContext)) {
                mGeoJsonService.getGeoJson()
                        .map((jsonElement) -> setGeoJson(deploymentId, jsonElement));
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    // Workaround for testing static method for checking status of data
    // connection on the device.
    public boolean isDeviceConnectedToInternet(Context context) {
        return ApiUtil.isDeviceConnectedToInternet(context);
    }

    /**
     * Sets the {@link GeoJsonEntity} entity properties from the {@link JsonElement}
     *
     * @param deploymentId The ID of the deployment the GeoJson is fetched from.
     * @param jsonElement  The jsonElement to retrieve the raw JSON string from.
     */
    private Observable<GeoJsonEntity> setGeoJson(Long deploymentId, JsonElement jsonElement) {
        return Observable.create(subscriber -> {
            GeoJsonEntity geoJsonEntity = new GeoJsonEntity();
            geoJsonEntity.setDeploymentId(deploymentId);
            geoJsonEntity.setGeoJson(jsonElement.toString());
            subscriber.onNext(geoJsonEntity);
            subscriber.onCompleted();
        });
    }
}
