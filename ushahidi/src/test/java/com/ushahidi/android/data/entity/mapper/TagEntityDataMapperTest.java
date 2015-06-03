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
import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.domain.entity.Tag;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link TagEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TagEntityDataMapperTest {

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

    private TagEntityDataMapper mTagEntityDataMapper;

    private TagEntity mTagEntity;

    private Tag mTag;

    @Before
    public void setUp() throws Exception {
        mTagEntityDataMapper = new TagEntityDataMapper();
    }

    @Test
    public void shouldMapTagEntityToTagValue() {
        mTagEntity = new TagEntity();
        mTagEntity.setDeploymentId(DEPLOYMENT_ID);
        mTagEntity._id = DUMMY_ID;
        mTagEntity.setColor(COLOR);
        mTagEntity.setCreated(CREATED);
        mTagEntity.setDescription(DESCRIPTION);
        mTagEntity.setParentId(PARENT_ID);
        mTagEntity.setPriority(PRIORITY);
        mTagEntity.setTag(TAG);
        mTagEntity.setType(TYPE);
        mTagEntity.setIcon(ICON);
        Tag tag = mTagEntityDataMapper.map(mTagEntity);
        assertThat(tag).isNotNull();
        assertThat(tag).isInstanceOf(Tag.class);
        assertThat(tag.getCreated()).isEqualTo(CREATED);
        assertThat(tag.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(tag.getParentId()).isEqualTo(PARENT_ID);
        assertThat(tag.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(tag.getColor()).isEqualTo(COLOR);
        assertThat(tag.getPriority()).isEqualTo(PRIORITY);
        assertThat(tag.getTag()).isEqualTo(TAG);
        assertThat(tag.getType()).isEqualTo(Tag.Type.CATEGORY);
    }

    @Test
    public void shouldMapTagValueToTagEntity() {
        mTag = new Tag();
        mTag.setDeploymentId(DEPLOYMENT_ID);
        mTag._id = DUMMY_ID;
        mTag.setColor(COLOR);
        mTag.setCreated(CREATED);
        mTag.setDescription(DESCRIPTION);
        mTag.setParentId(PARENT_ID);
        mTag.setPriority(PRIORITY);
        mTag.setTag(TAG);
        mTag.setType(Tag.Type.CATEGORY);
        mTag.setIcon(ICON);
        TagEntity tagEntity = mTagEntityDataMapper.map(mTag);
        assertThat(tagEntity).isNotNull();
        assertThat(tagEntity).isInstanceOf(TagEntity.class);
        assertThat(tagEntity.getCreated()).isEqualTo(CREATED);
        assertThat(tagEntity.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(tagEntity.getParentId()).isEqualTo(PARENT_ID);
        assertThat(tagEntity.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(tagEntity.getColor()).isEqualTo(COLOR);
        assertThat(tagEntity.getPriority()).isEqualTo(PRIORITY);
        assertThat(tagEntity.getTag()).isEqualTo(TAG);
        assertThat(tagEntity.getType()).isEqualTo(TYPE);
    }

    @Test
    public void shouldMapFromTagEntityListToTagValueList() {
        mTagEntity = new TagEntity();
        mTagEntity.setDeploymentId(DEPLOYMENT_ID);
        mTagEntity._id = DUMMY_ID;
        mTagEntity.setColor(COLOR);
        mTagEntity.setCreated(CREATED);
        mTagEntity.setDescription(DESCRIPTION);
        mTagEntity.setParentId(PARENT_ID);
        mTagEntity.setPriority(PRIORITY);
        mTagEntity.setTag(TAG);
        mTagEntity.setType(TYPE);
        mTagEntity.setIcon(ICON);
        List<TagEntity> tagEntityList = new ArrayList<>();
        tagEntityList.add(mTagEntity);
        List<Tag> tagList = mTagEntityDataMapper.map(tagEntityList);
        assertThat(tagList).isNotNull();
        assertThat(tagList.get(0)).isNotNull();
        assertThat(tagList.get(0)).isInstanceOf(Tag.class);
        assertThat(tagList.get(0).getCreated()).isEqualTo(CREATED);
        assertThat(tagList.get(0).getDescription()).isEqualTo(DESCRIPTION);
        assertThat(tagList.get(0).getParentId()).isEqualTo(PARENT_ID);
        assertThat(tagList.get(0).getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(tagList.get(0).getColor()).isEqualTo(COLOR);
        assertThat(tagList.get(0).getPriority()).isEqualTo(PRIORITY);
        assertThat(tagList.get(0).getTag()).isEqualTo(TAG);
        assertThat(tagList.get(0).getType()).isEqualTo(Tag.Type.CATEGORY);
    }
}
