package com.ushahidi.android.presentation.presenter;

import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ActivateDeploymentPresenter_Factory implements Factory<ActivateDeploymentPresenter> {
  private final MembersInjector<ActivateDeploymentPresenter> membersInjector;
  private final Provider<Usecase> activateDeploymentUsecaseProvider;
  private final Provider<DeploymentModelDataMapper> deploymentModelMapperProvider;

  public ActivateDeploymentPresenter_Factory(MembersInjector<ActivateDeploymentPresenter> membersInjector, Provider<Usecase> activateDeploymentUsecaseProvider, Provider<DeploymentModelDataMapper> deploymentModelMapperProvider) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
    assert activateDeploymentUsecaseProvider != null;
    this.activateDeploymentUsecaseProvider = activateDeploymentUsecaseProvider;
    assert deploymentModelMapperProvider != null;
    this.deploymentModelMapperProvider = deploymentModelMapperProvider;
  }

  @Override
  public ActivateDeploymentPresenter get() {  
    ActivateDeploymentPresenter instance = new ActivateDeploymentPresenter(activateDeploymentUsecaseProvider.get(), deploymentModelMapperProvider.get());
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<ActivateDeploymentPresenter> create(MembersInjector<ActivateDeploymentPresenter> membersInjector, Provider<Usecase> activateDeploymentUsecaseProvider, Provider<DeploymentModelDataMapper> deploymentModelMapperProvider) {  
    return new ActivateDeploymentPresenter_Factory(membersInjector, activateDeploymentUsecaseProvider, deploymentModelMapperProvider);
  }
}

