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

package com.ushahidi.android.presentation.di.modules.deployment;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.deployment.AddDeploymentUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Add deployment related Dagger DI modules
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class AddDeploymentModule {

    private Long mDeploymentId;

    /**
     * Default constructor
     */
    public AddDeploymentModule() {
    }

    /**
     * Constructs an {@link AddDeploymentModule} with an initialized deployment id
     *
     * @param deploymentId The deployment id to be initialized
     */
    public AddDeploymentModule(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    /**
     * Provides {@link AddDeploymentUsecase} object with annotated name "categoryAdd"
     *
     * @param addDeploymentUsecase The add deployment use case
     * @return The add deployment use case
     */
    @Provides
    @ActivityScope
    @Named("categoryAdd")
    AddDeploymentUsecase provideAddDeploymentUseCase(AddDeploymentUsecase addDeploymentUsecase) {
        return addDeploymentUsecase;
    }
}
