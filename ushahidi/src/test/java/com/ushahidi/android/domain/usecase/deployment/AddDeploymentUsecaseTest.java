package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Test case for {@link AddDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class AddDeploymentUsecaseTest {

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    @Mock
    private DeploymentRepository mMockDeploymentRepository;

    @Mock
    private Deployment mMockDeployment;

    private AddDeploymentUsecase mAddDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mAddDeploymentUsecase = new AddDeploymentUsecase(mMockDeploymentRepository,
                mMockThreadExecutor, mMockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyAddDeployment() {
        mAddDeploymentUsecase.setDeployment(mMockDeployment);
        mAddDeploymentUsecase.buildUseCaseObservable();
        verify(mMockDeploymentRepository).addEntity(mMockDeployment);

        verifyNoMoreInteractions(mMockDeploymentRepository);
        verifyZeroInteractions(mMockPostExecutionThread);
        verifyZeroInteractions(mMockThreadExecutor);
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
}
