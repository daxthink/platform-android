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

package com.ushahidi.android.data.exception;

import com.ushahidi.android.R;

import android.content.Context;

import javax.inject.Inject;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * ErrorHandler to determine network issues
 *
 * Uses an event bus to pass the state of the login
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class RetrofitErrorHandler implements ErrorHandler {

    private final Context mContext;

    /**
     * Default constructor
     *
     * @param context The calling context
     */
    @Inject
    public RetrofitErrorHandler(Context context) {
        mContext = context;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
            String errorDescription = mContext.getString(R.string.exception_message_no_connection);
            return new NetworkException(errorDescription);
        }
        return cause;
    }
}
