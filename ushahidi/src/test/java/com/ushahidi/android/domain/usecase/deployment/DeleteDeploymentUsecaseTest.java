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

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Test case for {@link DeleteDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DeleteDeploymentUsecaseTest {

    private static final Long DUMMY_DEPLOYMENT_ID = 1l;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DeploymentRepository mockDeploymentRepository;

    private DeleteDeploymentUsecase mDeleteDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDeleteDeploymentUsecase = new DeleteDeploymentUsecase(mockDeploymentRepository,
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyDeleteDeployment() {
        mDeleteDeploymentUsecase.setDeploymentId(DUMMY_DEPLOYMENT_ID);
        mDeleteDeploymentUsecase.buildUseCaseObservable();
        verify(mockDeploymentRepository).deleteEntity(DUMMY_DEPLOYMENT_ID);

        verifyNoMoreInteractions(mockDeploymentRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void shouldThrowRuntimeException() {
        assertThat(mDeleteDeploymentUsecase).isNotNull();
        mDeleteDeploymentUsecase.setDeploymentId(null);
        try {
            mDeleteDeploymentUsecase.execute(null);
            assert_().fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            assertThat(e).hasMessage("Deployment ID is null. You must call setDeployment(...)");
        }
    }
}
