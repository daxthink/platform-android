/*
 * Copyright (c) 2015 Ushahidi.
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

package com.ushahidi.android.presentation.exception;


import com.google.gson.Gson;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.R;
import com.ushahidi.android.data.api.oauth.ErrorResponse;
import com.ushahidi.android.data.exception.DeploymentNotFoundException;
import com.ushahidi.android.data.exception.FormAttributeNotFoundException;
import com.ushahidi.android.data.exception.GeoJsonNotFoundException;
import com.ushahidi.android.data.exception.PostNotFoundException;
import com.ushahidi.android.data.exception.TagNotFoundException;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.state.NoAccessTokenEvent;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

import retrofit.RetrofitError;

/**
 * Creates the various app exceptions
 *
 * @author @author Ushahidi Team <team@ushahidi.com>
 */
public final class ErrorMessageFactory {

    private ErrorMessageFactory() {
        // No instance
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context   Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof DeploymentNotFoundException) {
            message = context.getString(R.string.exception_message_deployment_not_found);
        } else if (exception instanceof NoAccessTokenFoundException) {
            message = context.getString(R.string.exception_message_not_logged_in);
        } else if (exception instanceof TagNotFoundException) {
            message = context.getString(R.string.exception_message_tag_not_found);
        } else if (exception instanceof PostNotFoundException) {
            message = context.getString(R.string.fetch_post_not_found);
        } else if (exception instanceof GeoJsonNotFoundException) {
            message = context.getString(R.string.geojson_not_found);
        } else if (exception instanceof IllegalStateException && exception.getMessage()
                .equalsIgnoreCase("No access token found.")) {
            // Double check to make sure exception being checked is that of access token
            // then trigger a login prompt
            UshahidiApplication.getRxEventBusInstance().send(new NoAccessTokenEvent());

        } else if (exception instanceof RetrofitError) {
            RetrofitError retrofitError = (RetrofitError) exception;
            if (retrofitError.getResponse().getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                UshahidiApplication.getRxEventBusInstance().send(new NoAccessTokenEvent());
            }
            message = getRetrofitErrorMessage(retrofitError);
        } else if (exception instanceof FormAttributeNotFoundException) {
            message = context.getString(R.string.form_attribute_not_found);
        }
        // Only print stacktrace when running a debug build
        if (BuildConfig.DEBUG) {
            exception.printStackTrace();
        }
        return message;
    }

    public static String getRetrofitErrorMessage(RetrofitError error) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(error.getResponse().getBody().in(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (reader != null) {
            ErrorResponse errorResponse = new Gson().fromJson(reader, ErrorResponse.class);
            return errorResponse.getErrorDescription();
        }
        return "";
    }
}
