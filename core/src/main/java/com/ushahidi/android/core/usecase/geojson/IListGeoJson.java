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

package com.ushahidi.android.core.usecase.geojson;

import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.core.exception.ErrorWrap;
import com.ushahidi.android.core.usecase.IInteractor;

import java.util.List;

/**
 * Fetch {@link com.ushahidi.android.core.entity.Post} with GEOJSON data
 * from local cache. The database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface IListGeoJson extends IInteractor {
    /**
     * Executes this use case.
     *
     * @param deploymentId A {@link com.ushahidi.android.core.entity.Deployment} ID
     * @param callback     A {@link IListGeoJson.Callback} used to notify the client.
     */
    void execute(long deploymentId, Callback callback);

    /**
     * Notify client when a list of GEOJSON is successfully are successfully loaded or an error occurred in the
     * process.
     */
    interface Callback {

        void onGeoJsonLoaded(GeoJson geoJson);

        void onError(ErrorWrap error);
    }
}
