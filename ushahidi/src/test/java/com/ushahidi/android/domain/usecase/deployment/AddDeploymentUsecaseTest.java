package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.presentation.di.component.ApplicationComponent;
import com.addhen.android.raiburari.presentation.di.component.DaggerApplicationComponent;
import com.addhen.android.raiburari.presentation.di.module.ApplicationModule;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.presentation.UshahidiApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Test case for {@link AddDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class AddDeploymentUsecaseTest {


    @Before
    public void setUp() throws Exception {
        // DaggerMainActivityRobolectricTest_TestAppComponent may not be visible in Android Studio
        // but the code compiles and works. This issue should be addressed in gradle plugin 1.3.x
        // https://bitbucket.org/hvisser/android-apt/issue/36/no-dagger2-generated-files-for-junit-tests
        ApplicationComponent appComponent = DaggerApplicationComponent.builder().applicationModule(
                new ApplicationModule((UshahidiApplication) RuntimeEnvironment.application))
                .build();
        ((UshahidiApplication) RuntimeEnvironment.application).setTestComponent(appComponent);
    }

    @Test
    public void shouldSuccessfullyAddDeployment() {

    }
}
