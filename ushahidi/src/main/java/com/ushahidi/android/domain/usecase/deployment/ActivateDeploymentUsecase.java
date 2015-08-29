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

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Usecase for making a {@link Deployment} active
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ActivateDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    private List<Deployment> mDeployments;

    private int mPosition;

    /**
     * Default constructor
     *
     * @param deploymentRepository The Deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected ActivateDeploymentUsecase(DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    /**
     * Sets deployment
     *
     * @param deployments The deployment list cannot to be updated
     * @param position    The position of the deployment
     */
    public void setDeployment(List<Deployment> deployments, int position) {
        mDeployments = deployments;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeployments == null) {
            throw new RuntimeException("Deployments is null you need to call setDeployment(...)");
        }
        // Deactivate the previously activated deployment because we don't want more than one
        // deployments to be the active one.
        //TODO refactor so you don't have to go through a list of deployments to find the
        // current active deployment before making a deployment an active one.
        for (Deployment dep : mDeployments) {
            if (dep.getStatus() == Deployment.Status.ACTIVATED) {
                dep.setStatus(Deployment.Status.DEACTIVATED);
                mDeploymentRepository.updateEntity(dep);
            }
        }
        final Deployment deployment = mDeployments.get(mPosition);
        deployment.setStatus(Deployment.Status.ACTIVATED);
        return mDeploymentRepository.updateEntity(deployment);
    }
}
