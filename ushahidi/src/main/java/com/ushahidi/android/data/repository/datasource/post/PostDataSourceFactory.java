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

package com.ushahidi.android.data.repository.datasource.post;

import com.ushahidi.android.data.api.PostApi;
import com.ushahidi.android.data.api.service.PostService;
import com.ushahidi.android.data.database.PostDatabaseHelper;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

/**
 * Factory method for fetching post data source
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostDataSourceFactory {

    private PostDatabaseHelper mPostDatabaseHelper;

    private PostService mPostService;

    public PostDataSourceFactory(
            @NonNull PostDatabaseHelper postDatabaseHelper) {
        mPostDatabaseHelper = postDatabaseHelper;
    }

    /**
     * Call this to set the Post API service
     *
     * @param postService The post service
     */
    public void setPostService(@NonNull PostService postService) {
        mPostService = postService;
    }

    public PostDatabaseDataSource createPostDatabaseDataSource() {
        return new PostDatabaseDataSource(mPostDatabaseHelper);
    }

    public PostDataSource createPostApiDataSource() {
        if (mPostService == null) {
            throw new RuntimeException("Please call setPostService(...)");
        }
        final PostApi postApi = new PostApi(mPostService);
        return new PostApiDataSource(postApi, mPostDatabaseHelper);
    }
}
