package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Tests {@link UpdateDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListDeploymentUsecaseTest {

    @Mock
    private ThreadExecutor mMockThreadExecutor;

    @Mock
    private PostExecutionThread mMockPostExecutionThread;

    @Mock
    private DeploymentRepository mMockDeploymentRepository;

    @Mock
    private Deployment mMockDeployment;

    private ListDeploymentUsecase mListDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mListDeploymentUsecase = new ListDeploymentUsecase(mMockDeploymentRepository,
                mMockThreadExecutor, mMockPostExecutionThread);
    }

    @Test
    public void shouldSuccessfullyAddDeployment() {
        mListDeploymentUsecase.buildUseCaseObservable();
        verify(mMockDeploymentRepository).getEntities();

        verifyNoMoreInteractions(mMockDeploymentRepository);
        verifyZeroInteractions(mMockPostExecutionThread);
        verifyZeroInteractions(mMockThreadExecutor);
    }
}
