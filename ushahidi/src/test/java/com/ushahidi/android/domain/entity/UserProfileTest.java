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
import com.ushahidi.android.DefaultConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link UserProfile}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class UserProfileTest {

    private static final Long DUMMY_ID = 1l;

    private static String EMAIL = "email";

    private static String REAL_NAME = "Real name";

    private static String USER_NAME = "username";

    private static UserProfile.Role ROLE = UserProfile.Role.ADMIN;

    private static Date CREATED = new Date();

    private static Date UPDATED = new Date();

    private static Long DEPLOYMENT_ID = 1l;

    UserProfile mUserProfile;

    @Before
    public void setUp() {
        mUserProfile = new UserProfile();
    }

    @Test
    public void shouldSetUserProfile() {
        mUserProfile._id = DUMMY_ID;
        mUserProfile.setDeploymentId(DEPLOYMENT_ID);
        mUserProfile.setCreated(CREATED);
        mUserProfile.setUpdated(UPDATED);
        mUserProfile.setRole(ROLE);
        mUserProfile.setEmail(EMAIL);
        mUserProfile.setUsername(USER_NAME);
        mUserProfile.setRealName(REAL_NAME);

        assertThat(mUserProfile).isNotNull();
        assertThat(mUserProfile).isInstanceOf(UserProfile.class);
        assertThat(mUserProfile._id).isNotNull();
        assertThat(mUserProfile._id).isGreaterThan(0l);
        assertThat(mUserProfile._id).isEqualTo(DUMMY_ID);
        assertThat(mUserProfile.getCreated()).isNotNull();
        assertThat(mUserProfile.getCreated()).isEqualTo(CREATED);
        assertThat(mUserProfile.getDeploymentId()).isNotNull();
        assertThat(mUserProfile.getDeploymentId()).isGreaterThan(0l);
        assertThat(mUserProfile.getDeploymentId()).isEqualTo(DEPLOYMENT_ID);
    }
}
