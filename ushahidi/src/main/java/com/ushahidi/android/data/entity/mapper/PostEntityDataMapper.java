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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.PostEntity;
import com.ushahidi.android.domain.entity.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostEntityDataMapper {

    private TagEntityDataMapper mTagEntityMapper;

    @Inject
    public PostEntityDataMapper() {
        mTagEntityMapper = new TagEntityDataMapper();
    }

    /**
     * Maps {@link PostEntity} to {@link Post}
     *
     * @param postEntity The {@link PostEntity} to be
     *                   mapped
     * @return The {@link Post} entity
     */
    public Post map(PostEntity postEntity) {
        Post post = null;

        if (postEntity != null) {
            post = new Post();
            post._id = postEntity._id;
            post.setStatus(Post.Status.valueOf(postEntity.getStatus().name()));
            post.setTitle(postEntity.getTitle());
            post.setCreated(postEntity.getCreated());
            post.setUpdated(postEntity.getUpdated());
            post.setType(Post.Type.valueOf(postEntity.getType().name()));
            post.setSlug(postEntity.getSlug());
            post.setTags(mTagEntityMapper.map(postEntity.getTags()));
            post.setAuthorEmail(postEntity.getAuthorEmail());
            post.setAuthorRealname(postEntity.getAuthorRealname());
            post.setContent(postEntity.getContent());
            post.setDeploymentId(postEntity.getDeploymentId());

        }

        return post;
    }

    public PostEntity map(Post post) {
        PostEntity postEntity = null;

        if (post != null) {
            postEntity = new PostEntity();
            postEntity._id = post._id;
            postEntity.setStatus(PostEntity.Status.valueOf(post.getStatus().name()));
            postEntity.setTitle(post.getTitle());
            postEntity.setCreated(post.getCreated());
            postEntity.setUpdated(post.getUpdated());
            postEntity.setType(PostEntity.Type.valueOf(post.getType().name()));
            postEntity.setSlug(post.getSlug());
            postEntity.setTags(mTagEntityMapper.unmap(post.getTags()));
            postEntity.setAuthorEmail(post.getAuthorEmail());
            postEntity.setAuthorRealname(post.getAuthorRealname());
            postEntity.setContent(post.getContent());
            postEntity.setDeploymentId(post.getDeploymentId());
        }
        return postEntity;
    }

    /**
     * Maps a list {@link PostEntity} into a list of {@link Post}.
     *
     * @param postEntityList List to be mapped.
     * @return {@link Post}
     */
    public List<Post> map(List<PostEntity> postEntityList) {
        List<Post> postList = new ArrayList<>();
        Post post;
        for (PostEntity postEntity : postEntityList) {
            post = map(postEntity);
            if (post != null) {
                postList.add(post);
            }
        }

        return postList;
    }
}
