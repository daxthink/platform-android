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

package com.ushahidi.android.presentation.view.ui.activity;

import com.ushahidi.android.R;
import com.ushahidi.android.data.api.account.PlatformSession;
import com.ushahidi.android.data.api.account.SessionManager;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.components.post.DaggerListPostComponent;
import com.ushahidi.android.presentation.di.components.post.DaggerMapPostComponent;
import com.ushahidi.android.presentation.di.components.post.DaggerPostComponent;
import com.ushahidi.android.presentation.di.components.post.DaggerUserProfileComponent;
import com.ushahidi.android.presentation.di.components.post.ListPostComponent;
import com.ushahidi.android.presentation.di.components.post.MapPostComponent;
import com.ushahidi.android.presentation.di.components.post.PostComponent;
import com.ushahidi.android.presentation.di.components.post.UserProfileComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.model.UserProfileModel;
import com.ushahidi.android.presentation.presenter.post.PostPresenter;
import com.ushahidi.android.presentation.state.LoadUserProfileEvent;
import com.ushahidi.android.presentation.state.ReloadPostEvent;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.post.PostView;
import com.ushahidi.android.presentation.view.ui.fragment.ListPostFragment;
import com.ushahidi.android.presentation.view.ui.fragment.MapPostFragment;
import com.ushahidi.android.presentation.view.ui.fragment.UserProfileFragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostActivity extends BaseAppActivity implements PostView {

    private static final String STATE_PARAM_SELECTED_TAB
            = "com.ushahidi.android.presentation.view.ui.activity.SELECTED_TAB";

    private static final String BUNDLE_STATE_PARAM_CURRENT_MENU
            = "com.ushahidi.android.presentation.view.ui.activity.BUNDLE_STATE_PARAM_CURRENT_MENU";

    private static final int DEPLOYMENTS_MENU_ITEMS_GROUP_ID = 1;

    private static final int MISC_MENU_ITEMS = 2;

    // Using negative values for the static nav drawer menu items because
    // we want them to be unique from the dynamic menus
    private static final int MANAGE_DEPLOYMENT_MENU_ID = -1;

    private static final int FEEDBACK_MENU_ID = -2;

    private static final int ABOUT_MENU_ID = -3;

    private static final int START_DELAY_ANIM = 300;

    private static final int DURATION_ANIM = 400;

    private static int mCurrentItem;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind((R.id.main_navigation_drawer))
    NavigationView mNavigationView;

    @Bind(R.id.post_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.post_fab)
    FloatingActionButton mFab;

    @Bind(R.id.post_tabs)
    TabLayout mTabLayout;

    PostPresenter mPostPresenter;

    UshAccessTokenManager mUshAccessTokenManager;

    SessionManager<PlatformSession> mSessionManager;

    private ListPostComponent mListPostComponent;

    private MapPostComponent mMapPostComponent;

    private UserProfileComponent mUserProfileComponent;

    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentMenu;

    private List<DeploymentModel> mDeploymentModelList;

    private boolean mPendingIntroAnimation;

    /**
     * Default constructor
     */
    public PostActivity() {
        super(R.layout.activity_post, R.menu.main);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        initViews();
        if (savedInstanceState != null) {
            mCurrentItem = savedInstanceState.getInt(STATE_PARAM_SELECTED_TAB);
            mCurrentMenu = savedInstanceState
                    .getInt(BUNDLE_STATE_PARAM_CURRENT_MENU, 0);
        } else {
            mPendingIntroAnimation = true;
        }
        UserProfileFragment userProfileFragment = (UserProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.user_profile_fragment);
        if (userProfileFragment != null) {
            userProfileFragment.setDrawerLayout(mDrawerLayout);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_PARAM_SELECTED_TAB, mCurrentItem);
        savedInstanceState.putInt(BUNDLE_STATE_PARAM_CURRENT_MENU, mCurrentMenu);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPostPresenter.resume();
        showLoginUserProfile();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurrentItem = mViewPager.getCurrentItem();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPostPresenter.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (mPendingIntroAnimation) {
            mPendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_post_search:
                mListPostComponent.launcher().launchSearchPost();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name,
                R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (mNavigationView != null) {
            setNavigationViewMenuItems(mNavigationView.getMenu());
            setupDrawerContent(mNavigationView);
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void injector() {
        PostComponent postComponent = DaggerPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();

        mListPostComponent = DaggerListPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();

        mMapPostComponent = DaggerMapPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();

        mUserProfileComponent = DaggerUserProfileComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
        mUshAccessTokenManager = getAppComponent().ushahidiTokenManager();
        mSessionManager = getAppComponent().platformSessionManager();
        mPostPresenter = postComponent.postPresenter();
        mPostPresenter.setPostView(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), viewPager);
        adapter.addFragment(ListPostFragment.newInstance(), getString(R.string.list));
        adapter.addFragment(MapPostFragment.newInstance(), getString(R.string.map));
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        // All static menu items have their id set to a negative value.
                        // We use that to determine which activity or fragment to launch.
                        case MANAGE_DEPLOYMENT_MENU_ID:
                            getListPostComponent().launcher().launchListDeployment();
                            break;
                        case FEEDBACK_MENU_ID:
                            getListPostComponent().launcher().launchFeedback();
                            break;
                        case ABOUT_MENU_ID:
                            getListPostComponent().launcher().launchAbout();
                            break;
                        default:
                            if (menuItem.getGroupId() == DEPLOYMENTS_MENU_ITEMS_GROUP_ID) {
                                mCurrentMenu = menuItem.getItemId();
                                // Mark the deployment active if it's not
                                if (mDeploymentModelList.get(menuItem.getItemId()).getStatus()
                                        == DeploymentModel.Status.DEACTIVATED) {
                                    mPostPresenter.activateDeployment(
                                            mDeploymentModelList.get(menuItem.getItemId()));
                                }
                            }

                    }
                    menuItem.setChecked(true);
                    mToolbar.setTitle(menuItem.getTitle());
                    mDrawerLayout.closeDrawers();
                    return true;
                });
    }

    private void setNavigationViewMenuItems(@NonNull Menu menu) {
        // Reset menu items to avoid duplicates because we reinitialize them
        menu.clear();
        if (!Utility.isCollectionEmpty(mDeploymentModelList)) {
            SubMenu subMenu = menu
                    .addSubMenu(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.deployments);
            // Use item position as the menu item's id that way we can retrieve the individual
            // deployment when user clicks on it to make it the active deployment
            for (int pos = 0; pos < mDeploymentModelList.size(); pos++) {
                subMenu.add(DEPLOYMENTS_MENU_ITEMS_GROUP_ID, pos, pos,
                        mDeploymentModelList.get(pos).getTitle())
                        .setIcon(R.drawable.ic_action_globe);
                if (mDeploymentModelList.get(pos).getStatus() == DeploymentModel.Status.ACTIVATED) {
                    subMenu.getItem().setChecked(true);
                } else {
                    subMenu.getItem().setChecked(false);
                }
            }
            subMenu.setGroupCheckable(DEPLOYMENTS_MENU_ITEMS_GROUP_ID, true, true);
        }

        SubMenu subMenuMisc = menu
                .addSubMenu(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.actions);

        subMenuMisc.add(MISC_MENU_ITEMS, MANAGE_DEPLOYMENT_MENU_ID, 1, R.string.manage_deployments)
                .setIcon(R.drawable.ic_action_map);

        subMenuMisc.add(MISC_MENU_ITEMS, FEEDBACK_MENU_ID, 2, R.string.send_feedback)
                .setIcon(R.drawable.ic_action_info);
        subMenuMisc.add(MISC_MENU_ITEMS, ABOUT_MENU_ID, 3, R.string.about)
                .setIcon(R.drawable.ic_action_info);
        subMenuMisc.setGroupCheckable(MISC_MENU_ITEMS, true, true);

        // Work around to get the menus items to show
        // TODO: Remove the code snippet below when there is an official fix for it
        // https://code.google.com/p/android/issues/detail?id=176300
        menu.add(0, 99, 0, "gone");
        menu.removeItem(99);

    }

    /**
     * Gets the list post component
     *
     * @return The list component
     */
    public ListPostComponent getListPostComponent() {
        return mListPostComponent;
    }

    /**
     * Gets the map post component
     *
     * @return The map component
     */
    public MapPostComponent getMapPostComponent() {
        return mMapPostComponent;
    }

    /**
     * User profile component
     *
     * @return The user profile component
     */
    public UserProfileComponent getUserProfileComponent() {
        return mUserProfileComponent;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void startIntroAnimation() {
        mFab.setTranslationY(2 * mFab.getHeight());
        mToolbar.setTranslationY(-mToolbar.getHeight());
        mToolbar.animate()
                .translationY(0)
                .setDuration(DURATION_ANIM)
                .setStartDelay(START_DELAY_ANIM)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }

    private void startContentAnimation() {
        mFab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(START_DELAY_ANIM)
                .setDuration(DURATION_ANIM)
                .start();
        // Cause the post list to reload
        UshahidiApplication.getRxEventBusInstance().send(new ReloadPostEvent(null));
    }

    private void showLoginUserProfile() {
        mUshAccessTokenManager.getStorage().hasAccessToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loggedIn -> {
                    if (loggedIn) {
                        Long profileId = mSessionManager.getActiveSession().getId();
                        mPostPresenter.getUserProfile(profileId);
                    } else {
                        UshahidiApplication.getRxEventBusInstance()
                                .send(new LoadUserProfileEvent(null));
                    }
                }, x -> x.printStackTrace());
    }

    /**
     * Handles FAB clicks
     *
     * @param view The FAB view
     */
    @OnClick(R.id.post_fab)
    void onFabClick(View view) {
        Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null)
                .show();
    }


    @Override
    public void setActiveUserProfile(UserProfileModel userProfile) {
        UshahidiApplication.getRxEventBusInstance().send(new LoadUserProfileEvent(userProfile));
    }

    @Override
    public void setActiveDeployment(DeploymentModel deployment) {
        UshahidiApplication.getRxEventBusInstance().send(new ReloadPostEvent(deployment));
    }

    @Override
    public void deploymentList(List<DeploymentModel> deploymentModels) {
        mDeploymentModelList = deploymentModels;
        if (Utility.isCollectionEmpty(deploymentModels)) {
            getListPostComponent().launcher().launchListDeployment();
        } else {
            setNavigationViewMenuItems(mNavigationView.getMenu());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    /**
     * An adapter that provides fragments for {@link ViewPager}
     */
    static class TabPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        private final List<String> mFragmentTitles = new ArrayList<>();

        private ViewPager mPager;

        /**
         * Default constructor
         *
         * @param fm        The fragment manager
         * @param viewPager The view pager
         */
        public TabPagerAdapter(FragmentManager fm, ViewPager viewPager) {
            super(fm);
            mPager = viewPager;
        }

        /**
         * Adds a fragment to the view pager
         *
         * @param fragment The fragment to be added
         * @param title    The title of the view pager
         */
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            notifyDataSetChanged();
            mPager.setCurrentItem(mCurrentItem);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
