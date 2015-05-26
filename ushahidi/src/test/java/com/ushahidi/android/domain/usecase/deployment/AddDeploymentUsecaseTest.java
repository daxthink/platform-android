package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.di.component.ApplicationComponent;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.component.DaggerTestDeploymentComponent;
import com.ushahidi.android.presentation.di.component.TestDeploymentComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.util.Transcript;

import javax.inject.Inject;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;

/**
 * Test case for {@link AddDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class AddDeploymentUsecaseTest {

    @Inject
    AddDeploymentUsecase mAddDeploymentUsecase;

    private Transcript transcript;

    private TestSuccessfulSubscriber testSuccessfulSubscriber = new TestSuccessfulSubscriber();

    @Before
    public void setUp() throws Exception {
        TestDeploymentComponent appComponent
                = DaggerTestDeploymentComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .build();
        appComponent.inject(this);
        transcript = new Transcript();
        ShadowApplication.runBackgroundTasks();
        Robolectric.getBackgroundThreadScheduler().pause();
        Robolectric.getForegroundThreadScheduler().pause();
    }

    @Test
    public void shouldSuccessfullyAddDeployment() {
        assertThat(mAddDeploymentUsecase).isNotNull();
        mAddDeploymentUsecase.setDeployment(new Deployment());
        ShadowApplication.runBackgroundTasks();
        mAddDeploymentUsecase.execute(testSuccessfulSubscriber);
        ShadowLooper.runUiThreadTasks();
        transcript.assertEventsSoFar("onNext1", "onCompleted");
    }

    @Test
    public void shouldThrowRuntimeException() {
        assertThat(mAddDeploymentUsecase).isNotNull();
        mAddDeploymentUsecase.setDeployment(null);
        try {
            mAddDeploymentUsecase.execute(null);
            assert_().fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            assertThat(e).hasMessage("Deployment is null. You must call setDeployment(...)");
        }
    }

    @After
    public void destroy() {
        testSuccessfulSubscriber.unsubscribe();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((UshahidiApplication) RuntimeEnvironment.application).getApplicationComponent();
    }

    private class TestSuccessfulSubscriber extends DefaultSubscriber<Long> {

        @Override
        public void onCompleted() {
            transcript.add("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            transcript.add("onError");
        }

        @Override
        public void onNext(Long aLong) {
            transcript.add("onNext" + aLong);
        }
    }
}
