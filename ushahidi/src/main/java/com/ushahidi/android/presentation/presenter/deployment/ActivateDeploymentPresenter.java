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

package com.ushahidi.android.presentation.presenter.deployment;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.android.presentation.view.deployment.ActivateDeploymentView;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between {@link ActivateDeploymentView} and
 * {@link com.ushahidi.android.presentation.model.DeploymentModel} of the presentation layer.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
public class ActivateDeploymentPresenter implements Presenter {

    private final Usecase mActivateDeploymentUsecase;

    private final DeploymentModelDataMapper mDeploymentModelDataMapper;

    private ActivateDeploymentView mActivateDeploymentView;

    /**
     * Default constructor
     *
     * @param activateDeploymentUsecase The activate deployment use case
     * @param deploymentModelMapper     The deployment model mapper
     */
    @Inject
    public ActivateDeploymentPresenter(
            @Named("deploymentActivate") Usecase activateDeploymentUsecase,
            DeploymentModelDataMapper deploymentModelMapper) {
        mDeploymentModelDataMapper = deploymentModelMapper;
        mActivateDeploymentUsecase = activateDeploymentUsecase;
    }

    public void setView(@NonNull ActivateDeploymentView activateDeploymentView) {
        mActivateDeploymentView = activateDeploymentView;
    }

    @Override
    public void resume() {
        mActivateDeploymentUsecase.execute(new DefaultSubscriber<Deployment>() {
            @Override
            public void onCompleted() {
                mActivateDeploymentView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mActivateDeploymentView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mActivateDeploymentView.showRetry();
            }

            @Override
            public void onNext(Deployment deployment) {
                mActivateDeploymentView
                        .setActiveDeployment(mDeploymentModelDataMapper.map(deployment));
            }
        });
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void destroy() {
        mActivateDeploymentUsecase.unsubscribe();
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mActivateDeploymentView.getAppContext(),
                errorHandler.getException());
        mActivateDeploymentView.showError(errorMessage);
    }
}
