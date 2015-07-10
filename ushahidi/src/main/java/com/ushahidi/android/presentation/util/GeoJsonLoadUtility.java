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

package com.ushahidi.android.presentation.util;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.Geometry;
import com.cocoahero.android.geojson.GeometryCollection;
import com.cocoahero.android.geojson.LineString;
import com.cocoahero.android.geojson.MultiLineString;
import com.cocoahero.android.geojson.MultiPoint;
import com.cocoahero.android.geojson.MultiPolygon;
import com.cocoahero.android.geojson.Point;
import com.cocoahero.android.geojson.Polygon;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.presentation.model.ClusterMarkerModel;

import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Ported from https://goo.gl/wENBL7 to make it work with Android native maps
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonLoadUtility {

    /**
     * Load GeoJSON from URL (in synchronous manner) and return GeoJSON FeatureCollection
     *
     * @param geojsonText of file in assets directory
     * @return the parsed getGeoJson as a featurecollection
     */
    public static FeatureCollection parseGeoJson(
            final String geojsonText) throws JSONException {
        if (TextUtils.isEmpty(geojsonText)) {
            throw new NullPointerException("Please provide a valid Geojson.");
        }

        if (BuildConfig.DEBUG) {
            Timber.d(GeoJsonLoadUtility.class.getCanonicalName(),
                    "Loading GeoJSON URL: " + geojsonText);
        }

        FeatureCollection parsed = (FeatureCollection) GeoJSON.parse(geojsonText);
        if (BuildConfig.DEBUG) {
            Timber.d(GeoJsonLoadUtility.class.getCanonicalName(),
                    "Parsed GeoJSON with " + parsed.getFeatures().size() + " features.");
        }

        return parsed;
    }

    /**
     * Converts GeoJSON objects into UI Objects to be rendered on Google Maps
     *
     * @param featureCollection Parsed GeoJSON Objects
     * @param fillColor         Optional fill color
     * @param strokeColor       Optional stroke color
     * @return Collection of Mapbox SDK UI Objects
     */
    public static ArrayList<Object> createUIObjectsFromGeoJSONObjects(
            final FeatureCollection featureCollection, @ColorInt final int fillColor,
            @ColorInt final int strokeColor)
            throws JSONException {
        ArrayList<Object> uiObjects = new ArrayList<>();

        for (Feature f : featureCollection.getFeatures()) {
            // Parse Into UI Objections
            long id = 0;
            if (f.getProperties().has("id")) {
                id = f.getProperties().getLong("id");
            }
            final String title = f.getProperties().getString("title");
            final String description = f.getProperties().getString("description");
            if (f.getGeometry() instanceof GeometryCollection) {
                GeometryCollection geometryCollection = (GeometryCollection) f.getGeometry();

                for (Geometry geometry : geometryCollection.getGeometries()) {
                    deserializeGeometry(geometry, id, title, description, uiObjects, fillColor,
                            strokeColor);
                }
            } else {
                deserializeGeometry(f.getGeometry(), id, title, description, uiObjects, fillColor,
                        strokeColor);
            }
        }

        return uiObjects;
    }

    private static void deserializeGeometry(Geometry geometry, long id, String title,
            String description, ArrayList<Object> uiObjects, final int fillColor,
            final int strokeColor) throws JSONException {
        int j;

        if (geometry instanceof Point) {
            JSONArray coordinates = (JSONArray) geometry.toJSON().get("coordinates");
            double lon = coordinates.getDouble(0);
            double lat = coordinates.getDouble(1);
            ClusterMarkerModel clusterMarkerModel = new ClusterMarkerModel();
            clusterMarkerModel._id = id;
            clusterMarkerModel.latitude = lat;
            clusterMarkerModel.longitude = lon;
            clusterMarkerModel.title = title;
            clusterMarkerModel.description = description;
            uiObjects.add(clusterMarkerModel);
        } else if (geometry instanceof MultiPoint) {
            JSONArray points = (JSONArray) geometry.toJSON().get("coordinates");
            for (j = 0; j < points.length(); j++) {
                JSONArray coordinates = (JSONArray) points.get(j);
                double lon = coordinates.getDouble(0);
                double lat = coordinates.getDouble(1);
                ClusterMarkerModel clusterMarkerModel = new ClusterMarkerModel();
                clusterMarkerModel._id = id;
                clusterMarkerModel.latitude = lat;
                clusterMarkerModel.longitude = lon;
                clusterMarkerModel.title = title;
                clusterMarkerModel.description = description;
                uiObjects.add(clusterMarkerModel);
            }
        } else if (geometry instanceof LineString) {
            List<LatLng> latLngs = new ArrayList<>();
            JSONArray points = (JSONArray) geometry.toJSON().get("coordinates");
            JSONArray coordinates;
            for (j = 0; j < points.length(); j++) {
                coordinates = (JSONArray) points.get(j);
                double lon = coordinates.getDouble(0);
                double lat = coordinates.getDouble(1);
                latLngs.add(new LatLng(lat, lon));
            }
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(latLngs);
            polylineOptions.width(5);
            if (strokeColor > 0) {
                polylineOptions.color(strokeColor);
            } else {
                polylineOptions.color(Color.RED);
            }
            uiObjects.add(polylineOptions);
        } else if (geometry instanceof MultiLineString) {
            JSONArray lines = (JSONArray) geometry.toJSON().get("coordinates");
            for (int k = 0; k < lines.length(); k++) {
                PolylineOptions polylineOptions = new PolylineOptions();
                JSONArray points = (JSONArray) lines.get(k);
                JSONArray coordinates;
                for (j = 0; j < points.length(); j++) {
                    coordinates = (JSONArray) points.get(j);
                    double lon = coordinates.getDouble(0);
                    double lat = coordinates.getDouble(1);
                    polylineOptions.add(new LatLng(lat, lon));
                }
                polylineOptions.width(5);
                if (strokeColor > 0) {
                    polylineOptions.color(strokeColor);
                } else {
                    polylineOptions.color(Color.RED);
                }
                uiObjects.add(polylineOptions);
            }
        } else if (geometry instanceof Polygon) {
            PolygonOptions polygonOptions = new PolygonOptions();
            JSONArray points = (JSONArray) geometry.toJSON().get("coordinates");

            for (int r = 0; r < points.length(); r++) {
                JSONArray ring = (JSONArray) points.get(r);
                JSONArray coordinates;

                // we re-wind inner rings of GeoJSON polygons in order
                // to render them as transparent in the canvas layer.

                // first ring should have windingOrder = true,
                // all others should have winding order == false
                if ((r == 0 && !windingOrder(ring)) || (r != 0 && windingOrder(ring))) {
                    for (j = 0; j < ring.length(); j++) {
                        coordinates = (JSONArray) ring.get(j);
                        double lon = coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);
                        polygonOptions.add(new LatLng(lat, lon));
                    }
                } else {
                    for (j = ring.length() - 1; j >= 0; j--) {
                        coordinates = (JSONArray) ring.get(j);
                        double lon = coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);
                        polygonOptions.add(new LatLng(lat, lon));
                    }
                }

                if (strokeColor > 0) {
                    polygonOptions.strokeColor(fillColor);
                } else {
                    polygonOptions.strokeColor(Color.RED);
                }

                if (fillColor > 0) {
                    polygonOptions.fillColor(fillColor);
                } else {
                    polygonOptions.fillColor(Color.RED);
                }

                uiObjects.add(polygonOptions);
            }
        } else if (geometry instanceof MultiPolygon) {
            PolygonOptions polygonOptions = new PolygonOptions();
            JSONArray polygons = (JSONArray) geometry.toJSON().get("coordinates");

            for (int p = 0; p < polygons.length(); p++) {
                JSONArray points = (JSONArray) polygons.get(p);
                for (int r = 0; r < points.length(); r++) {
                    JSONArray ring = (JSONArray) points.get(r);
                    JSONArray coordinates;

                    // we re-wind inner rings of GeoJSON polygons in order
                    // to render them as transparent in the canvas layer.

                    // first ring should have windingOrder = true,
                    // all others should have winding order == false
                    if ((r == 0 && !windingOrder(ring)) || (r != 0 && windingOrder(ring))) {
                        for (j = 0; j < ring.length(); j++) {
                            coordinates = (JSONArray) ring.get(j);
                            double lon = coordinates.getDouble(0);
                            double lat = coordinates.getDouble(1);
                            polygonOptions.add(new LatLng(lat, lon));
                        }
                    } else {
                        for (j = ring.length() - 1; j >= 0; j--) {
                            coordinates = (JSONArray) ring.get(j);
                            double lon = coordinates.getDouble(0);
                            double lat = coordinates.getDouble(1);
                            polygonOptions.add(new LatLng(lat, lon));
                        }
                    }

                    if (strokeColor > 0) {
                        polygonOptions.strokeColor(fillColor);
                    } else {
                        polygonOptions.strokeColor(Color.RED);
                    }

                    if (fillColor > 0) {
                        polygonOptions.fillColor(fillColor);
                    } else {
                        polygonOptions.fillColor(Color.RED);
                    }
                    uiObjects.add(polygonOptions);
                }
            }
        }
    }

    private static boolean windingOrder(JSONArray ring) throws JSONException {
        float area = 0;

        if (ring.length() > 2) {
            for (int i = 0; i < ring.length() - 1; i++) {
                JSONArray p1 = (JSONArray) ring.get(i);
                JSONArray p2 = (JSONArray) ring.get(i + 1);
                area += rad((Double) p2.get(0) - (Double) p1.get(0)) * (2 + Math.sin(
                        rad((Double) p1.get(1))) + Math.sin(rad((Double) p2.get(1))));
            }
        }

        return area > 0;
    }

    private static double rad(double val) {
        return val * Math.PI / 180f;
    }
}
