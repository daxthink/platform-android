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

import com.ushahidi.android.core.task.ThreadExecutor;
import com.ushahidi.android.data.BuildConfig;
import com.ushahidi.android.data.entity.GeoJsonEntity;

import android.content.Context;
import android.util.Log;

import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonDatabaseHelper extends BaseDatabseHelper implements IGeoJsonDatabaseHelper {

    private static GeoJsonDatabaseHelper sInstance;

    private static String TAG = PostDatabaseHelper.class.getSimpleName();

    private GeoJsonDatabaseHelper(Context context, ThreadExecutor threadExecutor) {
        super(context, threadExecutor);
    }

    public static synchronized GeoJsonDatabaseHelper getsInstance(Context context,
            ThreadExecutor threadExecutor
    ) {
        if (sInstance == null) {
            sInstance = new GeoJsonDatabaseHelper(context, threadExecutor);
        }
        return sInstance;
    }

    @Override
    public void put(final GeoJsonEntity geoJsonEntity, final IGeoJsonPutCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                if (!isClosed()) {
                    try {
                        cupboard().withDatabase(getWritableDatabase()).put(geoJsonEntity);
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public void put(final List<GeoJsonEntity> geoJsonEntities, final IGeoJsonPutCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                if (!isClosed()) {
                    try {
                        cupboard().withDatabase(getWritableDatabase()).put(geoJsonEntities);
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public void getGeoJsonEntities(final long deploymentId, final IGeoJsonEntityCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                GeoJsonEntity postGeoJsonEntities = cupboard().withDatabase(
                        getReadableDatabase()).get(GeoJsonEntity.class, deploymentId);
                callback.onGeoJsonEntityLoaded(postGeoJsonEntities);
            }
        });
    }

    @Override
    public void deleteAll(final IGeoJsonEntityDeletedCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                if (!isClosed()) {
                    try {
                        final int numRows = cupboard().withDatabase(getWritableDatabase())
                                .delete(GeoJsonEntity.class, null);
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "delete all tag entities. Deleted " + numRows
                                    + " rows.");
                        }
                        callback.onGeoJsonEntityDeleted();
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        });
    }
}
