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

package com.ushahidi.android.data.api.account;

/**
 * Tests {@link com.ushahidi.android.data.api.account.PersistedSessionManager}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.data.entity.TestEntityFixtures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowPreferenceManager;

import android.content.SharedPreferences;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class PersistedAccountManagerTest {

    private static final String PREF_ACTIVE_USER_ACCOUNT_KEY = "active_userAccount";

    private static final String PREF_USER_ACCOUNT_KEY = "user_account_key";

    private static final String PREF_RANDOM_USER_ACCOUNT_KEY = "random_user_account_key";

    private static final long USER_ACCOUNT_ENTITY_ID = TestEntityFixtures
            .getPlatformSession().getId();

    private static final long DEPLOYMENT_ID = TestEntityFixtures.getPlatformSession()
            .getDeploymentId();

    private SharedPreferences mSharedPreferences;

    private PersistedSessionManager<Session> mAccountManager;

    private SerializationStrategy<Session> mMockSerializer;

    @Before
    public void setUp() throws Exception {
        mSharedPreferences = ShadowPreferenceManager.getDefaultSharedPreferences(
                RuntimeEnvironment.application);
        mMockSerializer = mock(SerializationStrategy.class);
        mAccountManager = new PersistedSessionManager<>(mSharedPreferences, mMockSerializer,
                PREF_ACTIVE_USER_ACCOUNT_KEY, PREF_USER_ACCOUNT_KEY);
    }

    @After
    public void tearDown() throws Exception {
        mSharedPreferences.edit().clear().commit();
    }

    @Test
    public void shouldRestoreSessionNotSave() {
        when(mMockSerializer.deserialize("")).thenReturn(null);
        mAccountManager.restoreAllSessionsIfNecessary();
        assertThat(mAccountManager.getActiveSession()).isNull();
    }

    @Test
    public void shouldValidateSessionPreferenceKey() {
        final String preferenceKey = PREF_USER_ACCOUNT_KEY + "_" + TestEntityFixtures
                .getUserAccountEntity()._id + "_" + TestEntityFixtures.getUserAccountEntity()
                ._id;
        assertTrue(mAccountManager.isSessionPreferenceKey(preferenceKey));
    }

    @Test
    public void testIsSessionPreferenceKey_invalidKey() {
        assertFalse(mAccountManager.isSessionPreferenceKey(PREF_RANDOM_USER_ACCOUNT_KEY));
    }

    @Test
    public void shouldGetValidSession() {
        final Session userAccountEntity = setUpActiveSession();
        assertThat(userAccountEntity).isEqualTo(mAccountManager.getActiveSession());
    }

    private PlatformSession setUpActiveSession() {
        final PlatformSession mockSession = mock(PlatformSession.class);
        when(mockSession.getId()).thenReturn(USER_ACCOUNT_ENTITY_ID);
        when(mockSession.getDeploymentId()).thenReturn(DEPLOYMENT_ID);
        mAccountManager.setActiveSession(mockSession);
        return mockSession;
    }
}
