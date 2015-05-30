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

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test {@link Tag}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TagTest {

    private static final Long DUMMY_ID = 1l;

    private static Long PARENT_ID = 1l;

    private static String TAG = "tag";

    private static String SLUG = "slug";

    private static String COLOR = "color";

    private static Tag.Type TYPE = Tag.Type.CATEGORY;

    private static String ICON = "icon";

    private static String DESCRIPTION = "description";

    private static int PRIORITY = 1;

    private static Date CREATED = new Date();

    private static Long DEPLOYMENT_ID = 1l;

    private Tag mTag;

    @Before
    public void setUp() {
        mTag = new Tag();
    }

    @Test
    public void shouldSetTag() {
        mTag._id = DUMMY_ID;
        mTag.setDeploymentId(DEPLOYMENT_ID);
        mTag.setSlug(SLUG);
        mTag.setCreated(CREATED);
        mTag.setIcon(ICON);
        mTag.setPriority(PRIORITY);
        mTag.setColor(COLOR);
        mTag.setParentId(PARENT_ID);
        mTag.setDescription(DESCRIPTION);
        mTag.setType(TYPE);
        mTag.setTag(TAG);

        assertThat(mTag).isNotNull();
        assertThat(mTag).isInstanceOf(Tag.class);
        assertThat(mTag.getSlug()).isNull();
        assertThat(mTag.getSlug()).isEqualTo(SLUG);
        assertThat(mTag.getDeploymentId()).isNotNull();
        assertThat(mTag.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(mTag.getCreated()).isNotNull();
        assertThat(mTag.getCreated()).isEqualTo(CREATED);
        assertThat(mTag.getIcon()).isNotNull();
        assertThat(mTag.getIcon()).isEqualTo(ICON);
        assertThat(mTag.getPriority()).isNotNull();
        assertThat(mTag.getPriority()).isEqualTo(PRIORITY);
        assertThat(mTag.getType()).isNotNull();
        assertThat(mTag.getType()).isEqualTo(TYPE);
        assertThat(mTag.getColor()).isNotNull();
        assertThat(mTag.getColor()).isEqualTo(COLOR);
        assertThat(mTag.getParentId()).isNotNull();
        assertThat(mTag.getParentId()).isEqualTo(PARENT_ID);
        assertThat(mTag.getDescription()).isNotNull();
        assertThat(mTag.getDescription()).isEqualTo(DESCRIPTION);
    }
}
