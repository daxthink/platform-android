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

package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.data.entity.mapper.DeploymentEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentApiDataSource;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSource;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSourceFactory;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Implementation of {@link DeploymentRepository} for manipulating deployment data
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class DeploymentDataRepository implements DeploymentRepository {

    private final DeploymentDataSourceFactory mDeploymentDataStoreFactory;

    private final DeploymentEntityDataMapper mDeploymentEntityDataMapper;


    /**
     * Constructs a {@link DeploymentRepository}.
     *
     * @param dataSourceFactory          A factory to construct the different data source
     *                                   implementations.
     * @param deploymentEntityDataMapper {@link DeploymentEntityDataMapper}.
     */
    @Inject
    public DeploymentDataRepository(DeploymentDataSourceFactory dataSourceFactory,
            DeploymentEntityDataMapper deploymentEntityDataMapper) {
        mDeploymentDataStoreFactory = dataSourceFactory;
        mDeploymentEntityDataMapper = deploymentEntityDataMapper;
    }

    @Override
    public Observable<Deployment> getByStatus(Deployment.Status status) {
        final DeploymentDataSource deploymentDataSource = mDeploymentDataStoreFactory
                .createDatabaseDataSource();
        return deploymentDataSource.getByStatus(mDeploymentEntityDataMapper.map(status))
                .map(mDeploymentEntityDataMapper::map);
    }

    @Override
    public Observable<List<Deployment>> getEntities() {
        final DeploymentDataSource deploymentDataSource = mDeploymentDataStoreFactory
                .createDatabaseDataSource();
        return deploymentDataSource.getDeploymentEntityList().map(mDeploymentEntityDataMapper::map
        );
    }

    @Override
    public Observable<Deployment> getEntity(Long id) {
        final DeploymentDataSource deploymentDataSource = mDeploymentDataStoreFactory
                .createDatabaseDataSource();
        return deploymentDataSource.getDeploymentEntity(id)
                .map(mDeploymentEntityDataMapper::map);
    }

    @Override
    public Observable<Long> addEntity(Deployment deployment) {
        final DeploymentDataSource deploymentDataSource = mDeploymentDataStoreFactory
                .createDatabaseDataSource();
        return deploymentDataSource
                .addDeploymentEntity(mDeploymentEntityDataMapper.map(deployment));
    }

    @Override
    public Observable<Long> updateEntity(Deployment deployment) {
        final DeploymentDataSource deploymentDataSource = mDeploymentDataStoreFactory
                .createDatabaseDataSource();
        return deploymentDataSource
                .updateDeploymentEntity(mDeploymentEntityDataMapper.map(deployment));
    }

    @Override
    public Observable<Long> deleteEntity(Long deploymentId) {
        final DeploymentDataSource deploymentDataSource = mDeploymentDataStoreFactory
                .createDatabaseDataSource();
        return deploymentDataSource.deleteDeploymentEntity(deploymentId);
    }

    @Override
    public Observable<DeploymentEntity> getDeploymentEntity(String url) {
        final DeploymentApiDataSource deploymentApiDataSource = mDeploymentDataStoreFactory
                .createDeploymentApiDataSource();
        return deploymentApiDataSource.getDeploymentEntity(url);
    }

}
