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
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostEntityDataMapper {

    private TagEntityDataMapper mTagEntityMapper;

    private PostValueEntityDataMapper mPostValueEntityMapper;

    /**
     * Default constructor
     */
    @Inject
    public PostEntityDataMapper() {
        mTagEntityMapper = new TagEntityDataMapper();
        mPostValueEntityMapper = new PostValueEntityDataMapper();
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
            if (postEntity.getStatus() != null) {
                post.setStatus(Post.Status.valueOf(postEntity.getStatus().name()));
            } else {
                post.setStatus(Post.Status.UNKNOWN);
            }
            post.setTitle(postEntity.getTitle());
            post.setCreated(postEntity.getCreated());
            post.setUpdated(postEntity.getUpdated());
            if (postEntity.getType() != null) {
                post.setType(Post.Type.valueOf(postEntity.getType().name()));
            } else {
                post.setType(Post.Type.UNKNOWN);
            }
            post.setSlug(postEntity.getSlug());
            post.setTags(mTagEntityMapper.map(postEntity.getTags()));
            post.setAuthorEmail(postEntity.getAuthorEmail());
            post.setAuthorRealname(postEntity.getAuthorRealname());
            post.setContent(postEntity.getContent());
            post.setDeploymentId(postEntity.getDeploymentId());
            post.setParent(postEntity.getParent());
            post.setValues(mPostValueEntityMapper.map(postEntity.getValues()));
        }
        return post;
    }

    /**
     * Maps {@link Post} onto {@link PostEntity}
     *
     * @param post The post to be mapped
     * @return The PostEntity
     */
    public PostEntity map(Post post) {
        PostEntity postEntity = null;
        if (post != null) {
            postEntity = new PostEntity();
            postEntity._id = post._id;
            if (post.getStatus() != null) {
                postEntity.setStatus(PostEntity.Status.valueOf(post.getStatus().name()));
            } else {
                postEntity.setStatus(PostEntity.Status.UNKNOWN);
            }
            postEntity.setTitle(post.getTitle());
            postEntity.setCreated(post.getCreated());
            postEntity.setUpdated(post.getUpdated());
            if (post.getType() != null) {
                postEntity.setType(PostEntity.Type.valueOf(post.getType().name()));
            } else {
                postEntity.setType(PostEntity.Type.UNKNOWN);
            }
            postEntity.setSlug(post.getSlug());
            postEntity.setTags(mTagEntityMapper.unmap(post.getTags()));
            postEntity.setAuthorEmail(post.getAuthorEmail());
            postEntity.setAuthorRealname(post.getAuthorRealname());
            postEntity.setContent(post.getContent());
            postEntity.setDeploymentId(post.getDeploymentId());
            postEntity.setParent(post.getParent());
            postEntity.setValues(mPostValueEntityMapper.map(post.getValues()));
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

    /**
     * Maps a list {@link PostEntity} into a list of {@link Post}.
     *
     * @param postList List to be mapped.
     * @return {@link Post}
     */
    public List<PostEntity> unmap(List<Post> postList) {
        List<PostEntity> postEntityList = new ArrayList<>();
        PostEntity postEntity;
        for (Post post : postList) {
            postEntity = map(post);
            if (postEntity != null) {
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}
