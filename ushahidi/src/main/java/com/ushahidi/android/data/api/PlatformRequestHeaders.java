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

package com.ushahidi.android.data.api;

import com.ushahidi.android.data.api.account.Session;

import android.text.TextUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PlatformRequestHeaders {

    public static final String HEADER_USER_AGENT = "User-Agent";

    private final Session mSession;

    private final String mUserAgent;

    public PlatformRequestHeaders(Session session, String userAgent) {
        mSession = session;
        mUserAgent = userAgent;
    }

    public final Map<String, String> getHeaders() {
        final HashMap<String, String> headers = new HashMap<>();
        headers.putAll(getExtraHeaders());
        if (!TextUtils.isEmpty(mUserAgent)) {
            headers.put(HEADER_USER_AGENT, mUserAgent);
        }
        headers.putAll(getAuthHeaders());
        return headers;
    }

    /**
     * Returns a list of extra HTTP headers (besides Authorization and User-Agent) to go along with
     * this request
     */
    protected Map<String, String> getExtraHeaders() {
        return Collections.emptyMap();
    }

    /**
     * @return a map of auth headers to go along with this request. Override this method if you
     * need to provide a special Authorization header.
     */
    public Map<String, String> getAuthHeaders() {
        if (mSession != null && mSession.getAuthToken() != null) {
            return mSession.getAuthToken().getAuthHeaders();
        }
        return Collections.emptyMap();
    }
}
