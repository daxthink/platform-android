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

package com.ushahidi.android.data.repository.datasource.geojson;

import com.ushahidi.android.data.api.GeoJsonApi;
import com.ushahidi.android.data.api.service.GeoJsonService;
import com.ushahidi.android.data.database.GeoJsonDatabaseHelper;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * For creating the object of the various data source for {@link com.ushahidi.android.data.entity.GeoJsonEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonDataSourceFactory {

    private final Context mContext;

    private final GeoJsonDatabaseHelper mGeoDatabaseHelper;

    private GeoJsonService mGeoJsonService;

    @Inject
    public GeoJsonDataSourceFactory(@NonNull Context context,
            @NonNull GeoJsonDatabaseHelper geoJsonDatabaseHelper) {
        mContext = context;
        mGeoDatabaseHelper = geoJsonDatabaseHelper;
    }

    /**
     * Call this to set the GeoJson API service
     *
     * @param geoJsonService The GeoJsonService to use for fetching communicating with the API
     */
    public void setGeoJsonService(@NonNull GeoJsonService geoJsonService) {
        mGeoJsonService = geoJsonService;
    }

    public GeoJsonDataSource createGeoJsonApiDataSource() {
        final GeoJsonApi geoJsonApi = new GeoJsonApi(mContext, mGeoJsonService);
        return new GeoJsonApiDataSource(geoJsonApi, mGeoDatabaseHelper);
    }

    public GeoJsonDataSource createGeoJsonDatabaseDataSource() {
        return new GeoJsonDatabaseDataSource(mGeoDatabaseHelper);
    }
}
