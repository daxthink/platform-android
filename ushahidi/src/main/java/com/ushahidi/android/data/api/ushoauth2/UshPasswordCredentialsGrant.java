package com.ushahidi.android.data.api.ushoauth2;

import com.ushahidi.android.data.api.auth.AccessTokenRequestBody;
import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessToken;
import com.ushahidi.android.data.api.heimdalldroid.grants.OAuth2ResourceOwnerPasswordCredentialsGrant;
import com.ushahidi.android.data.api.service.RestfulService;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;

/**
 * Authorization code for Ushahidi
 */
public class UshPasswordCredentialsGrant extends
        OAuth2ResourceOwnerPasswordCredentialsGrant<OAuth2AccessToken> {

    public String clientSecret;

    public String clientId;

    private final RestfulService mRestfulService;

    @Inject
    public UshPasswordCredentialsGrant(@NonNull RestfulService restfulService) {
        mRestfulService = restfulService;
    }

    @Override
    public Observable<OAuth2AccessToken> grantNewAccessToken() {
        AccessTokenRequestBody accessTokenRequestBody = new AccessTokenRequestBody(username,
                password, GRANT_TYPE, clientId, clientSecret, scope);
        return mRestfulService.grantNewAccessToken(accessTokenRequestBody);
    }
}
