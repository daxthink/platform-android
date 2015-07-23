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
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    private Deployment mDeployment;

    /**
     * Default constructor
     *
     * @param deploymentRepository The deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected AddDeploymentUsecase(DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    /**
     * Sets the deployment
     *
     * @param deployment The deployment to be added
     */
    public void setDeployment(Deployment deployment) {
        mDeployment = deployment;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeployment == null) {
            throw new RuntimeException("Deployment is null. You must call setDeployment(...)");
        }
        return mDeploymentRepository.addEntity(mDeployment);
    }
}
