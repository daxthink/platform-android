package com.ushahidi.android.presentation.di.component;

import com.addhen.android.raiburari.presentation.di.component.ApplicationComponent;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecase;
import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecaseTest;
import com.ushahidi.android.presentation.di.module.TestDeploymentModule;

import dagger.Component;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
// Test Specific DI modules.
// TODO: put these in a reusable class
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {
        TestDeploymentModule.class})
public interface TestDeploymentComponent {

    void inject(AddDeploymentUsecaseTest addDeploymentUsecaseTest);

    AddDeploymentUsecase getAddDeploymentUsecase();
}
