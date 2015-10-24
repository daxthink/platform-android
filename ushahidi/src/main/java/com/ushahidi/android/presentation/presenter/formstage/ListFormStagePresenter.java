package com.ushahidi.android.presentation.presenter.formstage;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.FormStage;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.usecase.formstages.ListFormStageUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.FormStageModelDataMapper;
import com.ushahidi.android.presentation.view.formstage.ListFormStageView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Get form presenter
 *
 * @author Henry Addo
 */
public class ListFormStagePresenter implements Presenter {

    private final FormStageModelDataMapper mFormStageModelDataMapper;

    private final PrefsFactory mPrefsFactory;

    private final ListFormStageUsecase mListFormStageUsecase;

    private ListFormStageView mListFormStageView;

    @Inject
    public ListFormStagePresenter(
            @Named("formStageList") ListFormStageUsecase listFormStageUsecase,
            FormStageModelDataMapper formStageModelDataMapper,
            PrefsFactory prefsFactory) {
        mListFormStageUsecase = listFormStageUsecase;
        mFormStageModelDataMapper = formStageModelDataMapper;
        mPrefsFactory = prefsFactory;
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
        mListFormStageUsecase.unsubscribe();
    }

    public void setView(@NonNull ListFormStageView getFormStageView) {
        mListFormStageView = getFormStageView;
    }

    public void getFormOnline(Long formId) {
        mListFormStageUsecase.setListFormStage(mPrefsFactory.getActiveDeploymentId().get(),
                formId, From.ONLINE);
        mListFormStageUsecase.execute(new GetFormStageSubscriber());
    }

    public void getFormDb(Long formId) {
        mListFormStageUsecase.setListFormStage(mPrefsFactory.getActiveDeploymentId().get(),
                formId, From.DATABASE);
        mListFormStageUsecase.execute(new GetFormStageSubscriber());
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mListFormStageView.getAppContext(),
                errorHandler.getException());
        mListFormStageView.showError(errorMessage);
    }

    private class GetFormStageSubscriber extends DefaultSubscriber<List<FormStage>> {

        @Override
        public void onStart() {
            mListFormStageView.showLoading();
        }

        @Override
        public void onCompleted() {
            mListFormStageView.hideLoading();
        }

        @Override
        public void onNext(List<FormStage> formStages) {
            mListFormStageView
                    .renderFormStage(mFormStageModelDataMapper.map(formStages));
        }

        @Override
        public void onError(Throwable e) {
            mListFormStageView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    }
}
