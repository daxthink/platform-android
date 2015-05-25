package com.ushahidi.android.presentation.di.modules;

import com.ushahidi.android.data.repository.DeploymentDataRepository;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideDeploymentRepositoryFactory implements Factory<DeploymentRepository> {
  private final AppModule module;
  private final Provider<DeploymentDataRepository> deploymentDataRepositoryProvider;

  public AppModule_ProvideDeploymentRepositoryFactory(AppModule module, Provider<DeploymentDataRepository> deploymentDataRepositoryProvider) {  
    assert module != null;
    this.module = module;
    assert deploymentDataRepositoryProvider != null;
    this.deploymentDataRepositoryProvider = deploymentDataRepositoryProvider;
  }

  @Override
  public DeploymentRepository get() {  
    DeploymentRepository provided = module.provideDeploymentRepository(deploymentDataRepositoryProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DeploymentRepository> create(AppModule module, Provider<DeploymentDataRepository> deploymentDataRepositoryProvider) {  
    return new AppModule_ProvideDeploymentRepositoryFactory(module, deploymentDataRepositoryProvider);
  }
}

