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
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.entity.UserProfile;
import com.ushahidi.android.domain.usecase.deployment.ActivateDeploymentUsecase;
import com.ushahidi.android.domain.usecase.user.GetUserProfileUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.android.presentation.model.mapper.UserProfileModelDataMapper;
import com.ushahidi.android.presentation.view.post.PostView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between {@link PostView} and
 * {@link com.ushahidi.android.presentation.model.PostModel} of the presentation layer.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostPresenter implements Presenter {

    private final Usecase mListDeploymentUsecase;

    private final GetUserProfileUsecase mGetUserProfileUsecase;

    private final DeploymentModelDataMapper mDeploymentModelDataMapper;

    private final UserProfileModelDataMapper mUserProfileModelDataMapper;

    private final ActivateDeploymentUsecase mActivateDeploymentUsecase;

    private PostView mPostView;

    private PrefsFactory mPrefsFactory;

    /**
     * Default constructor
     *
     * @param listDeploymentUsecase      The list deployment use case
     * @param userProfileUsecase         The user profile usecase
     * @param activateDeploymentUsecase  The activate deployment use case
     * @param prefsFactory               The prefs factory
     * @param deploymentModelDataMapper  The deployment data mapper
     * @param userProfileModelDataMapper The user profile model data mapper
     */
    @Inject
    public PostPresenter(@Named("deploymentList") Usecase listDeploymentUsecase,
            @Named("userprofileGet") GetUserProfileUsecase userProfileUsecase,
            @Named("deploymentActivate") ActivateDeploymentUsecase activateDeploymentUsecase,
            PrefsFactory prefsFactory,
            DeploymentModelDataMapper deploymentModelDataMapper,
            UserProfileModelDataMapper userProfileModelDataMapper) {
        mListDeploymentUsecase = listDeploymentUsecase;
        mGetUserProfileUsecase = userProfileUsecase;
        mActivateDeploymentUsecase = activateDeploymentUsecase;
        mDeploymentModelDataMapper = deploymentModelDataMapper;
        mPrefsFactory = prefsFactory;
        mUserProfileModelDataMapper = userProfileModelDataMapper;
    }

    public void setPostView(@NonNull PostView postView) {
        mPostView = postView;
    }

    @Override
    public void resume() {
        getDeploymentList();
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void destroy() {
        mListDeploymentUsecase.unsubscribe();
    }

    private void getDeploymentList() {
        mListDeploymentUsecase.execute(new DefaultSubscriber<List<Deployment>>() {
            @Override
            public void onCompleted() {
                mPostView.hideLoading();
            }

            @Override
            public void onNext(List<Deployment> deploymentList) {
                mPostView.deploymentList(mDeploymentModelDataMapper.map(deploymentList));
            }

            @Override
            public void onError(Throwable e) {
                mPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
            }
        });
    }

    /**
     * Gets a user profile
     *
     * @param userProfileId The user profile to be used for fetching user's details
     */
    public void getUserProfile(Long userProfileId) {
        mGetUserProfileUsecase
                .setListUserProfile(mPrefsFactory.getActiveDeploymentId().get(), userProfileId);
        mGetUserProfileUsecase.execute(new DefaultSubscriber<UserProfile>() {
            @Override
            public void onCompleted() {
                mPostView.hideLoading();
            }

            @Override
            public void onNext(UserProfile profile) {
                mPostView.setActiveUserProfile(mUserProfileModelDataMapper.map(profile));
            }

            @Override
            public void onError(Throwable e) {
                mPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mPostView.getAppContext(), errorHandler.getException());
        mPostView.showError(errorMessage);
    }

    /**
     * Sets {@link DeploymentModel} status to Activate
     *
     * @param deploymentModels The model to activate it's status
     * @param position         The position of the deployment model to be deactivated
     */
    public void activateDeployment(List<DeploymentModel> deploymentModels, int position) {
        mActivateDeploymentUsecase
                .setDeployment(mDeploymentModelDataMapper.unmap(deploymentModels), position);
        mActivateDeploymentUsecase.execute(new DefaultSubscriber<Long>() {
            @Override
            public void onCompleted() {
                // Do nothing
            }

            @Override
            public void onError(Throwable e) {
                mPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mPostView.showRetry();
            }

            @Override
            public void onNext(Long row) {
                // Do nothing
                mPrefsFactory.getActiveDeploymentId().set(deploymentModels.get(position)._id);
                mPrefsFactory.getActiveDeploymentUrl().set(deploymentModels.get(position).getUrl());
                mPostView.setActiveDeployment(deploymentModels.get(position));
            }
        });
    }
}
