package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
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
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class FetchDeploymentUsecaseTest {

    private static final String DUMMY_DEPLOYMENT_URL = "http://ushahidi-platform-api-release.herokuapp.com/";

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DeploymentRepository mockDeploymentRepository;

    private FetchDeploymentUsecase mFetchDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mFetchDeploymentUsecase = new FetchDeploymentUsecase(mockDeploymentRepository, mockThreadExecutor, mockPostExecutionThread);
        mFetchDeploymentUsecase.setDeploymentUrl(DUMMY_DEPLOYMENT_URL);
    }

    @Test
    public void shouldSuccessfullyDeleteDeployment() {
        mFetchDeploymentUsecase.buildUseCaseObservable();
        verify(mockDeploymentRepository).getDeploymentEntity(DUMMY_DEPLOYMENT_URL);

        verifyNoMoreInteractions(mockDeploymentRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }
}
