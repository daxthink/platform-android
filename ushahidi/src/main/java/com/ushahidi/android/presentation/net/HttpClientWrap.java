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

package com.ushahidi.android.presentation.net;

import com.addhen.android.raiburari.presentation.util.Utils;
import com.ushahidi.android.presentation.exception.NetworkConnectionException;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Implements @{link Client} to provide a global detection for connectivity on the
 * device before the client attempts to make a connection. It add auth headers for interacting
 * with protected resources.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class HttpClientWrap implements Client {

    private Client mClient;

    private Context mContext;

    @Inject
    public HttpClientWrap(Context context, Client client) {
        mContext = context;
        mClient = client;
    }

    @Override
    public Response execute(Request request) throws IOException {
        if (!Utils.isNetworkConnected(mContext)) {
            throw RetrofitError
                    .unexpectedError("No internet", new NetworkConnectionException("No Internet"));
        } else {
            return mClient.execute(request);
        }
    }

}
