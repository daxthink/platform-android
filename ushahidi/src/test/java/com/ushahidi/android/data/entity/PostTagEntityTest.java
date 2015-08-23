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
import com.ushahidi.android.DefaultConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link PostTagEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class PostTagEntityTest {

    private Long POST_ID = 1l;

    private Long TAG_ID = 2l;

    private Long ID = 1l;

    private PostTagEntity mPostTagEntity;

    @Before
    public void setUp() {
        mPostTagEntity = new PostTagEntity();
    }

    @Test
    public void shouldSetPostTagEntity() {
        mPostTagEntity.setId(ID);
        mPostTagEntity.setPostId(POST_ID);
        mPostTagEntity.setTagId(TAG_ID);

        assertThat(mPostTagEntity).isNotNull();
        assertThat(mPostTagEntity).isInstanceOf(PostTagEntity.class);
        assertThat(mPostTagEntity).isNotNull();
        assertThat(mPostTagEntity.getId()).isEqualTo(ID);
        assertThat(mPostTagEntity.getPostId()).isNotNull();
        assertThat(mPostTagEntity.getPostId()).isEqualTo(POST_ID);
        assertThat(mPostTagEntity.getTagId()).isNotNull();
        assertThat(mPostTagEntity.getTagId()).isEqualTo(TAG_ID);
    }
}
