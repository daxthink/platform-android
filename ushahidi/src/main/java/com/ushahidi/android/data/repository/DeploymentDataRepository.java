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
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSource;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSourceFactory;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Implementation of {@link DeploymentRepository} for manipulating deployment data
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentDataRepository implements DeploymentRepository {

    private final DeploymentDataSourceFactory mDeploymentDataStoreFactory;

    private final DeploymentEntityDataMapper mDeploymentEntityDataMapper;

    private final DeploymentDataSource mDeploymentDataSource;

    private final Func1<List<DeploymentEntity>, List<Deployment>> deploymentListEntityMapper =
            new Func1<List<DeploymentEntity>, List<Deployment>>() {
                @Override
                public List<Deployment> call(List<DeploymentEntity> deploymentEntities) {
                    return DeploymentDataRepository.this.mDeploymentEntityDataMapper
                            .map(deploymentEntities);
                }
            };

    private final Func1<DeploymentEntity, Deployment>
            deploymentEntityMapper = new Func1<DeploymentEntity, Deployment>() {
        @Override
        public Deployment call(DeploymentEntity deploymentEntity) {
            return DeploymentDataRepository.this.mDeploymentEntityDataMapper.map(deploymentEntity);
        }
    };

    /**
     * Constructs a {@link DeploymentRepository}.
     *
     * @param dataSourceFactory          A factory to construct different data source
     *                                   implementations.
     * @param deploymentEntityDataMapper {@link DeploymentEntityDataMapper}.
     */
    @Inject
    public DeploymentDataRepository(DeploymentDataSourceFactory dataSourceFactory,
            DeploymentEntityDataMapper deploymentEntityDataMapper) {
        mDeploymentDataStoreFactory = dataSourceFactory;
        mDeploymentEntityDataMapper = deploymentEntityDataMapper;
        mDeploymentDataSource = mDeploymentDataStoreFactory.createDatabaseDataSource();
    }

    @Override
    public void getByStatus(Deployment.Status status) {

    }

    @Override
    public Observable<List<Deployment>> getEntities() {
        return null;
    }

    @Override
    public Observable<Deployment> getEntity(Long aLong) {
        return null;
    }

    @Override
    public Observable<Long> addEntity(Deployment deployment) {
        return null;
    }

    @Override
    public Observable<Long> updateEntity(Deployment deployment) {
        return null;
    }

    @Override
    public Observable<Long> deleteEntity(Long aLong) {
        return null;
    }
}
