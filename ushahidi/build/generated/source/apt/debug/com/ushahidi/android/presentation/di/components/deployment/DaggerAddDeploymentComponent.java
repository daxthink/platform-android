package com.ushahidi.android.presentation.di.components.deployment;

import android.app.Activity;
import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.presentation.di.component.ApplicationComponent;
import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.module.ActivityModule_ActivityFactory;
import com.addhen.android.raiburari.presentation.state.ApplicationState;
import com.addhen.android.raiburari.presentation.ui.activity.BaseActivity;
import com.addhen.android.raiburari.presentation.ui.activity.BaseActivity_MembersInjector;
import com.ushahidi.android.data.entity.mapper.DeploymentEntityDataMapper_Factory;
import com.ushahidi.android.data.repository.DeploymentDataRepository;
import com.ushahidi.android.data.repository.DeploymentDataRepository_Factory;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSourceFactory_Factory;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecase;
import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecase_Factory;
import com.ushahidi.android.presentation.di.modules.AppModule;
import com.ushahidi.android.presentation.di.modules.AppModule_ProvideDeploymentRepositoryFactory;
import com.ushahidi.android.presentation.di.modules.deployment.AddDeploymentModule;
import com.ushahidi.android.presentation.di.modules.deployment.AddDeploymentModule_ProvideAddDeploymentUseCaseFactory;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper_Factory;
import com.ushahidi.android.presentation.presenter.AddDeploymentPresenter;
import com.ushahidi.android.presentation.presenter.AddDeploymentPresenter_Factory;
import com.ushahidi.android.presentation.ui.activity.AddDeploymentActivity;
import com.ushahidi.android.presentation.ui.fragment.AddDeploymentFragment;
import com.ushahidi.android.presentation.ui.fragment.AddDeploymentFragment_MembersInjector;
import com.ushahidi.android.presentation.ui.navigation.Launcher;
import com.ushahidi.android.presentation.ui.navigation.Launcher_Factory;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerAddDeploymentComponent implements AddDeploymentComponent {
  private Provider<Activity> activityProvider;
  private Provider<Launcher> launcherProvider;
  private Provider<ApplicationState> applicationStateProvider;
  private MembersInjector<BaseActivity> baseActivityMembersInjector;
  private MembersInjector<AddDeploymentActivity> addDeploymentActivityMembersInjector;
  private Provider<DeploymentDataRepository> deploymentDataRepositoryProvider;
  private Provider<DeploymentRepository> provideDeploymentRepositoryProvider;
  private Provider<ThreadExecutor> threadExecutorProvider;
  private Provider<PostExecutionThread> postExecutionThreadProvider;
  private Provider<AddDeploymentUsecase> addDeploymentUsecaseProvider;
  private Provider<AddDeploymentUsecase> provideAddDeploymentUseCaseProvider;
  private Provider<AddDeploymentPresenter> addDeploymentPresenterProvider;
  private MembersInjector<AddDeploymentFragment> addDeploymentFragmentMembersInjector;

  private DaggerAddDeploymentComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.activityProvider = ScopedProvider.create(ActivityModule_ActivityFactory.create(builder.activityModule));
    this.launcherProvider = Launcher_Factory.create(activityProvider);
    this.applicationStateProvider = new Factory<ApplicationState>() {
      @Override public ApplicationState get() {
        ApplicationState provided = builder.applicationComponent.applicationState();
        if (provided == null) {
          throw new NullPointerException("Cannot return null from a non-@Nullable component method");
        }
        return provided;
      }
    };
    this.baseActivityMembersInjector = BaseActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), applicationStateProvider);
    this.addDeploymentActivityMembersInjector = MembersInjectors.delegatingTo(baseActivityMembersInjector);
    this.deploymentDataRepositoryProvider = DeploymentDataRepository_Factory.create(DeploymentDataSourceFactory_Factory.create(), DeploymentEntityDataMapper_Factory.create());
    this.provideDeploymentRepositoryProvider = ScopedProvider.create(AppModule_ProvideDeploymentRepositoryFactory.create(builder.appModule, deploymentDataRepositoryProvider));
    this.threadExecutorProvider = new Factory<ThreadExecutor>() {
      @Override public ThreadExecutor get() {
        ThreadExecutor provided = builder.applicationComponent.threadExecutor();
        if (provided == null) {
          throw new NullPointerException("Cannot return null from a non-@Nullable component method");
        }
        return provided;
      }
    };
    this.postExecutionThreadProvider = new Factory<PostExecutionThread>() {
      @Override public PostExecutionThread get() {
        PostExecutionThread provided = builder.applicationComponent.postExecutionThread();
        if (provided == null) {
          throw new NullPointerException("Cannot return null from a non-@Nullable component method");
        }
        return provided;
      }
    };
    this.addDeploymentUsecaseProvider = AddDeploymentUsecase_Factory.create((MembersInjector) MembersInjectors.noOp(), provideDeploymentRepositoryProvider, threadExecutorProvider, postExecutionThreadProvider);
    this.provideAddDeploymentUseCaseProvider = ScopedProvider.create(AddDeploymentModule_ProvideAddDeploymentUseCaseFactory.create(builder.addDeploymentModule, addDeploymentUsecaseProvider));
    this.addDeploymentPresenterProvider = AddDeploymentPresenter_Factory.create((MembersInjector) MembersInjectors.noOp(), provideAddDeploymentUseCaseProvider, DeploymentModelDataMapper_Factory.create());
    this.addDeploymentFragmentMembersInjector = AddDeploymentFragment_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), addDeploymentPresenterProvider);
  }

  @Override
  public Launcher launcher() {  
    return launcherProvider.get();
  }

  @Override
  public void inject(AddDeploymentActivity addDeploymentActivity) {  
    addDeploymentActivityMembersInjector.injectMembers(addDeploymentActivity);
  }

  @Override
  public void inject(AddDeploymentFragment addDeploymentFragment) {  
    addDeploymentFragmentMembersInjector.injectMembers(addDeploymentFragment);
  }

  @Override
  public AddDeploymentPresenter addDeploymentPresenter() {  
    return addDeploymentPresenterProvider.get();
  }

  public static final class Builder {
    private ActivityModule activityModule;
    private AppModule appModule;
    private AddDeploymentModule addDeploymentModule;
    private ApplicationComponent applicationComponent;
  
    private Builder() {  
    }
  
    public AddDeploymentComponent build() {  
      if (activityModule == null) {
        throw new IllegalStateException("activityModule must be set");
      }
      if (appModule == null) {
        this.appModule = new AppModule();
      }
      if (addDeploymentModule == null) {
        this.addDeploymentModule = new AddDeploymentModule();
      }
      if (applicationComponent == null) {
        throw new IllegalStateException("applicationComponent must be set");
      }
      return new DaggerAddDeploymentComponent(this);
    }
  
    public Builder activityModule(ActivityModule activityModule) {  
      if (activityModule == null) {
        throw new NullPointerException("activityModule");
      }
      this.activityModule = activityModule;
      return this;
    }
  
    public Builder appModule(AppModule appModule) {  
      if (appModule == null) {
        throw new NullPointerException("appModule");
      }
      this.appModule = appModule;
      return this;
    }
  
    public Builder addDeploymentModule(AddDeploymentModule addDeploymentModule) {  
      if (addDeploymentModule == null) {
        throw new NullPointerException("addDeploymentModule");
      }
      this.addDeploymentModule = addDeploymentModule;
      return this;
    }
  
    public Builder applicationComponent(ApplicationComponent applicationComponent) {  
      if (applicationComponent == null) {
        throw new NullPointerException("applicationComponent");
      }
      this.applicationComponent = applicationComponent;
      return this;
    }
  }
}

