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

package com.ushahidi.android.data.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonModel implements Serializable {

    private static final long serialVersionUID = -2160935700493053283L;

    private static class Geometry {
        private List<Geometries> geometries;

        private static class Geometries {
            private Double coordinates[];
        }
    }

    private static class Properties {
        private String title;
        private Long id;
        private String description;
    }

    private Geometry geometry;

    private Properties properties;

    public Long getPostId() {
        return properties.id;
    }

    public String getTitle() {
        return properties.title;
    }

    public String getDescription() {
        return properties.description;
    }

    public Double[] getCoordinates() {
        if (geometry.geometries != null && geometry.geometries.size() > 0) {
            return geometry.geometries.get(0).coordinates;
        }
        return new Double[]{};
    }
}
