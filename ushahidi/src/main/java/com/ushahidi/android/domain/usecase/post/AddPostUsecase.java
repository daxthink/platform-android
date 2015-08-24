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

package com.ushahidi.android.domain.usecase.post;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.repository.PostRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Usecase for adding a Post online
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostUsecase extends Usecase {

    private final PostRepository mPostRepository;

    private Post mPost;

    /**
     * Default constructor
     *
     * @param postRepository      The post repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    public AddPostUsecase(PostRepository postRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mPostRepository = postRepository;
    }

    /**
     * Adds post
     *
     * @param post The post to be added
     */
    public void setAddPost(Post post) {
        mPost = post;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mPost == null) {
            throw new RuntimeException("Post cannot be null. You must call setAddPost(...)");
        }
        return mPostRepository.putPost(mPost);
    }
}
