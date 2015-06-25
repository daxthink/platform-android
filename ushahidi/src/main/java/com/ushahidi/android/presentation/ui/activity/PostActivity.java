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
import com.ushahidi.android.presentation.ui.fragment.ListPostFragment;
import com.ushahidi.android.presentation.ui.fragment.MapPostFragment;

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
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostActivity extends BaseAppActivity {

    private static final String STATE_PARAM_SELECTED_TAB
            = "com.ushahidi.android.presentation.ui.activity.SELECTED_TAB";

    private static final int DEPLOYMENTS_MENU_ITEMS_ID = 1;

    private static final int MISC_MENU_ITEMS = 2;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView((R.id.main_navigation_drawer))
    NavigationView mNavigationView;

    @InjectView(R.id.post_viewpager)
    ViewPager mViewPager;

    @InjectView(R.id.post_fab)
    FloatingActionButton fab;

    @InjectView(R.id.post_tabs)
    TabLayout mTabLayout;

    private static int mCurrentItem;

    public PostActivity() {
        super(R.layout.activity_post, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            mCurrentItem = savedInstanceState.getInt(STATE_PARAM_SELECTED_TAB);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurrentItem = mViewPager.getCurrentItem();
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        if (mNavigationView != null) {
            setNavigationViewMenuItems(mNavigationView.getMenu());
            setupDrawerContent(mNavigationView);
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        fab.setOnClickListener(
                view -> Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_PARAM_SELECTED_TAB, mCurrentItem);
        super.onSaveInstanceState(outState);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), viewPager);
        adapter.addFragment(new ListPostFragment(), getString(R.string.list));
        adapter.addFragment(new MapPostFragment(), getString(R.string.map));
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                });
    }

    private void setNavigationViewMenuItems(@NonNull Menu menu) {
        // TODO: Use real deployment to populate the menu items for deployments
        SubMenu subMenu = menu
                .addSubMenu(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.deployments);
        subMenu.add(DEPLOYMENTS_MENU_ITEMS_ID, 1, 1, "Ebola watch doc")
                .setIcon(R.drawable.ic_action_globe);
        subMenu.add(DEPLOYMENTS_MENU_ITEMS_ID, 2, 2, "Catch him")
                .setIcon(R.drawable.ic_action_globe);
        subMenu.setGroupCheckable(DEPLOYMENTS_MENU_ITEMS_ID, true, true);

        SubMenu subMenuMisc = menu
                .addSubMenu(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.actions);
        subMenuMisc.add(MISC_MENU_ITEMS, 1, 1, R.string.manage_deployments)
                .setIcon(R.drawable.ic_action_map);
        subMenuMisc.add(MISC_MENU_ITEMS, 2, 2, R.string.send_feedback)
                .setIcon(R.drawable.ic_action_info);
        subMenuMisc.add(MISC_MENU_ITEMS, 2, 2, R.string.settings)
                .setIcon(R.drawable.ic_action_settings);
        subMenuMisc.setGroupCheckable(MISC_MENU_ITEMS, true, true);

        // Work around to get the menus items to show
        // TODO: Remove the code snippet below when there is an official fix for it
        menu.add(0, 99, 0, "gone");
        menu.removeItem(99);

    }

    static class TabPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        private final List<String> mFragmentTitles = new ArrayList<>();

        private ViewPager mPager;

        public TabPagerAdapter(FragmentManager fm, ViewPager viewPager) {
            super(fm);
            mPager = viewPager;
        }

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
