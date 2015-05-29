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
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.PostRepository;

import rx.Observable;

/**
 * Use case for listing {@link com.ushahidi.android.domain.entity.Post}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListPostUsecase extends Usecase {

    private final PostRepository mPostRepository;

    private Long mDeploymentId = null;

    private From mFrom;

    protected ListPostUsecase(PostRepository postRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mPostRepository = postRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.Post}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param from         Whether to fetch through the API or the local storage
     */
    public void setListPost(Long deploymentId, From from) {
        mDeploymentId = deploymentId;
        mFrom = from;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFrom == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setListPost(...)");
        }
        return mPostRepository.getPostList(mDeploymentId, mFrom);
    }
}
