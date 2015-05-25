package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.DeploymentEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSourceFactory;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DeploymentDataRepository_Factory implements Factory<DeploymentDataRepository> {
  private final Provider<DeploymentDataSourceFactory> dataSourceFactoryProvider;
  private final Provider<DeploymentEntityDataMapper> deploymentEntityDataMapperProvider;

  public DeploymentDataRepository_Factory(Provider<DeploymentDataSourceFactory> dataSourceFactoryProvider, Provider<DeploymentEntityDataMapper> deploymentEntityDataMapperProvider) {  
    assert dataSourceFactoryProvider != null;
    this.dataSourceFactoryProvider = dataSourceFactoryProvider;
    assert deploymentEntityDataMapperProvider != null;
    this.deploymentEntityDataMapperProvider = deploymentEntityDataMapperProvider;
  }

  @Override
  public DeploymentDataRepository get() {  
    return new DeploymentDataRepository(dataSourceFactoryProvider.get(), deploymentEntityDataMapperProvider.get());
  }

  public static Factory<DeploymentDataRepository> create(Provider<DeploymentDataSourceFactory> dataSourceFactoryProvider, Provider<DeploymentEntityDataMapper> deploymentEntityDataMapperProvider) {  
    return new DeploymentDataRepository_Factory(dataSourceFactoryProvider, deploymentEntityDataMapperProvider);
  }
}

