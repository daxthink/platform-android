package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.BuildConfig;
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
 * Tests {@link GetDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class GetDeploymentUsecaseTest {

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DeploymentRepository mockDeploymentRepository;

    private GetDeploymentUsecase mGetDeploymentUsecase;

    private static final Long DUMMY_DEPLOYMENT_ID = 1l;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mGetDeploymentUsecase = new GetDeploymentUsecase(DUMMY_DEPLOYMENT_ID,
                mockDeploymentRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyDeleteDeployment() {
        mGetDeploymentUsecase.buildUseCaseObservable();
        verify(mockDeploymentRepository).getEntity(DUMMY_DEPLOYMENT_ID);

        verifyNoMoreInteractions(mockDeploymentRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }
}
