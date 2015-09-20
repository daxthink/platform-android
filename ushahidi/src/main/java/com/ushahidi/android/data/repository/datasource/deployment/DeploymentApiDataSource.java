package com.ushahidi.android.data.repository.datasource.deployment;

import android.support.annotation.NonNull;

import com.ushahidi.android.data.api.DeploymentApi;
import com.ushahidi.android.data.database.DeploymentDatabaseHelper;
import com.ushahidi.android.data.entity.DeploymentEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentApiDataSource implements DeploymentDataSource {

    private final DeploymentApi mDeploymentApi;

    private final DeploymentDatabaseHelper mDeploymentDatabaseHelper;

    /**
     * Default constructor that constructs {@link DeploymentApiDataSource}
     *
     * @param deploymentApi            The Deployment API
     * @param deploymentDatabaseHelper The Deployment database helper
     */
    public DeploymentApiDataSource(@NonNull DeploymentApi deploymentApi,
                                DeploymentDatabaseHelper deploymentDatabaseHelper) {
        mDeploymentApi = deploymentApi;
        mDeploymentDatabaseHelper = deploymentDatabaseHelper;
    }

    @Override
    public Observable<List<DeploymentEntity>> getDeploymentEntityList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<DeploymentEntity> getDeploymentEntity(Long deploymentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<DeploymentEntity> getByStatus(DeploymentEntity.Status status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> addDeploymentEntity(DeploymentEntity deployment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> updateDeploymentEntity(DeploymentEntity deployment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> deleteDeploymentEntity(Long deploymentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<DeploymentEntity> getDeploymentEntity(String url) {
        return mDeploymentApi.getDeploymentConfig(url).doOnNext(deploymentEntity -> mDeploymentDatabaseHelper
                .put(deploymentEntity));
    }

}
