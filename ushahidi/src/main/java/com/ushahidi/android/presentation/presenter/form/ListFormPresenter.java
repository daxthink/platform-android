package com.ushahidi.android.presentation.presenter.form;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.usecase.form.ListFormUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.FormModelDataMapper;
import com.ushahidi.android.presentation.view.form.ListFormView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListFormPresenter implements Presenter {

    private final FormModelDataMapper mFormModelDataMapper;

    private final ListFormUsecase mListFormUsecase;

    private final PrefsFactory mPrefsFactory;

    private ListFormView mListFormView;

    /**
     * Default constructor
     *
     * @param listFormUsecase     The list form use case
     * @param formModelDataMapper The form model data mapper
     * @param prefsFactory        The preference factory
     */
    @Inject
    public ListFormPresenter(@Named("formList") ListFormUsecase listFormUsecase,
            FormModelDataMapper formModelDataMapper,
            PrefsFactory prefsFactory) {
        mListFormUsecase = listFormUsecase;
        mFormModelDataMapper = formModelDataMapper;
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
        mListFormUsecase.unsubscribe();
    }

    public void setView(@NonNull ListFormView listFormView) {
        mListFormView = listFormView;
    }

    public void loadFormFromDb() {
        loadForm(From.DATABASE);
    }

    private void loadForm(From from) {
        mListFormUsecase.setListForm(mPrefsFactory.getActiveDeploymentId().get(), from);
        mListFormUsecase.execute(new DefaultSubscriber<List<Form>>() {

            @Override
            public void onStart() {
                mListFormView.hideRetry();
                mListFormView.showLoading();
            }

            @Override
            public void onCompleted() {
                mListFormView.hideLoading();
            }

            @Override
            public void onNext(List<Form> formList) {
                mListFormView.hideLoading();
                mListFormView.renderFormList(mFormModelDataMapper.map(formList));
            }

            @Override
            public void onError(Throwable e) {
                mListFormView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mListFormView.showRetry();
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mListFormView.getAppContext(), errorHandler.getException());
        mListFormView.showError(errorMessage);
    }
}
