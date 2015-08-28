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

package com.ushahidi.android.presentation.view.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.squareup.picasso.Picasso;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.components.post.UserProfileComponent;
import com.ushahidi.android.presentation.model.UserProfileModel;
import com.ushahidi.android.presentation.state.LoadUserProfileEvent;
import com.ushahidi.android.presentation.state.RxEventBus;
import com.ushahidi.android.presentation.util.GravatarUtility;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;
import com.ushahidi.android.presentation.view.ui.navigation.Launcher;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static butterknife.ButterKnife.findById;
import static rx.android.app.AppObservable.bindSupportFragment;

/**
 * Fragment for showing logged User profile prompt user to login
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileFragment extends BaseFragment {

    @Inject
    Launcher mLauncher;

    RxEventBus mRxEventBus;

    // User profile
    private View mLoginLayout;

    private View mUserProfileLayout;

    // So we can close the nav drawer
    private DrawerLayout mDrawerLayout;

    private CompositeSubscription mSubscriptions;

    public UserProfileFragment() {
        super(R.layout.fragment_user_profile, 0);
    }

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginLayout = findById(view, R.id.layout_user_login);
        mUserProfileLayout = findById(view, R.id.layout_user_profile);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        mRxEventBus = UshahidiApplication.getRxEventBusInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        mSubscriptions = new CompositeSubscription();

        mSubscriptions
                .add(bindSupportFragment(this, mRxEventBus.toObservable())
                        .subscribe(event -> {
                            if (event instanceof LoadUserProfileEvent) {
                                LoadUserProfileEvent loadUserProfileEvent
                                        = (LoadUserProfileEvent) event;
                                if (loadUserProfileEvent.getUserProfileModel() != null) {
                                    showUserProfile(loadUserProfileEvent.getUserProfileModel());
                                } else {
                                    showLogin();
                                }
                            }
                        }));
    }

    @Override
    public void onStop() {
        super.onStop();
        mSubscriptions.unsubscribe();
    }

    private void initialize() {
        getUserProfileComponent(UserProfileComponent.class).inject(this);
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    private void showLogin() {

        if (mLoginLayout == null) {
            return;
        }
        mLoginLayout.setVisibility(VISIBLE);
        mUserProfileLayout.setVisibility(GONE);
        mLoginLayout.setOnClickListener(view -> {
            mLauncher.launchLogin();
            mDrawerLayout.closeDrawers();
        });
    }

    private void showUserProfile(UserProfileModel profile) {
        if (mUserProfileLayout == null) {
            return;
        }

        mLoginLayout.setVisibility(GONE);
        mUserProfileLayout.setVisibility(VISIBLE);

        AppCompatTextView
                usernameTextView = findById(mUserProfileLayout, R.id.user_username);
        AppCompatTextView roleTextView = findById(mUserProfileLayout, R.id.user_role);
        usernameTextView.setText(profile.getUsername());
        roleTextView.setText(profile.getRole().getValue());
        ImageView avatarImageView = findById(mUserProfileLayout, R.id.user_profile_image);

        if (profile.getEmail() != null) {
            Picasso.with(getActivity()).load(GravatarUtility.url(profile.getEmail()))
                    .into(avatarImageView);
        }
    }

    private <C> C getUserProfileComponent(Class<C> componentType) {
        return componentType.cast(((PostActivity) getActivity()).getUserProfileComponent());
    }

    @OnClick(R.id.layout_user_profile)
    void onUserProfileClick() {
        mDrawerLayout.closeDrawers();
    }

    @OnClick(R.id.layout_user_login)
    void onLoginClick() {
        mDrawerLayout.closeDrawers();
        mLauncher.launchLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
