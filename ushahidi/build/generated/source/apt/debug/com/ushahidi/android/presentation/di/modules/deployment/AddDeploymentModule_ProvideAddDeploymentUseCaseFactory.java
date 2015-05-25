package com.ushahidi.android.presentation.di.modules.deployment;

import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecase;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AddDeploymentModule_ProvideAddDeploymentUseCaseFactory implements Factory<AddDeploymentUsecase> {
  private final AddDeploymentModule module;
  private final Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider;

  public AddDeploymentModule_ProvideAddDeploymentUseCaseFactory(AddDeploymentModule module, Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider) {  
    assert module != null;
    this.module = module;
    assert addDeploymentUsecaseProvider != null;
    this.addDeploymentUsecaseProvider = addDeploymentUsecaseProvider;
  }

  @Override
  public AddDeploymentUsecase get() {  
    AddDeploymentUsecase provided = module.provideAddDeploymentUseCase(addDeploymentUsecaseProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<AddDeploymentUsecase> create(AddDeploymentModule module, Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider) {  
    return new AddDeploymentModule_ProvideAddDeploymentUseCaseFactory(module, addDeploymentUsecaseProvider);
  }
}

