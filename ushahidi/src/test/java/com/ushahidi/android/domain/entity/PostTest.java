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

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link Post}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostTest {

    private Post mPost;

    private static Long PARENT = 1l;

    private static String TITLE = "title";

    private static Post.Type TYPE = Post.Type.REPORT;

    private static String SLUG = "slug";

    private static String CONTENT = "content";

    private static String AUTHOR_EMAIL = "author email";

    private static String AUTHOR_REAL_NAME = "author real name";

    private static Post.Status POST_STATUS = Post.Status.DRAFT;

    private static Long DEPLOYMENT_ID = 1l;

    private static Long DUMMY_ID = 1l;

    private static Date CREATED = new Date();

    private static Date UPDATED = new Date();

    private static List<Tag> TAG_LIST = new ArrayList<>();

    private static PostValue POST_VALUE = new PostValue();

    @Before
    public void setUp() {
        mPost = new Post();
    }

    @Test
    public void shouldSetPost() {
        mPost._id = DUMMY_ID;
        mPost.setTitle(TITLE);
        mPost.setDeploymentId(DEPLOYMENT_ID);
        mPost.setAuthorEmail(AUTHOR_EMAIL);
        mPost.setAuthorRealname(AUTHOR_REAL_NAME);
        mPost.setContent(CONTENT);
        mPost.setSlug(SLUG);
        mPost.setCreated(CREATED);
        mPost.setUpdated(UPDATED);
        mPost.setParent(PARENT);
        mPost.setStatus(POST_STATUS);
        mPost.setValues(POST_VALUE);
        mPost.setTags(TAG_LIST);
        mPost.setType(TYPE);

        assertThat(mPost).isNotNull();
        assertThat(mPost).isInstanceOf(Post.class);
        assertThat(mPost.getDeploymentId()).isNotNull();
        assertThat(mPost.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
        assertThat(mPost.getAuthorEmail()).isNotNull();
        assertThat(mPost.getAuthorEmail()).isEqualTo(AUTHOR_EMAIL);
        assertThat(mPost.getAuthorRealname()).isNotNull();
        assertThat(mPost.getAuthorRealname()).isEqualTo(AUTHOR_REAL_NAME);
        assertThat(mPost.getContent()).isNotNull();
        assertThat(mPost.getContent()).isEqualTo(CONTENT);
        assertThat(mPost.getCreated()).isNotNull();
        assertThat(mPost.getCreated()).isEqualTo(CREATED);
        assertThat(mPost.getUpdated()).isNotNull();
        assertThat(mPost.getUpdated()).isEqualTo(UPDATED);
        assertThat(mPost.getSlug()).isNotNull();
        assertThat(mPost.getSlug()).isEqualTo(SLUG);
        assertThat(mPost.getTags()).isNotNull();
        assertThat(mPost.getTags()).isEqualTo(TAG_LIST);
        assertThat(mPost.getStatus()).isNotNull();
        assertThat(mPost.getStatus()).isEqualTo(POST_STATUS);
        assertThat(mPost.getType()).isNotNull();
        assertThat(mPost.getType()).isEqualTo(TYPE);
        assertThat(mPost._id).isNotNull();
        assertThat(mPost._id).isEqualTo(DUMMY_ID);
        assertThat(mPost.getParent()).isNotNull();
        assertThat(mPost.getParent()).isEqualTo(PARENT);
        assertThat(mPost.getValues()).isNotNull();
        assertThat(mPost.getValues()).isEqualTo(POST_VALUE);
        assertThat(mPost.getTitle()).isNotNull();
        assertThat(mPost.getTitle()).isEqualTo(TITLE);
    }
}
