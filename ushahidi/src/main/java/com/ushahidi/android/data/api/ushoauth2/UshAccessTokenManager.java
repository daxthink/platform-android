package com.ushahidi.android.data.api.ushoauth2;

import com.ushahidi.android.data.api.Constant;
import com.ushahidi.android.data.api.PlatformAuthConfig;
import com.ushahidi.android.data.api.PlatformService;
import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessToken;
import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessTokenManager;
import com.ushahidi.android.data.api.heimdalldroid.OAuth2AccessTokenStorage;
import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.UserAccountEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Token manger used to handle all your access token needs with the Ushahidi API
 */
@Singleton
public final class UshAccessTokenManager extends OAuth2AccessTokenManager<OAuth2AccessToken> {

    private final PlatformService mPlatformService;

    private final PlatformAuthConfig mPlatformAuthConfig;

    // Constructor
    @Inject
    public UshAccessTokenManager(OAuth2AccessTokenStorage<OAuth2AccessToken> storage,
            PlatformService platformService, PlatformAuthConfig platformAuthConfig) {
        super(storage);
        mPlatformService = platformService;
        mPlatformAuthConfig = platformAuthConfig;
    }

    /**
     * Login a user
     */
    public UshPasswordCredentialsGrant login(UserAccountEntity userAccount) {
        UshPasswordCredentialsGrant ushPasswordCredentialsGrant = new UshPasswordCredentialsGrant(
                getRestfulService());
        ushPasswordCredentialsGrant.clientId = mPlatformAuthConfig.clientId;
        ushPasswordCredentialsGrant.clientSecret = mPlatformAuthConfig.clientSecret;
        ushPasswordCredentialsGrant.scope = mPlatformAuthConfig.scope;
        ushPasswordCredentialsGrant.password = userAccount.getPassword();
        ushPasswordCredentialsGrant.username = userAccount.getPassword();
        return ushPasswordCredentialsGrant;
    }

    /**
     * Returns a valid authorization header string using a preconfigured
     * Ushahidi RefreshAccessTokenGrant.
     */
    public Observable<String> getValidAccessToken() {
        UshRefreshAuthTokenGrant grant = new UshRefreshAuthTokenGrant(getRestfulService());
        grant.clientId = mPlatformAuthConfig.clientId;
        grant.clientSecret = mPlatformAuthConfig.clientSecret;
        grant.scope = Constant.SCOPE;
        return super.getValidAccessToken(grant).map(
                token -> token.tokenType + " " + token.accessToken);
    }


    public RestfulService getRestfulService() {
        return mPlatformService.getService();
    }
}
