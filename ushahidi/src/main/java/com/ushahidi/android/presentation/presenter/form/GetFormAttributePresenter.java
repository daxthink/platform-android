package com.ushahidi.android.presentation.presenter.form;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.FormAttribute;
import com.ushahidi.android.domain.usecase.formattribute.GetFormAttributeUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.FormAttributeModelDataMapper;
import com.ushahidi.android.presentation.view.form.GetFormAttributeView;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Get form presenter
 *
 * @author Henry Addo
 */
public class GetFormAttributePresenter implements Presenter {

    private final FormAttributeModelDataMapper mFormAttributeModelDataMapper;

    private final PrefsFactory mPrefsFactory;

    private final GetFormAttributeUsecase mGetFormAttributeUsecase;

    private GetFormAttributeView mGetFormAttributeView;

    @Inject
    public GetFormAttributePresenter(
            @Named("formAttributeGet") GetFormAttributeUsecase getGetFormAttributeUsecase,
            FormAttributeModelDataMapper formAttributeModelDataMapper,
            PrefsFactory prefsFactory) {
        mGetFormAttributeUsecase = getGetFormAttributeUsecase;
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
        mGetFormAttributeUsecase.unsubscribe();
    }

    public void setView(@NonNull GetFormAttributeView getFormAttributeView) {
        mGetFormAttributeView = getFormAttributeView;
    }

    public void getForm(Long formId) {
        mGetFormAttributeUsecase
                .setGetFormAttribute(mPrefsFactory.getActiveDeploymentId().get(), formId);
        mGetFormAttributeUsecase.execute(new GetFormAttributeSubscriber());
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mGetFormAttributeView.getAppContext(),
                errorHandler.getException());
        mGetFormAttributeView.showError(errorMessage);
    }

    private class GetFormAttributeSubscriber extends DefaultSubscriber<FormAttribute> {

        @Override
        public void onStart() {
            mGetFormAttributeView.showLoading();
        }

        @Override
        public void onCompleted() {
            mGetFormAttributeView.hideLoading();
        }

        @Override
        public void onNext(FormAttribute formAttribute) {
            mGetFormAttributeView
                    .renderFormAttribute(mFormAttributeModelDataMapper.map(formAttribute));
        }

        @Override
        public void onError(Throwable e) {
            mGetFormAttributeView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    }
}
