package com.ushahidi.android.presentation.presenter;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.domain.usecase.deployment.DeleteDeploymentUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.view.DeleteDeploymentView;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteDeploymentPresenter implements Presenter {

    private final DeleteDeploymentUsecase mDeleteDeploymentUsecase;

    private DeleteDeploymentView mDeleteDeploymentView;

    @Inject
    public DeleteDeploymentPresenter(
            @Named("categoryDelete") DeleteDeploymentUsecase deleteDeploymentUsecase) {
        mDeleteDeploymentUsecase = deleteDeploymentUsecase;
    }

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void destroy() {
        mDeleteDeploymentUsecase.unsubscribe();
    }

    public void setView(@NonNull DeleteDeploymentView deleteDeploymentView) {
        mDeleteDeploymentView = deleteDeploymentView;
    }

    public void deleteDeployment(Long deploymentId) {
        mDeleteDeploymentUsecase.setDeploymentId(deploymentId);
        mDeleteDeploymentUsecase.execute(new DefaultSubscriber<Long>() {
            @Override
            public void onCompleted() {
                mDeleteDeploymentView.hideLoading();
                // TODO: Delete all saved items in the shared preferences
            }

            @Override
            public void onNext(Long row) {
                mDeleteDeploymentView.onDeploymentDeleted();
            }

            @Override
            public void onError(Throwable e) {
                mDeleteDeploymentView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
            }
        });

    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mDeleteDeploymentView.getAppContext(),
                errorHandler.getException());
        mDeleteDeploymentView.showError(errorMessage);
    }
}
