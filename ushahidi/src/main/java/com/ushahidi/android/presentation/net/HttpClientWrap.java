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
import com.ushahidi.android.data.api.PlatformRequestHeaders;
import com.ushahidi.android.data.api.account.Session;
import com.ushahidi.android.presentation.exception.NetworkConnectionException;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Header;
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

    private Session mSession;

    @Inject
    public HttpClientWrap(Session session, Context context, Client client) {
        mContext = context;
        mClient = client;
        mSession = session;
    }

    @Override
    public Response execute(Request request) throws IOException {
        if (!Utils.isNetworkConnected(mContext)) {
            throw RetrofitError
                    .unexpectedError("No internet", new NetworkConnectionException("No Internet"));
        } else {
            return mClient.execute(new Request(request.getMethod(), request.getUrl(),
                    getAuthHeaders(request), request.getBody()));
        }
    }

    protected List<Header> getAuthHeaders(Request request) {
        // TODO: Remove hardcoded name with app name from the string resource
        final PlatformRequestHeaders authHeaders = new PlatformRequestHeaders(mSession,
                "Ushahidi Android app");

        // Copies the headers from the original list
        final List<Header> headers = new ArrayList<>(request.getHeaders());
        for (Map.Entry<String, String> header : authHeaders.getHeaders().entrySet()) {
            headers.add(new Header(header.getKey(), header.getValue()));
        }
        return headers;
    }
}
