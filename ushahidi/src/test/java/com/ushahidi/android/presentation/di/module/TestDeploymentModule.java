package com.ushahidi.android.presentation.di.module;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.data.repository.DeploymentDataRepositoryTest;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class TestDeploymentModule {

    @Provides
    @ActivityScope
    DeploymentRepository provideDeploymentRepository(
            DeploymentDataRepositoryTest deploymentDataRepositoryTest) {
        return deploymentDataRepositoryTest;
    }
}
