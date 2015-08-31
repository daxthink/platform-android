/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.domain.repository;

import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.Post;

import java.util.List;

import rx.Observable;

/**
 * Repository for manipulating {@link Post} entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface PostRepository {

    /**
     * @param deploymentId @param deploymentId The deploymentId to be used for fetching the API
     * @param from         Where to fetch the deployment from. Either Online or Offline.
     * @return The list of posts
     */
    Observable<List<Post>> getPostList(Long deploymentId, From from);

    /**
     * @param deploymentId The deploymentId to be used for fetching the API
     * @param postId       The ID of the post
     * @return The posts
     */
    Observable<Post> getPost(Long deploymentId, Long postId);

    /**
     * Search for a {@link Post}
     *
     * @param deploymentId The deploymentId to be used for fetching the API
     * @param query        The entity to be searched for.
     * @return The list of posts
     */
    Observable<List<Post>> search(Long deploymentId, String query);

    /**
     * Add / Update an {@link Post} to/in a storage.
     *
     * @param postList The post entities to be added.
     * @return The row affected
     */
    Observable<Long> putPost(List<Post> postList);

    /**
     * Add / Update an {@link Post} to/in a storage.
     *
     * @param post The post entity to be added.
     * @return The row affected
     */
    Observable<Long> putPost(Post post);

    /**
     * Delete an existing {@link Post} form storage.
     *
     * @param post The post's id to be deleted.
     * @return True if successfully deleted otherwise false
     */
    Observable<Boolean> deletePost(Post post);
}
