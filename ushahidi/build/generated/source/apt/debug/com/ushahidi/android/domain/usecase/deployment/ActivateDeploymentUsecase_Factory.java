package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ActivateDeploymentUsecase_Factory implements Factory<ActivateDeploymentUsecase> {
  private final MembersInjector<ActivateDeploymentUsecase> membersInjector;
  private final Provider<DeploymentRepository> deploymentRepositoryProvider;
  private final Provider<ThreadExecutor> threadExecutorProvider;
  private final Provider<PostExecutionThread> postExecutionThreadProvider;

  public ActivateDeploymentUsecase_Factory(MembersInjector<ActivateDeploymentUsecase> membersInjector, Provider<DeploymentRepository> deploymentRepositoryProvider, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
    assert deploymentRepositoryProvider != null;
    this.deploymentRepositoryProvider = deploymentRepositoryProvider;
    assert threadExecutorProvider != null;
    this.threadExecutorProvider = threadExecutorProvider;
    assert postExecutionThreadProvider != null;
    this.postExecutionThreadProvider = postExecutionThreadProvider;
  }

  @Override
  public ActivateDeploymentUsecase get() {  
    ActivateDeploymentUsecase instance = new ActivateDeploymentUsecase(deploymentRepositoryProvider.get(), threadExecutorProvider.get(), postExecutionThreadProvider.get());
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<ActivateDeploymentUsecase> create(MembersInjector<ActivateDeploymentUsecase> membersInjector, Provider<DeploymentRepository> deploymentRepositoryProvider, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider) {  
    return new ActivateDeploymentUsecase_Factory(membersInjector, deploymentRepositoryProvider, threadExecutorProvider, postExecutionThreadProvider);
  }
}

