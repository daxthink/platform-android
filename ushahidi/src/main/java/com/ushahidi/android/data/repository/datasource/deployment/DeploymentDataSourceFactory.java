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

import android.support.annotation.NonNull;

import com.ushahidi.android.data.api.DeploymentApi;
import com.ushahidi.android.data.database.DeploymentDatabaseHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A factory class for creating the different datasource for {@link com.ushahidi.android.data.entity.DeploymentEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class DeploymentDataSourceFactory {

    private final DeploymentDatabaseHelper mDeploymentDatabaseHelper;

    /**
     * Default constructor that constructs {@link DeploymentDataSourceFactory}
     *
     * @param deploymentDatabaseHelper The deployment database helper
     */
    @Inject
    DeploymentDataSourceFactory(@NonNull DeploymentDatabaseHelper deploymentDatabaseHelper) {
        mDeploymentDatabaseHelper = deploymentDatabaseHelper;
    }

    /**
     * Creates {@link DeploymentDatabaseDataSource}
     *
     * @return The deployment database source
     */
    public DeploymentDataSource createDatabaseDataSource() {
        return new DeploymentDatabaseDataSource(mDeploymentDatabaseHelper);
    }

    /**
     * Creates {@link DeploymentApiDataSource}
     *
     * @return The deployment api data source
     */
    public DeploymentApiDataSource createDeploymentApiDataSource() {
        DeploymentApi deploymentApi = new DeploymentApi();
        return new DeploymentApiDataSource(deploymentApi, mDeploymentDatabaseHelper);
    }

}
