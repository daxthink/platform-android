/*
 * Copyright (c) 2014 Ushahidi.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.data.exception;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

/**
 * Tests {@link com.ushahidi.android.data.exception.RepositoryError}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class RepositoryErrorTest extends BaseTestCase {

    private RepositoryError mRepositoryError;

    @Mock
    private Exception mMockException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mRepositoryError = new RepositoryError(mMockException);
    }

    @Test
    public void shouldGetRepositoryExceptionMessage() {
        mRepositoryError.getErrorMessage();

        verify(mMockException).getMessage();
    }

}
