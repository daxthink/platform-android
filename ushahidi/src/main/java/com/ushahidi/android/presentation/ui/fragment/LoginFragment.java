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

package com.ushahidi.android.presentation.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.account.LoginComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.model.UserAccountModel;
import com.ushahidi.android.presentation.presenter.account.LoginPresenter;
import com.ushahidi.android.presentation.ui.activity.LoginActivity;
import com.ushahidi.android.presentation.ui.adapter.DeploymentSpinnerAdapter;
import com.ushahidi.android.presentation.view.account.LoginView;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LoginFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,
        LoginView {

    private static LoginFragment mLoginFragment;

    @Bind(R.id.login_username)
    EditText mUsername;

    @Bind(R.id.login_password)
    EditText mPassword;

    @Bind(R.id.login_submit_btn)
    Button mLoginButton;

    @Bind(R.id.select_deployment)
    Spinner mSpinner;

    @Bind(R.id.login_type)
    RadioGroup mTypeRadioGroup;

    @Bind(R.id.radio_btn_login)
    RadioButton mLoginRadioButton;

    @Bind(R.id.radio_btn_register)
    RadioButton mRegisterRadioButton;

    @Bind(R.id.active_email)
    AutoCompleteTextView mEmailAutoComplete;

    @Bind(R.id.login_progress_bar)
    ProgressBar mProgressBar;

    @Inject
    LoginPresenter mLoginPresenter;

    private DeploymentSpinnerAdapter mDeploymentSpinnerArrayAdapter;

    private Observable<OnTextChangeEvent> mUsernameText;

    private Observable<OnTextChangeEvent> mPasswordText;

    private Subscription mSubscribe;

    public LoginFragment() {
        super(R.layout.fragment_login, 0);
    }

    public static LoginFragment newInstance() {
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
        }
        return mLoginFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialize();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
        }
    }


    private void intialize() {
        getLoginComponent(LoginComponent.class).inject(this);
        mLoginPresenter.setView(this);
        initView();
    }

    private void initView() {
        mTypeRadioGroup.setOnCheckedChangeListener(this);
        mLoginRadioButton.setChecked(true);
        mEmailAutoComplete.setOnFocusChangeListener(
                (v, hasFocus) -> mEmailAutoComplete.showDropDown());
        mLoginPresenter.getDeploymentList();
        mRegisterRadioButton.setVisibility(View.GONE);
        mUsernameText = WidgetObservable.text(mUsername);
        mPasswordText = WidgetObservable.text(mPassword);
    }

    @OnClick(R.id.login_submit_btn)
    public void onSubmit() {
        submit();
    }

    private void submit() {
        mPassword.setError(null);
        // TODO: Implement user logins
        switch (mTypeRadioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_btn_login:
                UserAccountModel userAccountModel = new UserAccountModel();
                userAccountModel.setAccountName(mUsername.getText().toString().trim());
                userAccountModel.setPassword(mPassword.getText().toString().trim());
                mLoginPresenter.performLogin(userAccountModel);
                break;
            default:
                // do nothing
        }
    }

    /*private void validate() {
        mSubscribe = Observable.combineLatest(mUsernameText, mPasswordText,
                (Func2<OnTextChangeEvent, OnTextChangeEvent, Boolean>)
                (onTextChangeEvent, onTextChangeEvent2) -> false);
    }*/

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radio_btn_login:
                mLoginButton.setText(R.string.login);
                mEmailAutoComplete.setText(null);
                mEmailAutoComplete.setVisibility(View.GONE);
                // Set actionbar title to login
                getActivity().setTitle(getResources().getString(R.string.login));
                break;
            case R.id.radio_btn_register:
                mLoginButton.setText(R.string.register);
                mEmailAutoComplete.setVisibility(View.VISIBLE);

                // Set actionbar title to register
                getActivity().setTitle(getResources().getString(R.string.register));
                if (mEmailAutoComplete.getAdapter() == null) {
                    final Set<String> emailSet = new HashSet<>();
                    for (Account account : AccountManager.get(getActivity()).getAccounts()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                            emailSet.add(account.name);
                        }
                    }
                    List<String> emails = new ArrayList<>(emailSet);
                    mEmailAutoComplete.setAdapter(new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, emails));
                }
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void deploymentList(List<DeploymentModel> deploymentModels) {
        mDeploymentSpinnerArrayAdapter = new DeploymentSpinnerAdapter(getAppContext(),
                deploymentModels);
        mSpinner.setAdapter(mDeploymentSpinnerArrayAdapter);

        // By default, select the active deployment.
        // TODO: Get the active deployment and make it the selected value
        mSpinner.setSelection(0);
    }

    @Override
    public void loginCompleted() {
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        mLoginButton.setVisibility(View.GONE);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoginButton.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        // Do nothing
    }

    @Override
    public void hideRetry() {
        // Do nothing
    }

    @Override
    public void showError(String s) {
        showSnabackar(getView(), s);
    }

    @Override
    public Context getAppContext() {
        return getActivity();
    }

    private <C> C getLoginComponent(Class<C> componentType) {
        return componentType.cast(((LoginActivity) getActivity()).getLoginComponent());
    }
}
