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

import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.entity.GeoJsonEntity;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;

/**
 * GeoJson API related services
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonApi {

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param ushAccessTokenManager The access token manager
     */
    @Inject
    public GeoJsonApi(@NonNull UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link GeoJsonEntity}.
     *
     * @return {@link GeoJsonEntity} Observable
     */
    public Observable<GeoJsonEntity> getGeoJson() {
        return mUshAccessTokenManager.getValidAccessToken()
                .concatMap(
                        authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                                .getGeoJson(authorizationHeader)
                                .flatMap(this::setGeoJson));

    }

    /**
     * Sets the {@link GeoJsonEntity} entity properties from the {@link JsonElement}
     *
     * @param jsonElement The jsonElement to retrieve the raw JSON string from.
     */
    private Observable<GeoJsonEntity> setGeoJson(JsonElement jsonElement) {
        GeoJsonEntity geoJsonEntity = new GeoJsonEntity();
        geoJsonEntity.setGeoJson(jsonElement.toString());
        return Observable.just(geoJsonEntity);
    }
}
