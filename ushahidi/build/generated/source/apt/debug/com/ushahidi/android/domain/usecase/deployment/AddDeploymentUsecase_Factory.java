package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AddDeploymentUsecase_Factory implements Factory<AddDeploymentUsecase> {
  private final MembersInjector<AddDeploymentUsecase> membersInjector;
  private final Provider<DeploymentRepository> deploymentRepositoryProvider;
  private final Provider<ThreadExecutor> threadExecutorProvider;
  private final Provider<PostExecutionThread> postExecutionThreadProvider;

  public AddDeploymentUsecase_Factory(MembersInjector<AddDeploymentUsecase> membersInjector, Provider<DeploymentRepository> deploymentRepositoryProvider, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider) {  
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
  public AddDeploymentUsecase get() {  
    AddDeploymentUsecase instance = new AddDeploymentUsecase(deploymentRepositoryProvider.get(), threadExecutorProvider.get(), postExecutionThreadProvider.get());
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<AddDeploymentUsecase> create(MembersInjector<AddDeploymentUsecase> membersInjector, Provider<DeploymentRepository> deploymentRepositoryProvider, Provider<ThreadExecutor> threadExecutorProvider, Provider<PostExecutionThread> postExecutionThreadProvider) {  
    return new AddDeploymentUsecase_Factory(membersInjector, deploymentRepositoryProvider, threadExecutorProvider, postExecutionThreadProvider);
  }
}

