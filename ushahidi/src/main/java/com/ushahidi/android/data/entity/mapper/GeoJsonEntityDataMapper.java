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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.domain.entity.GeoJson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonEntityDataMapper {

    @Inject
    public GeoJsonEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link GeoJsonEntity} to {@link
     * GeoJson}
     *
     * @param geoJsonEntity The {@link GeoJson} to be
     *                      mapped
     * @return The {@link GeoJsonEntity} entity
     */
    public GeoJson map(GeoJsonEntity geoJsonEntity) {
        GeoJson geoJson = new GeoJson();
        if (geoJsonEntity != null) {
            geoJson._id = geoJsonEntity._id;
            geoJson.setGeoJson(geoJsonEntity.getGeoJson());
            geoJson.setDeploymentId(geoJsonEntity.getDeploymentId());
        }
        return geoJson;
    }

    /**
     * Unmaps {@link GeoJson} from {@link
     * GeoJsonEntity}
     *
     * @param geoJson The {@link GeoJsonEntity} to be
     *                mapped
     * @return The {@link GeoJson} entity
     */
    public GeoJsonEntity map(GeoJson geoJson) {
        GeoJsonEntity geoJsonEntity = new GeoJsonEntity();
        if (geoJson != null) {
            geoJsonEntity._id = geoJson._id;
            geoJsonEntity.setGeoJson(geoJson.getGeoJson());
            geoJsonEntity.setDeploymentId(geoJson.getDeploymentId());
        }
        return geoJsonEntity;
    }

    /**
     * Maps a list {@link GeoJsonEntity} into a list of {@link GeoJson}.
     *
     * @param geoJsonEntityList List to be mapped.
     * @return {@link GeoJson}
     */
    public List<GeoJson> map(List<GeoJsonEntity> geoJsonEntityList) {
        List<GeoJson> geoJsonList = new ArrayList<>();
        GeoJson geoJson;
        for (GeoJsonEntity geoJsonEntity : geoJsonEntityList) {
            geoJson = map(geoJsonEntity);
            if (geoJson != null) {
                geoJsonList.add(geoJson);
            }
        }
        return geoJsonList;
    }
}
