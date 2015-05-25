/*
 *  Copyright (c) 2015 Ushahidi.
 *
 *   This program is free software: you can redistribute it and/or modify it under
 *   the terms of the GNU Affero General Public License as published by the Free
 *   Software Foundation, either version 3 of the License, or (at your option)
 *   any later version.
 *
 *   This program is distributed in the hope that it will be useful, but WITHOUT
 *   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *   FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program in the file LICENSE-AGPL. If not, see
 *   https://www.gnu.org/licenses/agpl-3.0.html
 *
 */

package com.ushahidi.android.core.usecase.geojson;

import com.ushahidi.android.core.repository.IGeoJsonRepository;
import com.ushahidi.android.core.task.PostExecutionThread;
import com.ushahidi.android.core.task.ThreadExecutor;
import com.ushahidi.android.core.usecase.IInteractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Tests FetchGeoJson Usecase
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FetchGeoJsonTest {
    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    @Mock
    private IGeoJsonRepository mMockGeoJsonRepository;

    private FetchGeoJson mFetchPostGeoJson;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mFetchPostGeoJson = new FetchGeoJson(mMockThreadExecutor,
            mMockPostExecutionThread);
        mFetchPostGeoJson.setGeoJsonRepository(mMockGeoJsonRepository);
    }

    @Test
    public void testFetchPostGeoJsonExecution() {
        doNothing().when(mMockThreadExecutor).execute(any(IInteractor.class));

        FetchGeoJson.Callback mockListPostCallback = getPostGeoJsonCallback();

        mFetchPostGeoJson.execute(1l, mockListPostCallback);

        verify(mMockThreadExecutor).execute(any(IInteractor.class));
        verifyNoMoreInteractions(mMockThreadExecutor);
        verifyZeroInteractions(mMockGeoJsonRepository);
        verifyZeroInteractions(mMockPostExecutionThread);
    }

    private FetchGeoJson.Callback getPostGeoJsonCallback() {
        return mock(
            FetchGeoJson.Callback.class);
    }

    @Test
    public void testFetchPostGeoJsonRun() {
        doNothing().when(mMockThreadExecutor).execute(any(IInteractor.class));
        FetchGeoJson.Callback mockListPostCallback = getPostGeoJsonCallback();
        doNothing().when(mMockGeoJsonRepository).getGeoJsonListViaApi(anyLong(),
            any(IGeoJsonRepository.GeoJsonListCallback.class));

        mFetchPostGeoJson.execute(1l, mockListPostCallback);
        mFetchPostGeoJson.run();

        verify(mMockGeoJsonRepository).getGeoJsonListViaApi(anyLong(),
            any(IGeoJsonRepository.GeoJsonListCallback.class));

        verify(mMockThreadExecutor).execute(any(IInteractor.class));
        verifyNoMoreInteractions(mMockGeoJsonRepository);
        verifyNoMoreInteractions(mMockPostExecutionThread);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFetchPostGeoJsonNullParameter() {
        mFetchPostGeoJson.execute(1l, null);
    }
}
