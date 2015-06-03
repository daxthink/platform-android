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

package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.domain.entity.GeoJson;
import com.ushahidi.android.presentation.model.GeoJsonModel;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonModelDataMapper {

    @Inject
    public GeoJsonModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link GeoJson} to {@link
     * GeoJson}
     *
     * @param geoJson The {@link GeoJson} to be
     *                mapped
     * @return The {@link GeoJson} entity
     */
    public GeoJsonModel map(@NonNull GeoJson geoJson) {
        GeoJsonModel geoJsonModel = new GeoJsonModel();
        if (geoJson != null) {
            geoJsonModel._id = geoJson._id;
            geoJsonModel.setGeoJson(geoJson.getGeoJson());
            geoJsonModel.setDeploymentId(geoJson.getDeploymentId());
        }
        return geoJsonModel;
    }

    /**
     * Unmaps {@link GeoJson} from {@link
     * GeoJson}
     *
     * @param geoJsonModel The {@link GeoJson} to be
     *                     mapped
     * @return The {@link GeoJson} entity
     */
    public GeoJson map(GeoJsonModel geoJsonModel) {
        GeoJson geoJson = new GeoJson();
        if (geoJson != null) {
            geoJson._id = geoJsonModel._id;
            geoJson.setGeoJson(geoJsonModel.getGeoJson());
            geoJson.setDeploymentId(geoJsonModel.getDeploymentId());
        }
        return geoJson;
    }
}
