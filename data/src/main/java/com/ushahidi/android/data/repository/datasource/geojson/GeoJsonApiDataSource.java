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

package com.ushahidi.android.data.repository.datasource.geojson;

import com.ushahidi.android.data.api.GeoJsonApi;
import com.ushahidi.android.data.entity.GeoJsonEntity;

/**
 * Fetches Data via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonApiDataSource implements GeoJsonDatasource {

    private final GeoJsonApi mGeoJsonApi;

    public GeoJsonApiDataSource(GeoJsonApi geoJsonApi) {
        mGeoJsonApi = geoJsonApi;
    }

    @Override
    public void getGeoJsonList(final Long deploymentId,
            final GeoJsonEntityListCallback geoJsonEntityListCallback) {
        mGeoJsonApi.getGeoJsonList(new GeoJsonApi.GeoJsonListCallback() {
            @Override
            public void onGeoJsonListLoaded(GeoJsonEntity geoJsons) {
                geoJsonEntityListCallback.onGeoJsonEntityListLoaded(geoJsons);
            }

            @Override
            public void onError(Exception e) {
                geoJsonEntityListCallback.onError(e);
            }
        });
    }

    @Override
    public void putGeojson(GeoJsonEntity geoJson,
            GeoJsonEntityAddCallback geoJsonCallback) {
    }
}
