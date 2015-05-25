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

package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.presentation.model.DeploymentModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Maps {@link Deployment} onto {@link DeploymentModel}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentModelDataMapper {

    @Inject
    public DeploymentModelDataMapper() {
    }

    /**
     * Maps {@link Deployment} to {@link DeploymentModel}
     *
     * @param deployment The {@link Deployment} to be mapped
     * @return The {@link DeploymentModel} entity
     */
    public DeploymentModel map(Deployment deployment) {
        if (deployment == null) {
            throw new IllegalArgumentException("Cannot map a null value");
        }

        DeploymentModel deploymentModel = new DeploymentModel();
        deploymentModel._id = deployment._id;
        deploymentModel.setStatus(DeploymentModel.Status.valueOf(deployment.getStatus().name()));
        deploymentModel.setTitle(deployment.getTitle());
        deploymentModel.setUrl(deployment.getUrl());
        return deploymentModel;
    }

    /**
     * Unmaps from {@link DeploymentModel} to {@link Deployment}
     *
     * @param deploymentModel The {@link DeploymentModel} to be unmapped
     * @return The {@link Deployment} entity
     */

    public Deployment map(DeploymentModel deploymentModel) {

        if (deploymentModel == null) {
            throw new IllegalArgumentException("Cannot unmap a null value");
        }

        Deployment deployment = new Deployment();
        deployment.setTitle(deploymentModel.getTitle());
        deployment.setStatus(Deployment.Status.valueOf(deploymentModel.getStatus().name()));
        deployment.setUrl(deploymentModel.getUrl());
        deployment._id = deploymentModel._id;

        return deployment;
    }

    /**
     * Maps a list {@link Deployment} into a list of {@link DeploymentModel}.
     *
     * @param deploymentList List to be mapped.
     * @return {@link DeploymentModel}
     */
    public List<DeploymentModel> map(List<Deployment> deploymentList) {
        List<DeploymentModel> deploymentModels = new ArrayList<>();

        if (deploymentList != null && !deploymentList.isEmpty()) {
            for (Deployment deployment : deploymentList) {
                deploymentModels.add(map(deployment));
            }
        }

        return deploymentModels;
    }

    /**
     * Maps a list {@link DeploymentModel} into a list of {@link Deployment}.
     *
     * @param deploymentModels List to be unmapped.
     * @return {@link DeploymentModel}
     */
    public List<Deployment> unmap(List<DeploymentModel> deploymentModels) {
        List<Deployment> deploymentModelList = new ArrayList<>();

        if (deploymentModels != null && !deploymentModels.isEmpty()) {
            for (DeploymentModel deploymentModel : deploymentModels) {
                deploymentModelList.add(map(deploymentModel));
            }
        }

        return deploymentModelList;
    }

}
