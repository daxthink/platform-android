package com.ushahidi.android.presentation.presenter.formattribute;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.FormAttribute;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.usecase.formattribute.ListFormAttributeUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.FormAttributeModelDataMapper;
import com.ushahidi.android.presentation.view.formattribute.ListFormAttributeView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Get form presenter
 *
 * @author Henry Addo
 */
public class ListFormAttributePresenter implements Presenter {

    private final FormAttributeModelDataMapper mFormAttributeModelDataMapper;

    private final PrefsFactory mPrefsFactory;

    private final ListFormAttributeUsecase mListFormAttributeUsecase;

    private ListFormAttributeView mListFormAttributeView;

    @Inject
    public ListFormAttributePresenter(
            @Named("formAttributeList") ListFormAttributeUsecase listFormAttributeUsecase,
            FormAttributeModelDataMapper formAttributeModelDataMapper,
            PrefsFactory prefsFactory) {
        mListFormAttributeUsecase = listFormAttributeUsecase;
        mFormAttributeModelDataMapper = formAttributeModelDataMapper;
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
        mListFormAttributeUsecase.unsubscribe();
    }

    public void setView(@NonNull ListFormAttributeView getFormAttributeView) {
        mListFormAttributeView = getFormAttributeView;
    }

    public void getFormOnline(Long formId) {
        mListFormAttributeUsecase.setListFormAttribute(mPrefsFactory.getActiveDeploymentId().get(),
                formId, From.ONLINE);
        mListFormAttributeUsecase.execute(new GetFormAttributeSubscriber());
    }

    public void getFormDb(Long formId) {
        mListFormAttributeUsecase.setListFormAttribute(mPrefsFactory.getActiveDeploymentId().get(),
                formId, From.DATABASE);
        mListFormAttributeUsecase.execute(new GetFormAttributeSubscriber());
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mListFormAttributeView.getAppContext(),
                errorHandler.getException());
        mListFormAttributeView.showError(errorMessage);
    }

    private class GetFormAttributeSubscriber extends DefaultSubscriber<List<FormAttribute>> {

        @Override
        public void onStart() {
            mListFormAttributeView.showLoading();
        }

        @Override
        public void onCompleted() {
            mListFormAttributeView.hideLoading();
        }

        @Override
        public void onNext(List<FormAttribute> formAttributes) {
            mListFormAttributeView
                    .renderFormAttribute(mFormAttributeModelDataMapper.map(formAttributes));
        }

        @Override
        public void onError(Throwable e) {
            mListFormAttributeView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    }
}
