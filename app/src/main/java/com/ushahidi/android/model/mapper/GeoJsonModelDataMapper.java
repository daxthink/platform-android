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

package com.ushahidi.android.model.mapper;

import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.model.GeoJsonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps and unmaps to and from {@link GeoJson} and {@link GeoJsonEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonModelDataMapper {

    /**
     * Maps {@link GeoJsonEntity} to {@link
     * GeoJson}
     *
     * @param geoJsonEntity The {@link GeoJson} to be
     *                      mapped
     * @return The {@link GeoJsonEntity} entity
     */
    public GeoJsonModel map(GeoJson geoJsonEntity) {
        GeoJsonModel postGeoJson = null;
        if (geoJsonEntity != null) {
            postGeoJson = new GeoJsonModel();
            postGeoJson.setId(geoJsonEntity.getId());
            postGeoJson.setGeoJson(geoJsonEntity.getGeoJson());
        }
        return postGeoJson;
    }

    /**
     * Unmaps {@link GeoJson} from {@link
     * GeoJsonEntity}
     *
     * @param postGeoJson The {@link GeoJsonEntity} to be
     *                    mapped
     * @return The {@link GeoJson} entity
     */
    public GeoJson unmap(GeoJsonModel postGeoJson) {
        GeoJson geoJsonEntity = null;
        if (postGeoJson != null) {
            geoJsonEntity = new GeoJson();
            geoJsonEntity.setId(postGeoJson.getId());
            geoJsonEntity.setGeoJson(postGeoJson.getGeoJson());
        }
        return geoJsonEntity;
    }

    /**
     * Maps a list {@link GeoJsonEntity} into a list of {@link GeoJson}.
     *
     * @param geoJsonEntityList List to be mapped.
     * @return {@link GeoJson}
     */
    public List<GeoJsonModel> map(List<GeoJson> geoJsonEntityList) {
        List<GeoJsonModel> postGeoJsonList = new ArrayList<>();
        GeoJsonModel postGeoJson;
        for (GeoJson geoJsonEntity : geoJsonEntityList) {
            postGeoJson = map(geoJsonEntity);
            if (postGeoJson != null) {
                postGeoJsonList.add(postGeoJson);
            }
        }
        return postGeoJsonList;
    }
}
