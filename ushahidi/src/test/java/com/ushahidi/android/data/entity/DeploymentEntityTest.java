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

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.domain.entity.Deployment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link DeploymentEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DeploymentEntityTest {

    private DeploymentEntity mDeploymentEntity;

    private static final Long DUMMY_ID = 1l;

    private static final String DUMMY_TITLE = "Dummy Deployment Title";

    private static final DeploymentEntity.Status DUMMY_STATUS = DeploymentEntity.Status.DEACTIVATED;

    private static final String DUMMY_URL = "http://deployment.com";

    @Before
    public void setUp() {
        mDeploymentEntity = new DeploymentEntity();
    }

    @Test
    public void shouldSetDeploymentEntityProperities() {
        mDeploymentEntity._id = DUMMY_ID;
        mDeploymentEntity.setTitle(DUMMY_TITLE);
        mDeploymentEntity.setStatus(DUMMY_STATUS);
        mDeploymentEntity.setUrl(DUMMY_URL);

        assertThat(mDeploymentEntity).isNotNull();
        assertThat(mDeploymentEntity).isInstanceOf(Deployment.class);
        assertThat(mDeploymentEntity._id).isEqualTo(DUMMY_ID);
        assertThat(mDeploymentEntity.getTitle()).isSameAs(DUMMY_TITLE);
        assertThat(mDeploymentEntity.getStatus()).isEqualTo(DUMMY_STATUS);
        assertThat(mDeploymentEntity.getUrl()).isEqualTo(DUMMY_URL);
    }
}
