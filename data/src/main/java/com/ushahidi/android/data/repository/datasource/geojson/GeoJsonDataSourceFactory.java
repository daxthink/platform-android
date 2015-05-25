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

import com.google.common.base.Preconditions;

import com.ushahidi.android.data.api.GeoJsonApi;
import com.ushahidi.android.data.api.service.GeoJsonService;
import com.ushahidi.android.data.database.GeoJsonDatabaseHelper;

import android.content.Context;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonDataSourceFactory {

    private final Context mContext;

    private final GeoJsonDatabaseHelper mGeoDatabaseHelper;

    private GeoJsonService mGeoJsonService;

    public GeoJsonDataSourceFactory(Context context, GeoJsonDatabaseHelper geoJsonDatabaseHelper) {
        mContext = Preconditions.checkNotNull(context, "Context cannot be null.");
        mGeoDatabaseHelper = Preconditions
                .checkNotNull(geoJsonDatabaseHelper, "GeoJsonDatabaseHelper cannot be null");
    }

    public void setGeoJsonService(GeoJsonService geoJsonService) {
        mGeoJsonService = geoJsonService;
    }

    public GeoJsonDatasource createGeoJsonApiDataSource() {
        Preconditions.checkNotNull(mGeoJsonService,
                "GeoJsonService cannot be null, call setGeoJsonService(...)");
        final GeoJsonApi geoJsonApi = new GeoJsonApi(mContext, mGeoJsonService);
        return new GeoJsonApiDataSource(geoJsonApi);
    }

    public GeoJsonDatasource createGeoJsonDatabaseDataSource() {
        return new GeoJsonDatabaseDataSource(mGeoDatabaseHelper);
    }

}
