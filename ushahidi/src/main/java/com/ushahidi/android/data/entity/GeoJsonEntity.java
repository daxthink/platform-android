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

package com.ushahidi.android.data.entity;

import com.addhen.android.raiburari.data.entity.DataEntity;

/**
 * GeoJson Data Entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonEntity extends DataEntity {

    private String mGeoJson;

    private Long mDeploymentId;

    public String getGeoJson() {
        return mGeoJson;
    }

    /**
     * Sets a GEOJSON string
     *
     * @param geoJson The geojson string
     */
    public void setGeoJson(String geoJson) {
        mGeoJson = geoJson;
    }

    /**
     * Gets a deployment ID
     *
     * @return The deployment id
     */
    public Long getDeploymentId() {
        return mDeploymentId;
    }

    /**
     * Sets the deployment Id
     *
     * @param deploymentId The deployment Id
     */
    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "GeoJsonModel{"
                + "mID='" + _id + '\''
                + "mDeploymentId='" + mDeploymentId + '\''
                + ", geojson='" + mGeoJson + '\''
                + '}';
    }
}
