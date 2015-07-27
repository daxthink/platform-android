/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.presenter.post;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.usecase.post.SearchPostUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.PostModelDataMapper;
import com.ushahidi.android.presentation.view.post.SearchPostView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between {@link SearchPostView} and
 * {@link com.ushahidi.android.presentation.model.PostModel} of the presentation layer.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class SearchPostPresenter implements Presenter {

    private final PostModelDataMapper mPostModelDataMapper;

    private final SearchPostUsecase mSearchPostUsecase;

    private final PrefsFactory mPrefsFactory;

    private SearchPostView mSearchPostView;


    /**
     * Default constructor
     *
     * @param searchPostUsecase   The list post usecase
     * @param postModelDataMapper The post model data mapper
     * @param prefsFactory        The prefs factory
     */
    @Inject
    public SearchPostPresenter(@Named("postSearch") SearchPostUsecase searchPostUsecase,
            PostModelDataMapper postModelDataMapper, PrefsFactory prefsFactory) {
        mSearchPostUsecase = searchPostUsecase;
        mPostModelDataMapper = postModelDataMapper;
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
        mSearchPostUsecase.unsubscribe();
    }

    /**
     * Perform a search based  {@link com.ushahidi.android.presentation.model.PostModel} the query
     * passed to it
     *
     * @param query The search query
     */
    public void search(String query) {
        searchPost(query);
    }

    private void searchPost(String query) {

        mSearchPostUsecase.setSearchPost(mPrefsFactory.getActiveDeploymentId().get(), query);
        mSearchPostUsecase.execute(new DefaultSubscriber<List<Post>>() {

            @Override
            public void onStart() {
                mSearchPostView.hideRetry();
                mSearchPostView.showLoading();
            }

            @Override
            public void onCompleted() {
                mSearchPostView.hideLoading();
            }

            @Override
            public void onNext(List<Post> postList) {
                mSearchPostView.hideLoading();
                mSearchPostView.showSearchResult(mPostModelDataMapper.map(postList));
            }

            @Override
            public void onError(Throwable e) {
                mSearchPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mSearchPostView.showRetry();
            }
        });
    }

    public void setView(@NonNull SearchPostView searchPostView) {
        mSearchPostView = searchPostView;
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mSearchPostView.getAppContext(), errorHandler.getException());
        mSearchPostView.showError(errorMessage);
    }
}
