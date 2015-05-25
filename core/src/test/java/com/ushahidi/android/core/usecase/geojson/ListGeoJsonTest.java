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
 * Tests {@link ListGeoJson}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListGeoJsonTest {

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    @Mock
    private IGeoJsonRepository mMockGeoJsonRepository;

    private ListGeoJson mListPostGeoJson;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mListPostGeoJson = new ListGeoJson(mMockThreadExecutor, mMockPostExecutionThread);
        mListPostGeoJson.setGeoJsonRepository(mMockGeoJsonRepository);
    }

    @Test
    public void testListPostGeoJsonExecution() {
        doNothing().when(mMockThreadExecutor).execute(any(IInteractor.class));
        ListGeoJson.Callback mockListPostGeoJsonCallback = mock(ListGeoJson.Callback.class);
        mListPostGeoJson.execute(1l, mockListPostGeoJsonCallback);

        verify(mMockThreadExecutor).execute(any(IInteractor.class));
        verifyNoMoreInteractions(mMockThreadExecutor);
        verifyZeroInteractions(mMockGeoJsonRepository);
        verifyZeroInteractions(mMockPostExecutionThread);
    }

    @Test
    public void testListPostGeoJsonRun() {
        doNothing().when(mMockThreadExecutor).execute(any(IInteractor.class));
        ListGeoJson.Callback mockListPostGeoJsonCallback = mock(ListGeoJson.Callback.class);
        doNothing().when(mMockGeoJsonRepository).getGeoJsonList(anyLong(),
            any(IGeoJsonRepository.GeoJsonListCallback.class));
        mListPostGeoJson.execute(1l, mockListPostGeoJsonCallback);
        mListPostGeoJson.run();

        verify(mMockGeoJsonRepository).getGeoJsonList(anyLong(), any(IGeoJsonRepository.GeoJsonListCallback.class));
        verify(mMockThreadExecutor).execute(any(IInteractor.class));
        verifyNoMoreInteractions(mMockGeoJsonRepository);
        verifyNoMoreInteractions(mMockPostExecutionThread);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testListPostGeoJsonNullParameter() {
        mListPostGeoJson.execute(1l, null);
    }
}
