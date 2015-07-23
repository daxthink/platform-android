package com.ushahidi.android.data.api.oauth;

import com.ushahidi.android.data.api.service.RestfulService;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import de.rheinfabrik.heimdall.grants.OAuth2RefreshAccessTokenGrant;
import rx.Observable;

/**
 * Refreshes Ushahidi's autho token
 */
public class UshRefreshAuthTokenGrant extends OAuth2RefreshAccessTokenGrant<OAuth2AccessToken> {

    private String username;

    private String password;

    private String grantType;

    private String clientId;

    private String clientSecret;

    private String scope;

    private RestfulService mRestfulService;

    /**
     * Default constructor
     *
     * @param restfulService The API services. Cannot be null
     */
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
