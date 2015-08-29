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
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.usecase.form.ListFormUsecase;
import com.ushahidi.android.domain.usecase.post.ListPostUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.PostModelDataMapper;
import com.ushahidi.android.presentation.view.post.ListPostView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between {@link ListPostView} and
 * {@link com.ushahidi.android.presentation.model.PostModel} of the presentation layer.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListPostPresenter implements Presenter {

    private final PostModelDataMapper mPostModelDataMapper;

    private final ListPostUsecase mListPostUsecase;

    private final PrefsFactory mPrefsFactory;

    private ListPostView mListPostView;


    /**
     * Default constructor
     *
     * @param listPostUsecase     The list post usecase
     * @param postModelDataMapper The post model data mapper
     * @param prefsFactory        The prefs factory
     */
    @Inject
    public ListPostPresenter(ListPostUsecase listPostUsecase,
            PostModelDataMapper postModelDataMapper, PrefsFactory prefsFactory) {
        mListPostUsecase = listPostUsecase;
        mPostModelDataMapper = postModelDataMapper;
        mPrefsFactory = prefsFactory;
    }

    @Override
    public void resume() {
        loadLocalDatabase();
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void destroy() {
        mListPostUsecase.unsubscribe();
    }

    /**
     * Gets a list of {@link com.ushahidi.android.presentation.model.PostModel} from local storage
     */
    public void loadLocalDatabase() {
        loadPost(From.DATABASE);
    }

    /**
     * Gets a list of {@link com.ushahidi.android.presentation.model.PostModel} from online storage
     */
    public void loadPostViaApi() {
        loadPost(From.ONLINE);
    }

    private void loadPost(From from) {

        mListPostUsecase.setListPost(mPrefsFactory.getActiveDeploymentId().get(), from);
        mListPostUsecase.execute(new DefaultSubscriber<List<Post>>() {

            @Override
            public void onStart() {
                mListPostView.hideRetry();
                mListPostView.showLoading();
            }

            @Override
            public void onCompleted() {
                mListPostView.hideLoading();
            }

            @Override
            public void onNext(List<Post> postList) {
                mListPostView.hideLoading();
                mListPostView.renderPostList(mPostModelDataMapper.map(postList));
            }

            @Override
            public void onError(Throwable e) {
                mListPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mListPostView.showRetry();
            }
        });
    }

    public void setView(@NonNull ListPostView listPostView) {
        mListPostView = listPostView;
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mListPostView.getAppContext(), errorHandler.getException());
        mListPostView.showError(errorMessage);
    }
}
