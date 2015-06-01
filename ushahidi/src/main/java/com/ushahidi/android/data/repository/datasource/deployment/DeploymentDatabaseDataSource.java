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
import com.ushahidi.android.data.database.DeploymentDatabaseHelper;

import java.util.List;

import rx.Observable;

/**
 * Retrieves and adds a deployment data to the database
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentDatabaseDataSource implements DeploymentDataSource {

    private final DeploymentDatabaseHelper mDeploymentDatabaseHelper;

    public DeploymentDatabaseDataSource(DeploymentDatabaseHelper deploymentDatabaseHelper) {
        mDeploymentDatabaseHelper = deploymentDatabaseHelper;
    }

    @Override
    public Observable<List<DeploymentEntity>> getDeploymentEntityList() {
        return mDeploymentDatabaseHelper.getDeployments();
    }

    @Override
    public Observable<DeploymentEntity> getDeploymentEntity(Long deploymentId) {
        return mDeploymentDatabaseHelper.getDeployment(deploymentId);
    }

    @Override
    public Observable<DeploymentEntity> getByStatus(DeploymentEntity.Status status) {
        return mDeploymentDatabaseHelper.getByStatus(status);
    }

    @Override
    public Observable<Long> addDeploymentEntity(DeploymentEntity deployment) {
        return mDeploymentDatabaseHelper.put(deployment);
    }

    @Override
    public Observable<Long> updateDeploymentEntity(DeploymentEntity deployment) {
        return mDeploymentDatabaseHelper.put(deployment);
    }

    @Override
    public Observable<Long> deleteDeploymentEntity(Long deploymentId) {
        return mDeploymentDatabaseHelper.deleteDeployment(deploymentId);
    }
}
