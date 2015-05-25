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

package com.ushahidi.android.data.repository;

import com.google.common.base.Preconditions;

import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.core.exception.ErrorWrap;
import com.ushahidi.android.core.repository.IGeoJsonRepository;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.data.entity.mapper.GeoJsonEntityMapper;
import com.ushahidi.android.data.exception.RepositoryError;
import com.ushahidi.android.data.repository.datasource.geojson.GeoJsonDataSourceFactory;
import com.ushahidi.android.data.repository.datasource.geojson.GeoJsonDatasource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonDataRepository implements IGeoJsonRepository {

    private static GeoJsonDataRepository sInstance;

    private final GeoJsonEntityMapper mGeoJsonEntityMapper;

    private final GeoJsonDataSourceFactory mGeoJsonDataSourceFactory;

    public GeoJsonDataRepository(GeoJsonDataSourceFactory geoJsonDataSourceFactory,
            GeoJsonEntityMapper geoJsonEntityMapper) {
        mGeoJsonDataSourceFactory = Preconditions
                .checkNotNull(geoJsonDataSourceFactory, "GeoJsonDataSource Factory cannot be null");
        mGeoJsonEntityMapper = Preconditions
                .checkNotNull(geoJsonEntityMapper, "Entity mapper cannot be null");
    }

    public static synchronized GeoJsonDataRepository getInstance(
            GeoJsonDataSourceFactory geoJsonDataSourceFactory,
            GeoJsonEntityMapper geoJsonEntityMapper) {
        if (sInstance == null) {
            sInstance = new GeoJsonDataRepository(geoJsonDataSourceFactory, geoJsonEntityMapper);
        }
        return sInstance;
    }

    @Override
    public void getGeoJsonList(long deploymentId, final GeoJsonListCallback geoJsonListCallback) {
        final GeoJsonDatasource geoJsonDatasource = mGeoJsonDataSourceFactory
                .createGeoJsonDatabaseDataSource();
        geoJsonDatasource.getGeoJsonList(deploymentId,
                new GeoJsonDatasource.GeoJsonEntityListCallback() {
                    @Override
                    public void onGeoJsonEntityListLoaded(GeoJsonEntity geoJsonEntity) {
                        geoJsonListCallback
                                .onGeoJsonListLoaded(mGeoJsonEntityMapper.map(geoJsonEntity));
                    }

                    @Override
                    public void onError(Exception exception) {
                        geoJsonListCallback.onError(new RepositoryError(exception));
                    }
                });
    }

    @Override
    public void getGeoJsonListViaApi(final long deploymentId,
            final GeoJsonListCallback geoJsonListCallback) {
        final GeoJsonDatasource geoJsonDatasource = mGeoJsonDataSourceFactory
                .createGeoJsonApiDataSource();

        geoJsonDatasource.getGeoJsonList(deploymentId,
                new GeoJsonDatasource.GeoJsonEntityListCallback() {
                    @Override
                    public void onGeoJsonEntityListLoaded(GeoJsonEntity geoJsonEntity) {
                        geoJsonEntity.setId(deploymentId);
                        geoJsonListCallback.onGeoJsonListLoaded(
                                mGeoJsonEntityMapper.map(geoJsonEntity));
                        //Cache to local db
                        putGeojson(mGeoJsonEntityMapper.map(geoJsonEntity),
                                new GeoJsonAddCallback() {
                                    @Override
                                    public void onGeoJsonAdded() {
                                        // Do nothing
                                    }

                                    @Override
                                    public void onError(ErrorWrap error) {
                                        // Do nothing
                                    }
                                });
                    }

                    @Override
                    public void onError(Exception exception) {
                        geoJsonListCallback.onError(new RepositoryError(exception));
                    }
                });
    }

    @Override
    public void putGeojson(final GeoJson geoJson, final GeoJsonAddCallback geoJsonCallback) {
        final GeoJsonDatasource geoJsonDatasource = mGeoJsonDataSourceFactory
                .createGeoJsonDatabaseDataSource();
        geoJsonDatasource.putGeojson(mGeoJsonEntityMapper.unmap(geoJson),
                new GeoJsonDatasource.GeoJsonEntityAddCallback() {
                    @Override
                    public void onGeoJsonEntityAdded() {
                        geoJsonCallback.onGeoJsonAdded();
                    }

                    @Override
                    public void onError(Exception exception) {
                        geoJsonCallback.onError(new RepositoryError(exception));
                    }
                });
    }
}
