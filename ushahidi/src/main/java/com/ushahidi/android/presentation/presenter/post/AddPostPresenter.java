/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.presenter.post;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.domain.usecase.post.AddPostUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.model.mapper.PostModelDataMapper;
import com.ushahidi.android.presentation.view.post.AddPostView;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostPresenter implements Presenter {

    private final AddPostUsecase mAddPostUsecase;

    private final PostModelDataMapper mPostModelDataMapper;

    private AddPostView mAddPostView;

    /**
     * Default constructor
     *
     * @param addPostUsecase      The add Post use case
     * @param postModelDataMapper the Post model data mapper
     */
    @Inject
    public AddPostPresenter(@Named("postAdd") AddPostUsecase addPostUsecase,
            PostModelDataMapper postModelDataMapper) {
        mAddPostUsecase = addPostUsecase;
        mPostModelDataMapper = postModelDataMapper;
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
        mAddPostUsecase.unsubscribe();
    }

    public void setView(@NonNull AddPostView addPostView) {
        mAddPostView = addPostView;
    }

    /**
     * Posts a post model online
     *
     * @param postModel The Post model to be saved
     */
    public void addPost(PostModel postModel) {
        mAddPostView.hideRetry();
        mAddPostView.showLoading();
        mAddPostUsecase.setAddPost(mPostModelDataMapper.map(postModel));
        mAddPostUsecase.execute(new DefaultSubscriber<Long>() {
            @Override
            public void onCompleted() {
                mAddPostView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mAddPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mAddPostView.showRetry();
            }

            @Override
            public void onNext(Long row) {
                mAddPostView.onPostSuccessfullyAdded(row);
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mAddPostView.getAppContext(),
                errorHandler.getException());
        mAddPostView.showError(errorMessage);
    }
}