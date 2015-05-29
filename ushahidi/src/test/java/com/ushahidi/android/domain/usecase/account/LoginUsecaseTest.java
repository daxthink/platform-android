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

package com.ushahidi.android.domain.usecase.account;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.domain.entity.UserAccount;
import com.ushahidi.android.domain.repository.UserAccountRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests {@link LoginUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class LoginUsecaseTest {

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    @Mock
    private UserAccountRepository mMockUserAccountRepository;

    private LoginUsecase mLoginUsecase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mLoginUsecase = new LoginUsecase(mMockUserAccountRepository, mMockThreadExecutor,
                mMockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyLogin() {
        UserAccount userAccount = new UserAccount();
        mLoginUsecase.setUserAccount(userAccount);
        mLoginUsecase.buildUseCaseObservable();

        verify(mMockUserAccountRepository).login(userAccount);

        verifyNoMoreInteractions(mMockUserAccountRepository);
        verifyNoMoreInteractions(mMockPostExecutionThread);
        verifyNoMoreInteractions(mMockThreadExecutor);
    }

    @Test
    public void shouldThrowRuntimeException() {
        assertThat(mLoginUsecase).isNotNull();
        mLoginUsecase.setUserAccount(null);
        try {
            mLoginUsecase.execute(null);
            assert_().fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            assertThat(e).hasMessage("User account is null. You must call setUserAccount(...)");
        }
    }
}
