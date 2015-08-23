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

package com.ushahidi.android.domain.usecase.geojson;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.GeoJsonRepository;

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
 * Tests {@link ListGeoJsonUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class ListGeoJsonUsecaseTest {

    @Mock
    private GeoJsonRepository mMockGeoJsonRepository;

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    private ListGeoJsonUsecase mListGeoJsonUsecase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mListGeoJsonUsecase = new ListGeoJsonUsecase(mMockGeoJsonRepository, mMockThreadExecutor,
                mMockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyFetchFromOnline() {
        mListGeoJsonUsecase.setListGeoJson(1l, From.ONLINE);
        mListGeoJsonUsecase.buildUseCaseObservable();

        verify(mMockGeoJsonRepository).getGeoJson(1l, From.ONLINE);

        verifyNoMoreInteractions(mMockGeoJsonRepository);
        verifyNoMoreInteractions(mMockPostExecutionThread);
        verifyNoMoreInteractions(mMockThreadExecutor);
    }

    @Test
    public void shouldThrowRuntimeException() {
        assertThat(mListGeoJsonUsecase).isNotNull();
        mListGeoJsonUsecase.setListGeoJson(null, null);
        try {
            mListGeoJsonUsecase.execute(null);
            assert_().fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            assertThat(e).hasMessage(
                    "Deployment id and from cannot be null. You must call setListGeoJson(...)");
        }
    }
}
