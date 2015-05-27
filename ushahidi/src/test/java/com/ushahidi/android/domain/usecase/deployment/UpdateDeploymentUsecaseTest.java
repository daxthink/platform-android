package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Tests {@link UpdateDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UpdateDeploymentUsecaseTest {

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    @Mock
    private DeploymentRepository mMockDeploymentRepository;

    @Mock
    private Deployment mMockDeployment;

    private UpdateDeploymentUsecase mUpdateDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mUpdateDeploymentUsecase = new UpdateDeploymentUsecase(mMockDeploymentRepository,
                mMockThreadExecutor, mMockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyAddDeployment() {
        mUpdateDeploymentUsecase.setDeployment(mMockDeployment);
        mUpdateDeploymentUsecase.buildUseCaseObservable();
        verify(mMockDeploymentRepository).updateEntity(mMockDeployment);

        verifyNoMoreInteractions(mMockDeploymentRepository);
        verifyZeroInteractions(mMockPostExecutionThread);
        verifyZeroInteractions(mMockThreadExecutor);
    }

    @Test
    public void shouldThrowRuntimeException() {
        assertThat(mUpdateDeploymentUsecase).isNotNull();
        mUpdateDeploymentUsecase.setDeployment(null);
        try {
            mUpdateDeploymentUsecase.execute(null);
            assert_().fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            assertThat(e).hasMessage("Deployment is null. You must call setDeployment(...)");
        }
    }
}
