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

package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Fetch {@link com.ushahidi.android.data.entity.DeploymentEntity} use case
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FetchDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    private String mUrl = null;

    /**
     * Default constructor
     *
     * @param deploymentRepository The deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected FetchDeploymentUsecase(DeploymentRepository deploymentRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    /**
     * Sets the deployment url to be used to fetch the {@link com.ushahidi.android.data.entity.DeploymentEntity}
     *
     * @param url The url associated with the deployment
     */
    public void setDeploymentUrl(String url) {
        mUrl = url;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mUrl == null) {
                throw new IllegalStateException("DeploymentUrl should be a non null value");
        }
        return mDeploymentRepository.getDeploymentEntity(mUrl);
    }

}