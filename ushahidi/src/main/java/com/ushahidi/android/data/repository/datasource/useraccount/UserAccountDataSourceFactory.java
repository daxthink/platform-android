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

package com.ushahidi.android.data.repository.datasource.useraccount;

import com.ushahidi.android.data.api.UserApi;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class UserAccountDataSourceFactory {

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param ushAccessTokenManager The access token manager
     */
    @Inject
    public UserAccountDataSourceFactory(UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Creates {@link UserAccountApiDataSource}
     *
     * @return The user account api data source
     */
    public UserAccountDataSource createApiDataSource() {
        final UserApi userApi = new UserApi(mUshAccessTokenManager);
        return new UserAccountApiDataSource(userApi);
    }
}
