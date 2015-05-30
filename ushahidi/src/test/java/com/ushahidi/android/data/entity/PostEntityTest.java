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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class PostEntityTest {

    private static Long PARENT = 1l;

    private static String TITLE = "title";

    private static PostEntity.Type TYPE = PostEntity.Type.REPORT;

    private static String SLUG = "slug";

    private static String CONTENT = "content";

    private static String AUTHOR_EMAIL = "author email";

    private static String AUTHOR_REAL_NAME = "author real name";

    private static PostEntity.Status POST_STATUS = PostEntity.Status.DRAFT;

    private static Long DEPLOYMENT_ID = 1l;

    private static Long DUMMY_ID = 1l;

    private static Date CREATED = new Date();

    private static Date UPDATED = new Date();

    private static List<TagEntity> TAG_LIST = new ArrayList<>();

    private static List<PostTagEntity> POST_TAG_ENTITY = new ArrayList<>();

    private static PostValueEntity POST_VALUE = new PostValueEntity();

    private PostEntity mPostEntity = new PostEntity();

    @Test
    public void shouldSetPostEntity() {
        mPostEntity._id = DUMMY_ID;
        mPostEntity.setTitle(TITLE);
        mPostEntity.setDeploymentId(DEPLOYMENT_ID);
        mPostEntity.setAuthorEmail(AUTHOR_EMAIL);
        mPostEntity.setAuthorRealname(AUTHOR_REAL_NAME);
        mPostEntity.setContent(CONTENT);
        mPostEntity.setSlug(SLUG);
        mPostEntity.setCreated(CREATED);
        mPostEntity.setUpdated(UPDATED);
        mPostEntity.setParent(PARENT);
        mPostEntity.setStatus(POST_STATUS);
        mPostEntity.setValues(POST_VALUE);
        mPostEntity.setTags(TAG_LIST);
        mPostEntity.setType(TYPE);
        mPostEntity.setPostTagEntityList(POST_TAG_ENTITY);

        assertThat(mPostEntity).isNotNull();
        assertThat(mPostEntity).isInstanceOf(PostEntity.class);
        assertThat(mPostEntity.getDeploymentId()).isNotNull();
        assertThat(mPostEntity.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(mPostEntity.getAuthorEmail()).isNotNull();
        assertThat(mPostEntity.getAuthorEmail()).isEqualTo(AUTHOR_EMAIL);
        assertThat(mPostEntity.getAuthorRealname()).isNotNull();
        assertThat(mPostEntity.getAuthorRealname()).isEqualTo(AUTHOR_REAL_NAME);
        assertThat(mPostEntity.getContent()).isNotNull();
        assertThat(mPostEntity.getContent()).isEqualTo(CONTENT);
        assertThat(mPostEntity.getCreated()).isNotNull();
        assertThat(mPostEntity.getCreated()).isEqualTo(CREATED);
        assertThat(mPostEntity.getUpdated()).isNotNull();
        assertThat(mPostEntity.getUpdated()).isEqualTo(UPDATED);
        assertThat(mPostEntity.getSlug()).isNotNull();
        assertThat(mPostEntity.getSlug()).isEqualTo(SLUG);
        assertThat(mPostEntity.getTags()).isNotNull();
        assertThat(mPostEntity.getTags()).isEqualTo(TAG_LIST);
        assertThat(mPostEntity.getStatus()).isNotNull();
        assertThat(mPostEntity.getStatus()).isEqualTo(POST_STATUS);
        assertThat(mPostEntity.getType()).isNotNull();
        assertThat(mPostEntity.getType()).isEqualTo(TYPE);
        assertThat(mPostEntity._id).isNotNull();
        assertThat(mPostEntity._id).isEqualTo(DUMMY_ID);
        assertThat(mPostEntity.getParent()).isNotNull();
        assertThat(mPostEntity.getParent()).isEqualTo(PARENT);
        assertThat(mPostEntity.getValues()).isNotNull();
        assertThat(mPostEntity.getValues()).isEqualTo(POST_VALUE);
        assertThat(mPostEntity.getTitle()).isNotNull();
        assertThat(mPostEntity.getTitle()).isEqualTo(TITLE);
    }
}
