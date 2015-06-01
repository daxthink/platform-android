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
import com.ushahidi.android.data.entity.PostValueEntity;
import com.ushahidi.android.domain.entity.PostValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link PostValueEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class PostValueEntityDataMapperTest {

    private static Long DEPLOYMENT_ID = 1l;

    private static String value = "values";

    private PostValueEntityDataMapper mPostValueEntityDataMapper;

    private PostValue mPostValue;

    private PostValueEntity mPostValueEntity;

    @Before
    public void setUp() throws Exception {
        mPostValueEntityDataMapper = new PostValueEntityDataMapper();
    }

    @Test
    public void shouldMapPostValueEntityToPostValue() {
        mPostValueEntity = new PostValueEntity();
        mPostValueEntity.setDeploymentId(DEPLOYMENT_ID);
        mPostValueEntity.setValues(value);

        mPostValue = mPostValueEntityDataMapper.map(mPostValueEntity);
        assertThat(mPostValue).isNotNull();
        assertThat(mPostValue).isInstanceOf(PostValue.class);
        assertThat(mPostValue.getValues()).isEqualTo(mPostValueEntity.getValues());
        assertThat(mPostValue.getDeploymentId())
                .isEqualTo(mPostValueEntity.getDeploymentId());
    }

    @Test
    public void shouldMapFromPostValueToPostValueEntity() {
        mPostValue = new PostValue();
        mPostValue.setDeploymentId(DEPLOYMENT_ID);
        mPostValue.setValues(value);

        mPostValueEntity = mPostValueEntityDataMapper.map(mPostValue);
        assertThat(mPostValueEntity).isNotNull();
        assertThat(mPostValueEntity).isInstanceOf(PostValueEntity.class);
        assertThat(mPostValueEntity.getValues()).isEqualTo(mPostValue.getValues());
        assertThat(mPostValueEntity.getDeploymentId())
                .isEqualTo(mPostValue.getDeploymentId());
    }

    @Test
    public void shouldMapFromPostValueEntityListToPostValueList() {
        mPostValueEntity = new PostValueEntity();
        mPostValueEntity.setDeploymentId(DEPLOYMENT_ID);
        mPostValueEntity.setValues(value);
        List<PostValueEntity> postValueEntities = new ArrayList<>();
        postValueEntities.add(mPostValueEntity);
        List<PostValue> postValues = mPostValueEntityDataMapper.map(postValueEntities);

        assertThat(postValues).isNotNull();
        assertThat(postValues.get(0)).isInstanceOf(PostValue.class);
        assertThat(postValues.get(0).getValues()).isEqualTo(mPostValueEntity.getValues());
        assertThat(postValues.get(0).getDeploymentId())
                .isEqualTo(mPostValueEntity.getDeploymentId());
    }
}
