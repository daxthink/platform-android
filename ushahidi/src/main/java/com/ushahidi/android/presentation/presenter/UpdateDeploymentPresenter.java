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

package com.ushahidi.android.presentation.presenter;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.domain.usecase.deployment.UpdateDeploymentUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.android.presentation.view.UpdateDeploymentView;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UpdateDeploymentPresenter implements Presenter {

    private final UpdateDeploymentUsecase mUpdateDeploymentUsecase;

    private final DeploymentModelDataMapper mDeploymentModelDataMapper;

    private UpdateDeploymentView mUpdateDeploymentView;

    @Inject
    public UpdateDeploymentPresenter(
            @Named("categoryUpdate") UpdateDeploymentUsecase updateDeploymentUsecase,
            DeploymentModelDataMapper deploymentModelDataMapper) {
        mUpdateDeploymentUsecase = updateDeploymentUsecase;
        mDeploymentModelDataMapper = deploymentModelDataMapper;
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
        mUpdateDeploymentUsecase.unsubscribe();
    }

    public void setView(@NonNull UpdateDeploymentView addDeploymentView) {
        mUpdateDeploymentView = addDeploymentView;
    }

    public void updateDeployment(DeploymentModel deploymentModel) {
        mUpdateDeploymentView.hideRetry();
        mUpdateDeploymentView.showLoading();
        mUpdateDeploymentUsecase.setDeployment(mDeploymentModelDataMapper.map(deploymentModel));
        mUpdateDeploymentUsecase.execute(new DefaultSubscriber<Long>() {
            @Override
            public void onCompleted() {
                mUpdateDeploymentView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mUpdateDeploymentView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mUpdateDeploymentView.showRetry();
            }

            @Override
            public void onNext(Long row) {
                mUpdateDeploymentView.onDeploymentSuccessfullyUpdated(row);
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mUpdateDeploymentView.getAppContext(),
                errorHandler.getException());
        mUpdateDeploymentView.showError(errorMessage);
    }
}