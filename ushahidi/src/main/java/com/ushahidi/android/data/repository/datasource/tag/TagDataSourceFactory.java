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

package com.ushahidi.android.data.repository.datasource.tag;

import com.ushahidi.android.data.api.TagApi;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.database.TagDatabaseHelper;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory class for creating the various data source for the {@link
 * com.ushahidi.android.data.entity.TagEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class TagDataSourceFactory {

    private final TagDatabaseHelper mTagDatabaseHelper;

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor that constructs {@link TagDataSourceFactory}
     *
     * @param tagDatabaseHelper     The tag database helper
     * @param ushAccessTokenManager The access token manager
     */
    @Inject
    public TagDataSourceFactory(
            @NonNull TagDatabaseHelper tagDatabaseHelper,
            UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
        mTagDatabaseHelper = tagDatabaseHelper;
    }

    /**
     * Creates {@link TagDatabaseDataSource}
     *
     * @return The tag database data source
     */
    public TagDatabaseDataSource createTagDatabaseDataSource() {
        return new TagDatabaseDataSource(mTagDatabaseHelper);
    }

    /**
     * Creates {@link TagApiDataSource}
     *
     * @return The tag api data source
     */
    public TagDataSource createTagApiDataSource() {
        final TagApi tagApi = new TagApi(mUshAccessTokenManager);
        return new TagApiDataSource(tagApi, mTagDatabaseHelper);
    }
}
