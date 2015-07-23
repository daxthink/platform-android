package com.ushahidi.android.data.api.oauth;

import com.ushahidi.android.data.api.service.RestfulService;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import de.rheinfabrik.heimdall.grants.OAuth2ResourceOwnerPasswordCredentialsGrant;
import rx.Observable;

/**
 * Authorization code for Ushahidi
 */
public class UshPasswordCredentialsGrant extends
        OAuth2ResourceOwnerPasswordCredentialsGrant<OAuth2AccessToken> {

    private final RestfulService mRestfulService;

    private String clientSecret;

    private String clientId;

    /**
     * Default constructor
     *
     * @param restfulService The API service. Cannot be a null value
     */
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

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
