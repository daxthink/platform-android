package com.ushahidi.android.data.api.ushoauth2;

import com.ushahidi.android.data.api.auth.RefreshTokenRequestBody;
import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessToken;
import com.ushahidi.android.data.api.heimdalldroid.grants.OAuth2RefreshAccessTokenGrant;
import com.ushahidi.android.data.api.service.RestfulService;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;

/**
 * Refreshes Ushahidi's autho token
 */
public class UshRefreshAuthTokenGrant extends OAuth2RefreshAccessTokenGrant<OAuth2AccessToken> {

    // Properties

    public String username;

    public String password;

    public String grantType;

    public String clientId;

    public String clientSecret;

    public String scope;

    private RestfulService mRestfulService;

    @Inject
    public UshRefreshAuthTokenGrant(@NonNull RestfulService restfulService) {
        mRestfulService = restfulService;
    }

    @Override
    public Observable<OAuth2AccessToken> grantNewAccessToken() {
        RefreshTokenRequestBody body = new RefreshTokenRequestBody(grantType, clientId,
                clientSecret, scope);
        return mRestfulService.refreshAccessToken(body);
    }
}
