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
import com.ushahidi.android.data.entity.UserAccountEntity;
import com.ushahidi.android.domain.entity.UserAccount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link UserAccountEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class UserAccountEntityDataMapperTest {

    private UserAccount mUserAccount;

    private UserAccountEntity mUserAccountEntity;

    private String ACCOUNT_NAME = "account name";

    private String PASSWORD = "password";

    private String AUTH_TOKEN = "Auth token";

    private String AUTH_TOKEN_TYPE = "auth token type";

    private long DEPLOYMENT_ID = 1l;

    private static final Long DUMMY_ID = 1l;

    private UserAccountEntityDataMapper mUserAccountEntityDataMapper;

    @Before
    public void setUp() {
        mUserAccountEntityDataMapper = new UserAccountEntityDataMapper();
    }

    @Test
    public void shouldMapUserAccountEntityToUserAccount() {
        mUserAccountEntity = new UserAccountEntity();
        mUserAccountEntity._id = DUMMY_ID;
        mUserAccountEntity.setPassword(PASSWORD);
        mUserAccountEntity.setAccountName(ACCOUNT_NAME);

        mUserAccount = mUserAccountEntityDataMapper.map(mUserAccountEntity);
        assertThat(mUserAccount).isNotNull();
        assertThat(mUserAccount).isInstanceOf(UserAccount.class);
        assertThat(mUserAccount._id).isEqualTo(DUMMY_ID);
        assertThat(mUserAccount.getAccountName()).isEqualTo(ACCOUNT_NAME);
        assertThat(mUserAccount.getPassword()).isNotNull();
        assertThat(mUserAccount.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void shouldMapUserAccountToUserAccountEntity() {
        mUserAccount = new UserAccount();
        mUserAccount._id = DUMMY_ID;
        mUserAccount.setPassword(PASSWORD);
        mUserAccount.setAccountName(ACCOUNT_NAME);

        mUserAccountEntity = mUserAccountEntityDataMapper.map(mUserAccount);
        assertThat(mUserAccountEntity).isNotNull();
        assertThat(mUserAccountEntity).isInstanceOf(UserAccountEntity.class);
        assertThat(mUserAccountEntity._id).isEqualTo(DUMMY_ID);
        assertThat(mUserAccountEntity.getAccountName()).isEqualTo(ACCOUNT_NAME);
        assertThat(mUserAccountEntity.getPassword()).isNotNull();
        assertThat(mUserAccountEntity.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void shouldMapUserAccountEntityListToUserAccountList() {
        mUserAccountEntity = new UserAccountEntity();
        mUserAccountEntity._id = DUMMY_ID;
        mUserAccountEntity.setPassword(PASSWORD);
        mUserAccountEntity.setAccountName(ACCOUNT_NAME);
        List<UserAccountEntity> userAccountEntityList = new ArrayList<>();
        userAccountEntityList.add(mUserAccountEntity);

        List<UserAccount> userAccountList = mUserAccountEntityDataMapper.map(userAccountEntityList);
        assertThat(userAccountList.get(0)).isNotNull();
        assertThat(userAccountList.get(0)).isInstanceOf(UserAccount.class);
        assertThat(userAccountList.get(0)._id).isEqualTo(DUMMY_ID);
        assertThat(userAccountList.get(0).getAccountName()).isEqualTo(ACCOUNT_NAME);
        assertThat(userAccountList.get(0).getPassword()).isNotNull();
        assertThat(userAccountList.get(0).getPassword()).isEqualTo(PASSWORD);
    }
}
