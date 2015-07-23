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

package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.data.exception.GeoJsonNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Database helper for manipulating data cached on the device for {@link
 * com.ushahidi.android.data.entity.GeoJsonEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class GeoJsonDatabaseHelper extends BaseDatabaseHelper {

    /**
     * Default constructor
     *
     * @param context The calling context. Cannot be a null value
     */
    @Inject
    public GeoJsonDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Gets {@link GeoJsonEntity} from the database.
     *
     * @param deploymentId The deployment ID to be used for fetching the {@link GeoJsonEntity}
     * @return An Observable that emits {@link GeoJsonEntity}
     */
    public Observable<GeoJsonEntity> getGeoJson(Long deploymentId) {
        return Observable.create(subscriber -> {
            final GeoJsonEntity geoJsonEntity = cupboard()
                    .withDatabase(getReadableDatabase()).query(GeoJsonEntity.class)
                    .withSelection("mDeploymentId = ?", String.valueOf(deploymentId)).get();
            if (geoJsonEntity != null) {
                subscriber.onNext(geoJsonEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new GeoJsonNotFoundException());
            }
        });
    }

    /**
     * Adds / Update {@link GeoJsonEntity} in the database.
     *
     * @param geoJsonEntity The {@link GeoJsonEntity} to be added / updated.
     * @return The row affected
     */
    public Observable<Long> putGeoJson(GeoJsonEntity geoJsonEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Long row = null;
                try {
                    row = cupboard().withDatabase(getWritableDatabase()).put(geoJsonEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(row);
                subscriber.onCompleted();
            }
        });
    }
}
