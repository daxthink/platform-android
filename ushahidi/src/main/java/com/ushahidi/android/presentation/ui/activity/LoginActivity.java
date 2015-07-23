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

package com.ushahidi.android.presentation.ui.activity;

import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.account.DaggerLoginComponent;
import com.ushahidi.android.presentation.di.components.account.LoginComponent;
import com.ushahidi.android.presentation.ui.fragment.LoginFragment;

import android.accounts.AccountAuthenticatorResponse;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Login activity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LoginActivity extends BaseAppActivity {

    private static final String LOGIN_FRAGMENT_TAG = "login_fragment";

    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;

    private LoginComponent mLoginComponent;

    /**
     * Default constructor
     */
    public LoginActivity() {
        super(R.layout.activity_login, 0);
    }

    /**
     * Provides {@link Intent} for launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        initFragment();
    }

    private void injector() {
        mLoginComponent = DaggerLoginComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /**
     * Finishes this activity
     */
    public void finish() {
        super.finish();
    }

    private void initFragment() {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentByTag(LOGIN_FRAGMENT_TAG);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
        }
        replaceFragment(R.id.fragment_container, loginFragment, LOGIN_FRAGMENT_TAG);
    }

    public LoginComponent getLoginComponent() {
        return mLoginComponent;
    }
}
