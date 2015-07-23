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

import com.ushahidi.android.data.database.PostDatabaseHelper;
import com.ushahidi.android.data.entity.PostEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Data source for manipulating {@link com.ushahidi.android.data.entity.PostEntity} data to and
 * from
 * the database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostDatabaseDataSource implements PostDataSource {

    private PostDatabaseHelper mPostDatabaseHelper;

    /**
     * Default constructor
     *
     * @param postDatabaseHelper The post database helper
     */
    public PostDatabaseDataSource(@NonNull PostDatabaseHelper postDatabaseHelper) {
        mPostDatabaseHelper = postDatabaseHelper;
    }

    @Override
    public Observable<Long> putPostEntity(List<PostEntity> postEntities) {
        return mPostDatabaseHelper.putPosts(postEntities);
    }

    @Override
    public Observable<List<PostEntity>> getPostEntityList(Long deploymentId) {
        return mPostDatabaseHelper.getPostList(deploymentId);
    }

    @Override
    public Observable<PostEntity> getPostEntityById(Long deploymentId, Long postEntityId) {
        return mPostDatabaseHelper.getPostEntity(deploymentId, postEntityId);
    }

    @Override
    public Observable<Boolean> deletePostEntity(PostEntity postEntity) {
        return mPostDatabaseHelper.deletePost(postEntity);
    }

    @Override
    public Observable<List<PostEntity>> search(Long deploymentId, String query) {
        return mPostDatabaseHelper.search(deploymentId, query);
    }
}
