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

import com.ushahidi.android.data.entity.GeoJsonEntity;

/**
 * Represent a data source for which @{link GeoJsonEntity} data is retrieved
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface GeoJsonDatasource {

    /**
     * Get a list of {@link GeoJsonEntity}.
     *
     * @param deploymentId              An {@link com.ushahidi.android.core.entity.Deployment}
     * @param geoJsonEntityListCallback A {@link GeoJsonEntityListCallback} used for notifying
     *                                  clients
     *                                  about the status of the operation.
     */
    void getGeoJsonList(Long deploymentId, GeoJsonEntityListCallback geoJsonEntityListCallback);

    /**
     * Add/Update a {@link com.ushahidi.android.core.entity.Post}.
     *
     * @param geoJson         The GeoJson to be saved.
     * @param geoJsonCallback A {@link GeoJsonEntityAddCallback} used for notifying clients about
     *                        the status of the operation.
     * @author Ushahidi Team <team@ushahidi.com>
     */
    void putGeojson(GeoJsonEntity geoJson, GeoJsonEntityAddCallback geoJsonCallback);

    /**
     * Callback used for notifying the client when either a post list has been loaded successfully
     * or an error occurred during the process.
     */
    interface GeoJsonEntityListCallback {

        void onGeoJsonEntityListLoaded(GeoJsonEntity geoJsonEntity);

        void onError(Exception exception);
    }

    /**
     * Callback used for notifying the client when either a post has been successfully added to the
     * database or an error occurred during the process.
     */
    interface GeoJsonEntityAddCallback {

        void onGeoJsonEntityAdded();

        void onError(Exception exception);
    }
}
