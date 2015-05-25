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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.domain.entity.Deployment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Maps {@link com.ushahidi.android.domain.entity.Deployment} onto {@link
 * com.ushahidi.android.data.entity.DeploymentEntity} and vice versa
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentEntityDataMapper {

    @Inject
    public DeploymentEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link com.ushahidi.android.data.entity.DeploymentEntity} to {@link Deployment}
     *
     * @param deploymentEntity The {@link DeploymentEntity} to be
     *                         mapped
     * @return The {@link Deployment} entity
     */
    public Deployment map(DeploymentEntity deploymentEntity) {
        Deployment deployment = null;

        if (deploymentEntity != null) {
            deployment = new Deployment();
            deployment._id = deploymentEntity._id;
            deployment.setStatus(Deployment.Status.valueOf(deploymentEntity.getStatus().name()));
            deployment.setTitle(deploymentEntity.getTitle());
            deployment.setUrl(deploymentEntity.getUrl());
        }

        return deployment;
    }

    public DeploymentEntity map(Deployment deployment) {
        DeploymentEntity deploymentEntity = null;

        if (deployment != null) {
            deploymentEntity = new DeploymentEntity();
            deploymentEntity._id = deployment._id;
            deploymentEntity.setTitle(deployment.getTitle());
            deploymentEntity.setStatus(DeploymentEntity.Status.valueOf(
                    deployment.getStatus().name()));
            deploymentEntity.setUrl(deployment.getUrl());
        }
        return deploymentEntity;
    }

    /**
     * Maps a list {@link DeploymentEntity} into a list of {@link Deployment}.
     *
     * @param deploymentEntityList List to be mapped.
     * @return {@link Deployment}
     */
    public List<Deployment> map(List<DeploymentEntity> deploymentEntityList) {
        List<Deployment> deploymentList = new ArrayList<>();
        Deployment deployment;
        for (DeploymentEntity deploymentEntity : deploymentEntityList) {
            deployment = map(deploymentEntity);
            if (deployment != null) {
                deploymentList.add(deployment);
            }
        }

        return deploymentList;
    }

    public DeploymentEntity.Status map(Deployment.Status status) {
        return DeploymentEntity.Status.valueOf(
                status.name());
    }
}
