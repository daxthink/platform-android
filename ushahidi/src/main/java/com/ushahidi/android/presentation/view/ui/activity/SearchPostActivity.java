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

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.post.DaggerSearchPostComponent;
import com.ushahidi.android.presentation.di.components.post.SearchPostComponent;
import com.ushahidi.android.presentation.view.ui.fragment.SearchPostFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class SearchPostActivity extends BaseAppActivity
        implements HasComponent<SearchPostComponent> {

    private static final String FRAG_TAG = "search_post";

    private SearchPostFragment mSearchPostFragment;

    private SearchPostComponent mSearchPostComponent;

    private SearchView mSearchView = null;

    private String mQuery = null;

    /**
     * Default constructor
     */
    public SearchPostActivity() {
        super(R.layout.activity_search_post, R.menu.search_post);
    }

    /**
     * Provides {@link Intent} launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, SearchPostActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        mSearchPostFragment = (SearchPostFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG);
        if (mSearchPostFragment == null) {
            mSearchPostFragment = SearchPostFragment.newInstance();
            replaceFragment(R.id.search_post_fragment_container, mSearchPostFragment);
        }
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            if (mSearchView != null) {
                mSearchView.setQuery(mQuery, false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_post);
        menuItem.expandActionView();
        mSearchView = (SearchView) menuItem.getActionView();
        initSearchView();
        return true;
    }

    private void initSearchView() {
        final SearchManager searchManager = (SearchManager) getSystemService(
                Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();
                performQuery(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    performQuery(s);
                } else {
                    mSearchPostFragment.loadPosts();
                }
                return true;
            }
        });

        //mSearchView.setOnCloseListener(() -> false);

        if (!TextUtils.isEmpty(mQuery)) {
            mSearchView.setQuery(mQuery, false);
        }
        SearchView.SearchAutoComplete searchAutoComplete
                = (SearchView.SearchAutoComplete) mSearchView
                .findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
        searchAutoComplete.setTextSize(14);
    }

    private void injector() {
        mSearchPostComponent = DaggerSearchPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void performQuery(String query) {
        mSearchPostFragment.search(query);
    }

    @Override
    public SearchPostComponent getComponent() {
        return mSearchPostComponent;
    }
}