package com.ushahidi.android.data.api.oauth;

import com.ushahidi.android.data.api.PlatformAuthConfig;
import com.ushahidi.android.data.api.PlatformService;
import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.UserAccountEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import de.rheinfabrik.heimdall.OAuth2AccessTokenManager;
import de.rheinfabrik.heimdall.OAuth2AccessTokenStorage;
import rx.Observable;

/**
 * Token manger used to handle all your access token needs with the Ushahidi API
 */
@Singleton
public final class UshAccessTokenManager extends OAuth2AccessTokenManager<OAuth2AccessToken> {

    private final PlatformService mPlatformService;

    private final PlatformAuthConfig mPlatformAuthConfig;

    /**
     * Default constructor
     *
     * @param storage            The access token storage
     * @param platformService    The platform service
     * @param platformAuthConfig The platform auth config
     */
    @Inject
    public UshAccessTokenManager(OAuth2AccessTokenStorage<OAuth2AccessToken> storage,
            PlatformService platformService, PlatformAuthConfig platformAuthConfig) {
        super(storage);
        mPlatformService = platformService;
        mPlatformAuthConfig = platformAuthConfig;
    }

    /**
     * Login's in a user
     *
     * @param userAccount The user account to be logged in
     * @return The password credentials grant
     */
    public UshPasswordCredentialsGrant login(UserAccountEntity userAccount) {
        UshPasswordCredentialsGrant ushPasswordCredentialsGrant = new UshPasswordCredentialsGrant(
                getRestfulService());
        ushPasswordCredentialsGrant.setClientId(mPlatformAuthConfig.getClientId());
        ushPasswordCredentialsGrant.setClientSecret(mPlatformAuthConfig.getClientSecret());
        ushPasswordCredentialsGrant.scope = mPlatformAuthConfig.getScope();
        ushPasswordCredentialsGrant.password = userAccount.getPassword();
        ushPasswordCredentialsGrant.username = userAccount.getAccountName();
        return ushPasswordCredentialsGrant;
    }

    /**
     * Returns a valid authorization header string using a preconfigured
     * Ushahidi RefreshAccessTokenGrant.
     *
     * @return An Observable that emits a valid access token as string
     */
    public Observable<String> getValidAccessToken() {
        UshRefreshAuthTokenGrant grant = new UshRefreshAuthTokenGrant(getRestfulService());
        grant.setClientId(mPlatformAuthConfig.getClientId());
        grant.setClientSecret(mPlatformAuthConfig.getClientSecret());
        grant.setScope(mPlatformAuthConfig.getScope());
        return super.getValidAccessToken(grant)
                .map(token -> token.tokenType + " " + token.accessToken);
    }

    /**
     * Gets the API services
     *
     * @return The API services
     */
    public RestfulService getRestfulService() {
        return mPlatformService.getService();
    }
}
