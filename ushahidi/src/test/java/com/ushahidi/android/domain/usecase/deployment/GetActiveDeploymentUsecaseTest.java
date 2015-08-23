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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Tests {@link GetActiveDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class GetActiveDeploymentUsecaseTest {

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DeploymentRepository mockDeploymentRepository;

    private GetActiveDeploymentUsecase mGetActiveDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mGetActiveDeploymentUsecase = new GetActiveDeploymentUsecase(
                mockDeploymentRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyDeleteDeployment() {
        mGetActiveDeploymentUsecase.buildUseCaseObservable();
        verify(mockDeploymentRepository).getByStatus(Deployment.Status.ACTIVATED);

        verifyNoMoreInteractions(mockDeploymentRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }
}
