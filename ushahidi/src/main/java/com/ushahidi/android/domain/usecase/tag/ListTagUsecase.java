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

package com.ushahidi.android.domain.usecase.tag;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.TagRepository;

import rx.Observable;

/**
 * Use case for getting a list of tags either from online or
 * the local store.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListTagUsecase extends Usecase {

    private final TagRepository mTagRepository;

    private Long mDeploymentId = null;

    private From mFrom;

    /**
     * The list of tag use case
     *
     * @param tagRepository       The tag repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    protected ListTagUsecase(TagRepository tagRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mTagRepository = tagRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.Tag}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param from         Whether to fetch through the API or the local storage
     */
    public void setListTag(Long deploymentId, From from) {
        mDeploymentId = deploymentId;
        mFrom = from;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFrom == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setListTag(...)");
        }
        return mTagRepository.getTagList(mDeploymentId, mFrom);
    }
}
