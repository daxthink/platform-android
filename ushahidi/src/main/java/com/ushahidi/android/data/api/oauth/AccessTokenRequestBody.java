package com.ushahidi.android.data.api.oauth;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.android.data.api.qualifier.ClientId;
import com.ushahidi.android.data.api.qualifier.ClientSecret;
import com.ushahidi.android.data.api.qualifier.GrantType;
import com.ushahidi.android.data.api.qualifier.Password;
import com.ushahidi.android.data.api.qualifier.Scope;
import com.ushahidi.android.data.api.qualifier.Username;

import java.io.Serializable;

/**
 * Body object used to exchange a code with an access token.
 */
public class AccessTokenRequestBody extends BaseResponse implements Serializable {

    private final String username;

    private final String password;

    @SerializedName("grant_type")
    private final String grantType;

    @SerializedName("client_id")
    private final String clientId;

    @SerializedName("client_secret")
    private final String clientSecret;

    private final String scope;

    /**
     * Default constructor
     *
     * @param username     The username
     * @param password     The password
     * @param grantType    The grant type
     * @param clientId     The client ID
     * @param clientSecret The client secret
     * @param scope        The scope
     */
    public AccessTokenRequestBody(@Username String username, @Password String password,
            @GrantType String grantType, @ClientId String clientId,
            @ClientSecret String clientSecret, @Scope String scope) {
        this.username = username;
        this.password = password;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }
}
