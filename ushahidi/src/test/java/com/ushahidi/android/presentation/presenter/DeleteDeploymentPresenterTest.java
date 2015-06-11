package com.ushahidi.android.presentation.presenter;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.domain.usecase.deployment.DeleteDeploymentUsecase;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.android.presentation.ui.view.DeleteDeploymentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Tests {@link DeleteDeploymentPresenter}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DeleteDeploymentPresenterTest {

    private DeleteDeploymentPresenter mDeleteDeploymentPresenter;

    @Mock
    private Context mMockContext;

    @Mock
    private DeleteDeploymentView mMockDeleteDeploymentView;

    @Mock
    private DeleteDeploymentUsecase mMockDeleteDeploymentUsecase;

    @Mock
    private DeploymentModelDataMapper mMockDeploymentDataMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mDeleteDeploymentPresenter = new DeleteDeploymentPresenter(mMockDeleteDeploymentUsecase);
        mDeleteDeploymentPresenter.setView(mMockDeleteDeploymentView);
    }

    @Test
    public void testDeploymentDeletion() {
        given(mMockDeleteDeploymentView.getAppContext()).willReturn(mMockContext);
        mDeleteDeploymentPresenter.deleteDeployment(1l);

        verify(mMockDeleteDeploymentUsecase).setDeploymentId(1l);
        verify(mMockDeleteDeploymentUsecase).execute(any(Subscriber.class));
    }
}
