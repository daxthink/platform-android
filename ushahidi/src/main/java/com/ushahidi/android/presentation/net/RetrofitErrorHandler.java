package com.ushahidi.android.presentation.net;

import com.ushahidi.android.R;
import com.ushahidi.android.data.api.oauth.ErrorResponse;

import android.content.Context;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class RetrofitErrorHandler implements ErrorHandler {

    private final Context mContext;

    public RetrofitErrorHandler(Context context) {
        mContext = context;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        String errorDescription;

        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
            errorDescription = mContext.getString(R.string.exception_message_no_connection);
        } else {
            if (cause.getResponse() == null) {
                errorDescription = mContext.getString(R.string.exception_message_no_response);
            } else {

                // Error message handling - return a simple error to Retrofit handlers..
                try {
                    ErrorResponse errorResponse = (ErrorResponse) cause
                            .getBodyAs(ErrorResponse.class);
                    errorDescription = errorResponse.getErrorDescription();
                } catch (Exception ex) {
                    try {
                        errorDescription = mContext.getString(
                                R.string.exception_message_error_network_http_error,
                                cause.getResponse().getStatus());
                    } catch (Exception ex2) {
                        errorDescription = mContext.getString(R.string.exception_message_generic);
                    }
                }
            }
        }

        return new Exception(errorDescription);
    }
}


