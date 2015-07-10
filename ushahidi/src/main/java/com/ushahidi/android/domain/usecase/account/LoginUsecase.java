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

package com.ushahidi.android.domain.usecase.account;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.UserAccount;
import com.ushahidi.android.domain.repository.UserAccountRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Usecase for logging in a user to their account
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LoginUsecase extends Usecase {

    private final UserAccountRepository mUserAccountRepository;

    private UserAccount mUserAccount;

    @Inject
    protected LoginUsecase(UserAccountRepository userAccountRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserAccountRepository = userAccountRepository;
    }

    public void setUserAccount(UserAccount userAccount) {
        mUserAccount = userAccount;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mUserAccount == null) {
            throw new RuntimeException("User account is null. You must call setUserAccount(...)");
        }
        return mUserAccountRepository.login(mUserAccount);
    }
}