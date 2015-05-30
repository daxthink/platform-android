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
import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.domain.entity.Deployment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link DeploymentEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DeploymentEntityDataMapperTest {

    private static final long DUMMY_ID = 1;

    private static final String DUMMY_TITLE = "Dummy Deployment Title";

    private static final String DUMMY_URL = "http://deployment.com";

    private static final long DEPLOYMENT_DUMMY_ID = 1;

    private static final String DEPLOYMENT_DUMMY_TITLE = "Dummy Deployment Title";

    private static final DeploymentEntity.Status DEPLOYMENT_ENTITY_DUMMY_STATUS
            = DeploymentEntity.Status.ACTIVATED;

    private static final Deployment.Status DEPLOYMENT_DUMMY_STATUS = Deployment.Status.ACTIVATED;

    private static final String DEPLOYMENT_DUMMY_URL = "http://deployment.com";

    private DeploymentEntityDataMapper mDeploymentEntityMapper;

    private DeploymentEntity mDeploymentEntity;

    private Deployment mDeployment;

    @Before
    public void setUp() throws Exception {
        mDeploymentEntityMapper = new DeploymentEntityDataMapper();
    }

    @Test
    public void shouldMapDeploymentEntityToDeployment() {
        mDeploymentEntity = new DeploymentEntity();
        mDeploymentEntity._id = DUMMY_ID;
        mDeploymentEntity.setTitle(DUMMY_TITLE);
        mDeploymentEntity.setStatus(DEPLOYMENT_ENTITY_DUMMY_STATUS);
        mDeploymentEntity.setUrl(DUMMY_URL);

        Deployment deployment = mDeploymentEntityMapper.map(mDeploymentEntity);

        assertThat(deployment).isNotNull();
        assertThat(deployment).isInstanceOf(Deployment.class);
        assertThat(deployment._id).isNotNull();
        assertThat(deployment._id).isEqualTo(DUMMY_ID);
        assertThat(deployment.getTitle()).isNotNull();
        assertThat(deployment.getTitle()).isEqualTo(DUMMY_TITLE);
        assertThat(deployment.getStatus()).isNotNull();
        assertThat(deployment.getStatus()).isEqualTo(DEPLOYMENT_DUMMY_STATUS);
        assertThat(deployment.getUrl()).isNotNull();
        assertThat(deployment.getUrl()).isEqualTo(DUMMY_URL);
    }

    @Test
    public void shouldUnMapFromDeploymentToDeploymentEntity() {
        mDeployment = new Deployment();
        mDeployment._id = DEPLOYMENT_DUMMY_ID;
        mDeployment.setTitle(DEPLOYMENT_DUMMY_TITLE);
        mDeployment.setStatus(DEPLOYMENT_DUMMY_STATUS);
        mDeployment.setUrl(DEPLOYMENT_DUMMY_URL);

        DeploymentEntity deploymentEntity = mDeploymentEntityMapper.map(mDeployment);

        assertThat(deploymentEntity).isNotNull();
        assertThat(deploymentEntity).isInstanceOf(DeploymentEntity.class);
        assertThat(deploymentEntity._id).isNotNull();
        assertThat(deploymentEntity._id).isEqualTo(DUMMY_ID);
        assertThat(deploymentEntity.getTitle()).isNotNull();
        assertThat(deploymentEntity.getTitle()).isEqualTo(DUMMY_TITLE);
        assertThat(deploymentEntity.getStatus()).isNotNull();
        assertThat(deploymentEntity.getStatus()).isEqualTo(DEPLOYMENT_ENTITY_DUMMY_STATUS);
        assertThat(deploymentEntity.getUrl()).isNotNull();
        assertThat(deploymentEntity.getUrl()).isEqualTo(DUMMY_URL);
    }
}
