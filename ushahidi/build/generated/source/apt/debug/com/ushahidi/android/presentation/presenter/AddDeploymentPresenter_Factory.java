package com.ushahidi.android.presentation.presenter;

import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecase;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AddDeploymentPresenter_Factory implements Factory<AddDeploymentPresenter> {
  private final MembersInjector<AddDeploymentPresenter> membersInjector;
  private final Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider;
  private final Provider<DeploymentModelDataMapper> deploymentModelDataMapperProvider;

  public AddDeploymentPresenter_Factory(MembersInjector<AddDeploymentPresenter> membersInjector, Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider, Provider<DeploymentModelDataMapper> deploymentModelDataMapperProvider) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
    assert addDeploymentUsecaseProvider != null;
    this.addDeploymentUsecaseProvider = addDeploymentUsecaseProvider;
    assert deploymentModelDataMapperProvider != null;
    this.deploymentModelDataMapperProvider = deploymentModelDataMapperProvider;
  }

  @Override
  public AddDeploymentPresenter get() {  
    AddDeploymentPresenter instance = new AddDeploymentPresenter(addDeploymentUsecaseProvider.get(), deploymentModelDataMapperProvider.get());
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<AddDeploymentPresenter> create(MembersInjector<AddDeploymentPresenter> membersInjector, Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider, Provider<DeploymentModelDataMapper> deploymentModelDataMapperProvider) {  
    return new AddDeploymentPresenter_Factory(membersInjector, addDeploymentUsecaseProvider, deploymentModelDataMapperProvider);
  }
}

