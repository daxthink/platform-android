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

package com.ushahidi.android.domain.entity;

import com.ushahidi.android.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link GeoJson}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class GeoJsonTest {

    private GeoJson mGeoJson;

    private static final Long DUMMY_ID = 1l;

    private static final Long DUMMY_DEPLOYMENT_ID = 1l;

    private static final String DUMMY_STRING = "Dummy Deployment Title";

    @Before
    public void setUp() {
        mGeoJson = new GeoJson();
    }

    @Test
    public void shouldSetGeoJsonProperties() {
        mGeoJson._id = DUMMY_ID;
        mGeoJson.setDeploymentId(DUMMY_DEPLOYMENT_ID);
        mGeoJson.setGeoJson(DUMMY_STRING);

        assertThat(mGeoJson).isNotNull();
        assertThat(mGeoJson).isInstanceOf(GeoJson.class);
        assertThat(mGeoJson._id).isEqualTo(DUMMY_ID);
        assertThat(mGeoJson.getDeploymentId()).isEqualTo(DUMMY_DEPLOYMENT_ID);
        assertThat(mGeoJson.getGeoJson()).isEqualTo(DUMMY_STRING);
    }
}
