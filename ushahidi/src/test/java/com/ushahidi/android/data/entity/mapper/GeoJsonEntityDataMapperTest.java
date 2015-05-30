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

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.domain.entity.GeoJson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link GeoJsonEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class GeoJsonEntityDataMapperTest {

    private GeoJsonEntityDataMapper mGeoJsonEntityMapper;

    private GeoJsonEntity mGeoJsonEntity;

    private GeoJson mGeoJson;

    private static final Long DUMMY_ID = 22l;

    private static final String DUMMY_GEOJSON = "dummy_goejson";

    private static final Long DUMMY_DEPLOYMENT_ID = 1l;

    @Before
    public void setUp() {
        mGeoJsonEntityMapper = new GeoJsonEntityDataMapper();
    }

    @Test
    public void shouldMapPostGeoJsonEntityToGeoJson() {
        mGeoJsonEntity = new GeoJsonEntity();
        mGeoJsonEntity._id = DUMMY_ID;
        mGeoJsonEntity.setGeoJson(DUMMY_GEOJSON);
        mGeoJsonEntity.setDeploymentId(DUMMY_DEPLOYMENT_ID);

        GeoJson mGeoJson = mGeoJsonEntityMapper.map(mGeoJsonEntity);

        assertThat(mGeoJson).isNotNull();
        assertThat(mGeoJson).isInstanceOf(GeoJson.class);
        assertThat(mGeoJson._id).isEqualTo(DUMMY_ID);
        assertThat(mGeoJson.getDeploymentId()).isNotNull();
        assertThat(mGeoJson.getDeploymentId()).isEqualTo(DUMMY_DEPLOYMENT_ID);
        assertThat(mGeoJson.getGeoJson()).isNotNull();
        assertThat(mGeoJson.getGeoJson()).isEqualTo(DUMMY_GEOJSON);
    }

    @Test
    public void shouldUnMapFromPostGeoJsonToPostGeoJsonEntity() {
        mGeoJson = new GeoJson();
        mGeoJson._id = DUMMY_ID;
        mGeoJson.setGeoJson(DUMMY_GEOJSON);
        mGeoJson.setDeploymentId(DUMMY_DEPLOYMENT_ID);

        GeoJsonEntity geoJsonEntity = mGeoJsonEntityMapper.map(mGeoJson);
        assertThat(geoJsonEntity).isNotNull();
        assertThat(geoJsonEntity).isInstanceOf(GeoJsonEntity.class);
        assertThat(geoJsonEntity._id).isEqualTo(DUMMY_ID);
        assertThat(geoJsonEntity.getDeploymentId()).isNotNull();
        assertThat(geoJsonEntity.getDeploymentId()).isEqualTo(DUMMY_DEPLOYMENT_ID);
        assertThat(geoJsonEntity.getGeoJson()).isNotNull();
        assertThat(geoJsonEntity.getGeoJson()).isEqualTo(DUMMY_GEOJSON);
    }
}
