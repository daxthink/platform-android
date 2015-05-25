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

package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.GeoJsonEntity;

import java.util.List;

/**
 * All database queries for the {@link GeoJsonEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface IGeoJsonDatabaseHelper {

    void put(GeoJsonEntity geoJsonEntity, IGeoJsonPutCallback callback);

    void put(List<GeoJsonEntity> geoJsonEntities, IGeoJsonPutCallback callback);

    void getGeoJsonEntities(long deploymentId, IGeoJsonEntityCallback callback);

    void deleteAll(IGeoJsonEntityDeletedCallback callback);

    interface IGeoJsonPutCallback {

        void onGeoJsonEntityPut();

        void onError(Exception exception);
    }

    interface IGeoJsonEntityCallback {

        void onGeoJsonEntityLoaded(GeoJsonEntity postGeoJsonEntity);

        void onError(Exception exception);
    }

    interface IGeoJsonEntityDeletedCallback {

        void onGeoJsonEntityDeleted();

        void onError(Exception exception);
    }
}
