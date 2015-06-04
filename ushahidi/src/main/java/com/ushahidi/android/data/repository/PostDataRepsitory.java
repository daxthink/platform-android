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

package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.PostEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.post.PostDataSource;
import com.ushahidi.android.data.repository.datasource.post.PostDataSourceFactory;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.repository.PostRepository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Implements {@link PostRepository} to provide tag data related manipulations
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostDataRepsitory implements PostRepository {

    private final PostEntityDataMapper mPostEntityDataMapper;

    private final PostDataSourceFactory mPostDataSourceFactory;

    @Inject
    public PostDataRepsitory(@NonNull PostDataSourceFactory postDataSourceFactory,
            @NonNull PostEntityDataMapper postEntityDataMapper) {
        mPostDataSourceFactory = postDataSourceFactory;
        mPostEntityDataMapper = postEntityDataMapper;
    }

    @Override
    public Observable<List<Post>> getPostList(Long deploymentId, From from) {
        PostDataSource postDataSource;
        if (from.equals(From.ONLINE)) {
            postDataSource = mPostDataSourceFactory.createPostApiDataSource();
        } else {
            postDataSource = mPostDataSourceFactory.createPostDatabaseDataSource();
        }
        return postDataSource.getPostEntityList(deploymentId).map(mPostEntityDataMapper::map);
    }

    @Override
    public Observable<List<Post>> search(Long deploymentId, String query) {
        final PostDataSource postDataSource = mPostDataSourceFactory.createPostDatabaseDataSource();
        return postDataSource.search(deploymentId, query).map(mPostEntityDataMapper::map);
    }

    @Override
    public Observable<Long> putPost(List<Post> posts) {
        final PostDataSource postDataSource = mPostDataSourceFactory.createPostDatabaseDataSource();
        return postDataSource.putPostEntity(mPostEntityDataMapper.unmap(posts));
    }

    @Override
    public Observable<Boolean> deletePost(Post post) {
        final PostDataSource postDataSource = mPostDataSourceFactory.createPostDatabaseDataSource();
        return postDataSource.deletePostEntity(mPostEntityDataMapper.map(post));
    }

    @Override
    public Observable<Post> getPost(Long deploymentId, Long postEntityId) {
        final PostDataSource postDataSource = mPostDataSourceFactory.createPostDatabaseDataSource();
        return postDataSource.getPostEntityById(deploymentId, postEntityId)
                .map(mPostEntityDataMapper::map);
    }
}
