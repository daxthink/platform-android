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

package com.ushahidi.android.data.repository.datasource.deployment;

import com.ushahidi.android.data.entity.DeploymentEntity;

import java.util.List;

import rx.Observable;

/**
 * All different source providers must implement this interface to provide deployment data
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface DeploymentDataSource {

    /**
     * Get an {@link Observable} which will emit a List of {@link DeploymentEntity}.
     */
    Observable<List<DeploymentEntity>> getDeploymentEntityList();

    /**
     * Get an {@link Observable} which will emit a {@link DeploymentEntity} by its id.
     *
     * @param deploymentId The id to retrieve user data.
     */
    Observable<DeploymentEntity> getDeploymentEntity(Long deploymentId);

    /**
     * Get an {@link Observable} which will emit a {@link DeploymentEntity} by its id.
     *
     * @param status The deployment status to be used for retrieving a deployment
     */
    Observable<DeploymentEntity> getByStatus(DeploymentEntity.Status status);

    /**
     * Adds an {@link DeploymentEntity} to storage and then returns an {@link Observable} for all
     * subscribers
     * to react to it.
     *
     * @param deployment The deployment to be added
     */
    Observable<Long> addDeploymentEntity(DeploymentEntity deployment);

    /**
     * Adds an {@link DeploymentEntity} to storage and then returns an {@link Observable} for all
     * subscribers
     * to react to it.
     *
     * @param deployment The deployment to be added
     */
    Observable<Long> updateDeploymentEntity(DeploymentEntity deployment);

    /**
     * Deletes an {@link DeploymentEntity} from storage and then returns an {@link Observable} for
     * all
     * subscribers to react to it.
     *
     * @param deploymentId The deployment to be deleted
     */
    Observable<Long> deleteDeploymentEntity(Long deploymentId);
}
