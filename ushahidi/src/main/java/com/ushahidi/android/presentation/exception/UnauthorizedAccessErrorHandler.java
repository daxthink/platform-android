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

package com.ushahidi.android.presentation.exception;

import com.ushahidi.android.presentation.state.UserState;

import android.os.Handler;
import android.os.Looper;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * ErrorHandler for when the API returns an {@link HttpURLConnection.HTTP_UNAUTHORIZED}
 *
 * Uses an event bus to pass the state of the login
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UnauthorizedAccessErrorHandler implements ErrorHandler {

    private static final Handler MAIN_LOOPER_HANDLER = new Handler(Looper.getMainLooper());

    UserState mUserState;

    @Inject
    public UnauthorizedAccessErrorHandler(UserState userState) {
        mUserState = userState;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        Response r = cause.getResponse();
        if (r != null && r.getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            MAIN_LOOPER_HANDLER.post(() -> mUserState.unauthorized());
        }
        return cause;
    }
}
