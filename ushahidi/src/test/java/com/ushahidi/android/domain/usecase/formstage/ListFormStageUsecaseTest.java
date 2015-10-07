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

package com.ushahidi.android.domain.usecase.formstage;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormStageRepository;
import com.ushahidi.android.domain.usecase.form.ListFormUsecase;
import com.ushahidi.android.domain.usecase.formstages.ListFormStageUsecase;

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
 * Tests {@link ListFormUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class ListFormStageUsecaseTest {

    @Mock
    private FormStageRepository mMockFormStageRepository;

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    private ListFormStageUsecase mListFormStageUsecase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mListFormStageUsecase = new ListFormStageUsecase(mMockFormStageRepository,
                mMockThreadExecutor,
                mMockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyFetchFromOnline() {
        mListFormStageUsecase.setListFormStage(1l, 1l, From.ONLINE);
        mListFormStageUsecase.buildUseCaseObservable();

        verify(mMockFormStageRepository).getFormStages(1l, 1l, From.ONLINE);

        verifyNoMoreInteractions(mMockFormStageRepository);
        verifyNoMoreInteractions(mMockPostExecutionThread);
        verifyNoMoreInteractions(mMockThreadExecutor);
    }

    @Test
    public void shouldThrowRuntimeException() {
        assertThat(mListFormStageUsecase).isNotNull();
        mListFormStageUsecase.setListFormStage(null, null, null);
        try {
            mListFormStageUsecase.execute(null);
            assert_().fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            assertThat(e).hasMessage(
                    "Deployment id and form id cannot be null. You must call setListFormStage(...)");
        }
    }
}
