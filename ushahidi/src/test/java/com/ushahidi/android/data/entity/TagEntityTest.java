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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link TagEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TagEntityTest {

    private static final Long DUMMY_ID = 1l;

    private static Long PARENT_ID = 1l;

    private static String TAG = "tag";

    private static String COLOR = "color";

    private static TagEntity.Type TYPE = TagEntity.Type.CATEGORY;

    private static String ICON = "icon";

    private static String DESCRIPTION = "description";

    private static int PRIORITY = 1;

    private static Date CREATED = new Date();

    private static Long DEPLOYMENT_ID = 1l;

    private TagEntity mTagEntity;

    @Before
    public void setUp() {
        mTagEntity = new TagEntity();
    }

    @Test
    public void shouldSetTagEntity() {
        mTagEntity._id = DUMMY_ID;
        mTagEntity.setDeploymentId(DEPLOYMENT_ID);
        mTagEntity.setCreated(CREATED);
        mTagEntity.setIcon(ICON);
        mTagEntity.setPriority(PRIORITY);
        mTagEntity.setColor(COLOR);
        mTagEntity.setParentId(PARENT_ID);
        mTagEntity.setDescription(DESCRIPTION);
        mTagEntity.setType(TYPE);
        mTagEntity.setTag(TAG);

        assertThat(mTagEntity).isNotNull();
        assertThat(mTagEntity).isInstanceOf(TagEntity.class);
        assertThat(mTagEntity.getDeploymentId()).isNotNull();
        assertThat(mTagEntity.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(mTagEntity.getCreated()).isNotNull();
        assertThat(mTagEntity.getCreated()).isEqualTo(CREATED);
        assertThat(mTagEntity.getIcon()).isNotNull();
        assertThat(mTagEntity.getIcon()).isEqualTo(ICON);
        assertThat(mTagEntity.getPriority()).isNotNull();
        assertThat(mTagEntity.getPriority()).isEqualTo(PRIORITY);
        assertThat(mTagEntity.getType()).isNotNull();
        assertThat(mTagEntity.getType()).isEqualTo(TYPE);
        assertThat(mTagEntity.getColor()).isNotNull();
        assertThat(mTagEntity.getColor()).isEqualTo(COLOR);
        assertThat(mTagEntity.getParentId()).isNotNull();
        assertThat(mTagEntity.getParentId()).isEqualTo(PARENT_ID);
        assertThat(mTagEntity.getDescription()).isNotNull();
        assertThat(mTagEntity.getDescription()).isEqualTo(DESCRIPTION);
    }
}
