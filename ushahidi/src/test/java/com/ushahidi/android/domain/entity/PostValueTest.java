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
 * Tests {@link PostValue}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class PostValueTest {

    private static String VALUES = "values";

    private static Long DEPLOYMENT_ID = 1l;

    private PostValue mPostValue;

    @Before
    public void setUp() throws Exception {
        mPostValue = new PostValue();
    }

    @Test
    public void shouldSetPostValue() {
        mPostValue.setValues(VALUES);
        mPostValue.setDeploymentId(DEPLOYMENT_ID);

        assertThat(mPostValue.getValues()).isEqualTo(VALUES);
        assertThat(mPostValue.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
    }
}
