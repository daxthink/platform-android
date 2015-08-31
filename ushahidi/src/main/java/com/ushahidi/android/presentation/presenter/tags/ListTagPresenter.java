package com.ushahidi.android.presentation.presenter.tags;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.Tag;
import com.ushahidi.android.domain.usecase.tag.ListTagUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.TagModelDataMapper;
import com.ushahidi.android.presentation.view.tags.ListTagsView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListTagPresenter implements Presenter {

    private final ListTagUsecase mListTagUsecase;

    private TagModelDataMapper mTagModelDataMapper;

    private final PrefsFactory mPrefsFactory;

    private ListTagsView mListTagView;

    /**
     * Default constructor
     *
     * @param listTagUsecase     The list tag use case
     * @param tagModelDataMapper The tag model data mapper
     * @param prefsFactory       The prefs factory
     */
    @Inject
    public ListTagPresenter(@Named("tagList") ListTagUsecase listTagUsecase,
            TagModelDataMapper tagModelDataMapper, PrefsFactory prefsFactory) {
        mListTagUsecase = listTagUsecase;
        mTagModelDataMapper = tagModelDataMapper;
        mPrefsFactory = prefsFactory;
    }

    public void setView(@NonNull ListTagsView listTagsView) {
        mListTagView = listTagsView;
    }

    public void loadTags() {
        mListTagView.hideRetry();
        mListTagView.showLoading();
        mListTagUsecase.setListTag(mPrefsFactory.getActiveDeploymentId().get(), From.DATABASE);
        mListTagUsecase.execute(new DefaultSubscriber<List<Tag>>() {
            @Override
            public void onStart() {
                mListTagView.hideRetry();
                mListTagView.showLoading();
            }

            @Override
            public void onCompleted() {
                mListTagView.hideLoading();
            }

            @Override
            public void onNext(List<Tag> tagList) {
                mListTagView.hideLoading();
                mListTagView.renderTagList(mTagModelDataMapper.map(tagList));
            }

            @Override
            public void onError(Throwable e) {
                mListTagView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mListTagView.showRetry();
            }
        });
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
        mListTagUsecase.unsubscribe();
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mListTagView.getAppContext(),
                errorHandler.getException());
        mListTagView.showError(errorMessage);
    }
}
