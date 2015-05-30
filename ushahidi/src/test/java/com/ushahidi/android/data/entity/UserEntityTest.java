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
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class UserEntityTest {

    private static String EMAIL = "email";

    private static String REAL_NAME = "Real name";

    private static String USER_NAME = "username";

    private static UserEntity.Role ROLE = UserEntity.Role.ADMIN;

    private static Date CREATED = new Date();

    private static Date UPDATED = new Date();

    private static Long DEPLOYMENT_ID = 1l;

    private static final Long DUMMY_ID = 1l;

    private UserEntity mUserEntity;

    @Before
    public void setUp() {
        mUserEntity = new UserEntity();
    }

    @Test
    public void shouldSetUserEntity() {
        mUserEntity.setId(DUMMY_ID);
        mUserEntity.setDeploymentId(DEPLOYMENT_ID);
        mUserEntity.setCreated(CREATED);
        mUserEntity.setUpdated(UPDATED);
        mUserEntity.setRole(ROLE);
        mUserEntity.setEmail(EMAIL);
        mUserEntity.setUsername(USER_NAME);
        mUserEntity.setRealName(REAL_NAME);

        assertThat(mUserEntity).isNotNull();
        assertThat(mUserEntity).isInstanceOf(UserEntity.class);
        assertThat(mUserEntity.getId()).isNotNull();
        assertThat(mUserEntity.getId()).isEqualTo(DUMMY_ID);
        assertThat(mUserEntity.getCreated()).isNotNull();
        assertThat(mUserEntity.getCreated()).isEqualTo(CREATED);
        assertThat(mUserEntity.getDeploymentId()).isNotNull();
        assertThat(mUserEntity.getDeploymentId()).isGreaterThan(0l);
        assertThat(mUserEntity.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
    }
}
