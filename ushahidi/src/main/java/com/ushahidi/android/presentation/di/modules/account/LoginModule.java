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

package com.ushahidi.android.presentation.di.modules.account;

import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.account.LoginUsecase;
import com.ushahidi.android.domain.usecase.deployment.ListDeploymentUsecase;
import com.ushahidi.android.domain.usecase.user.FetchUserProfileUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger modules that provides login related objects
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class LoginModule {

    /**
     * Provides {@link LoginUsecase}  with annotated name "accountLogin"
     *
     * @param loginUsecase The login usecase
     * @return The login use case
     */
    @Provides
    @ActivityScope
    @Named("accountLogin")
    LoginUsecase providesLoginUseCase(LoginUsecase loginUsecase) {
        return loginUsecase;
    }

    /**
     * Provides {@link FetchUserProfileUsecase} with annotated name "userprofileFetch"
     *
     * @param fetchUserProfileUsecase The fetch user profile use case
     * @return The fetch user profile
     */
    @Provides
    @ActivityScope
    @Named("userprofileFetch")
    FetchUserProfileUsecase providesFetchUserProfileUseCase(
            FetchUserProfileUsecase fetchUserProfileUsecase) {
        return fetchUserProfileUsecase;
    }

    /**
     * Provides {@link Usecase} with annotated name "deploymentList"
     *
     * @param listDeploymentUsecase The list deployment use case to be provided
     * @return The provided use case
     */
    @Provides
    @ActivityScope
    @Named("deploymentList")
    Usecase provideDeploymetListUseCase(ListDeploymentUsecase listDeploymentUsecase) {
        return listDeploymentUsecase;
    }
}
