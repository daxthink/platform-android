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

import com.addhen.android.raiburari.presentation.ui.fragment.BaseRecyclerViewFragment;
import com.addhen.android.raiburari.presentation.ui.listener.RecyclerViewItemTouchListenerAdapter;
import com.addhen.android.raiburari.presentation.ui.widget.BloatedRecyclerView;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.post.SearchPostComponent;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.presenter.post.ListPostPresenter;
import com.ushahidi.android.presentation.presenter.post.SearchPostPresenter;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.post.ListPostView;
import com.ushahidi.android.presentation.view.post.SearchPostView;
import com.ushahidi.android.presentation.view.ui.activity.SearchPostActivity;
import com.ushahidi.android.presentation.view.ui.adapter.SearchPostAdapter;
import com.ushahidi.android.presentation.view.ui.navigation.Launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class SearchPostFragment extends BaseRecyclerViewFragment<PostModel, SearchPostAdapter>
        implements
        SearchPostView, ListPostView,
        RecyclerViewItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    private static SearchPostFragment mSearchPostFragment;

    @Bind(R.id.loading_progress_bar)
    ProgressBar mProgressBar;

    @Bind(android.R.id.list)
    BloatedRecyclerView mPostRecyclerView;

    @Bind(android.R.id.empty)
    TextView mEmptyView;

    @Inject
    SearchPostPresenter mSearchPostPresenter;

    @Inject
    ListPostPresenter mListPostPresenter;

    @Inject
    Launcher mLauncher;

    private SearchPostAdapter mSearchPostAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private List<PostModel> mPostModelList;

    public SearchPostFragment() {
        super(SearchPostAdapter.class, R.layout.fragment_search_post, 0);
    }

    public static SearchPostFragment newInstance() {
        if (mSearchPostFragment == null) {
            mSearchPostFragment = new SearchPostFragment();
        }
        return mSearchPostFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        initialize();
    }

    private void initialize() {
        getSearchPostComponent(SearchPostComponent.class).inject(this);
        mSearchPostPresenter.setView(this);
        mListPostPresenter.setView(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mSearchPostAdapter = new SearchPostAdapter(mEmptyView);
        mPostRecyclerView.setFocusable(true);
        mPostRecyclerView.setFocusableInTouchMode(true);
        mPostRecyclerView.setAdapter(mSearchPostAdapter);
        mPostRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mPostRecyclerView.addItemDividerDecoration(getActivity());
        mPostRecyclerView.setLayoutManager(mLinearLayoutManager);
        RecyclerViewItemTouchListenerAdapter recyclerViewItemTouchListenerAdapter
                = new RecyclerViewItemTouchListenerAdapter(mPostRecyclerView.recyclerView, this);
        mPostRecyclerView.recyclerView.addOnItemTouchListener(recyclerViewItemTouchListenerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchPostPresenter.resume();
        loadPosts();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSearchPostPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearchPostPresenter.destroy();
    }

    /**
     * Makes a post search query
     *
     * @param query The search term
     */
    public void search(String query) {
        mSearchPostPresenter.search(query);
    }

    public void loadPosts() {
        mListPostPresenter.loadLocalDatabase();
    }

    @Override
    public void showLoading() {
        mEmptyView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPostRecyclerView.setRefreshing(false);
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
    public void showSearchResult(List<PostModel> postModel) {
        setPostList(postModel);
    }

    private void setPostList(List<PostModel> postModel) {
        mPostModelList = postModel;
        mSearchPostAdapter.setItems(postModel);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int i) {
        if (!Utility.isCollectionEmpty(mPostModelList)) {
            final PostModel postModel = mPostModelList.get(i);
            mLauncher.launchDetailPost(postModel._id, postModel.getTitle());
        }
    }

    @Override
    public void onItemLongClick(RecyclerView recyclerView, View view, int i) {
        // Do nothing
    }

    @Override
    public void showError(String s) {
        Snackbar snackbar = Snackbar.make(getView(), s, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getAppContext().getResources().getColor(R.color.orange));
        snackbar.show();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    private <C> C getSearchPostComponent(Class<C> componentType) {
        return componentType.cast(((SearchPostActivity) getActivity()).getComponent());
    }

    @Override
    public void renderPostList(List<PostModel> postModel) {
        setPostList(postModel);
    }
}
