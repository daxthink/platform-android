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

package com.ushahidi.android.presentation.presenter.deployment;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.usecase.deployment.ListDeploymentUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.android.presentation.view.deployment.ListDeploymentView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListDeploymentPresenter implements
        Presenter {

    private final ListDeploymentUsecase mUsecase;

    private final DeploymentModelDataMapper mDeploymentModelDataMapper;

    private ListDeploymentView mListDeploymentView;

    /**
     * Default constructor
     *
     * @param usecase                   The list deployment use case
     * @param deploymentModelDataMapper The deployment model data mapper
     */
    @Inject
    public ListDeploymentPresenter(@Named("categoryList") ListDeploymentUsecase usecase,
            DeploymentModelDataMapper deploymentModelDataMapper) {
        mUsecase = usecase;
        mDeploymentModelDataMapper = deploymentModelDataMapper;
    }

    @Override
    public void resume() {
        loadDeployments();
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mUsecase.unsubscribe();
    }

    public void setView(@NonNull ListDeploymentView listDeploymentView) {
        mListDeploymentView = listDeploymentView;
    }

    /**
     * Gets deployment list from storage
     */
    public void loadDeployments() {
        mListDeploymentView.hideRetry();
        mListDeploymentView.showLoading();
        mUsecase.execute(new DefaultSubscriber<List<Deployment>>() {
            @Override
            public void onCompleted() {
                mListDeploymentView.hideLoading();
            }

            @Override
            public void onNext(List<Deployment> deploymentList) {
                mListDeploymentView.hideLoading();
                mListDeploymentView.renderDeploymentList(
                        mDeploymentModelDataMapper.map(deploymentList));
            }

            @Override
            public void onError(Throwable e) {
                mListDeploymentView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mListDeploymentView.showRetry();
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mListDeploymentView.getAppContext(),
                errorHandler.getException());
        mListDeploymentView.showError(errorMessage);
    }
}
