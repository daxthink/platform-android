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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.BuildConfig;
import com.ushahidi.android.data.entity.GeoJsonEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class GeoJsonEntityMapperTest extends BaseTestCase {

    private GeoJsonEntityMapper mGeoJsonEntityMapper;

    private GeoJsonEntity mGeoJsonEntity;

    private GeoJson mGeoJson;

    private static final Long DUMMY_ID = 22l;

    private static final String DUMMY_GEOJSON = "dummy_goejson";

    @Before
    public void setUp() throws Exception {
        mGeoJsonEntityMapper = new GeoJsonEntityMapper();
    }

    @Test
    public void shouldMapPostGeoJsonEntityToPostGeoJson() throws Exception {
        mGeoJsonEntity = new GeoJsonEntity();
        mGeoJsonEntity.setId(DUMMY_ID);
        mGeoJsonEntity.setGeoJson(DUMMY_GEOJSON);

        GeoJson mGeoJson = mGeoJsonEntityMapper.map(mGeoJsonEntity);

        assertThat(mGeoJson, is(instanceOf(GeoJson.class)));
        assertThat(mGeoJson.getId(), is(DUMMY_ID));
        assertThat(mGeoJson.getGeoJson(), is(DUMMY_GEOJSON));
    }

    @Test
    public void shouldUnMapFromPostGeoJsonToPostGeoJsonEntity() throws Exception {
        mGeoJson = new GeoJson();
        mGeoJson.setId(DUMMY_ID);
        mGeoJson.setGeoJson(DUMMY_GEOJSON);

        GeoJsonEntity geoJsonEntity = mGeoJsonEntityMapper.unmap(mGeoJson);
        assertThat(geoJsonEntity, is(instanceOf(GeoJsonEntity.class)));
        assertThat(geoJsonEntity.getId(), is(DUMMY_ID));
        assertThat(geoJsonEntity.getGeoJson(), is(DUMMY_GEOJSON));
    }
}
