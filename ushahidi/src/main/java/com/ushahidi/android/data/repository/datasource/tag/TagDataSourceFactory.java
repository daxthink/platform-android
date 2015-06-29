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
import com.ushahidi.android.data.api.service.RestfulService;
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

    private RestfulService mTagService;

    @Inject
    public TagDataSourceFactory(
            @NonNull TagDatabaseHelper tagDatabaseHelper) {
        mTagDatabaseHelper = tagDatabaseHelper;
    }

    /**
     * Call this to set the Tag API service
     *
     * @param tagService The tag service
     */
    public void setTagService(@NonNull RestfulService tagService) {
        mTagService = tagService;
    }

    public TagDatabaseDataSource createTagDatabaseDataSource() {
        return new TagDatabaseDataSource(mTagDatabaseHelper);
    }

    public TagDataSource createTagApiDataSource() {
        if (mTagService == null) {
            throw new RuntimeException("Please call setTagService(...)");
        }
        final TagApi tagApi = new TagApi(mTagService);
        return new TagApiDataSource(tagApi, mTagDatabaseHelper);
    }
}
