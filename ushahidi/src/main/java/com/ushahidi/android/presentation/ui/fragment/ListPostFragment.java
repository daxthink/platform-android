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

import com.addhen.android.raiburari.presentation.ui.fragment.BaseRecyclerViewFragment;
import com.addhen.android.raiburari.presentation.ui.listener.RecyclerViewItemTouchListenerAdapter;
import com.addhen.android.raiburari.presentation.ui.widget.BloatedRecyclerView;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.presenter.post.ListPostPresenter;
import com.ushahidi.android.presentation.ui.adapter.PostAdapter;
import com.ushahidi.android.presentation.ui.navigation.Launcher;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.post.ListPostView;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListPostFragment extends BaseRecyclerViewFragment<PostModel, PostAdapter> implements
        ListPostView, RecyclerViewItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    public ListPostFragment() {
        super(PostAdapter.class, R.layout.fragment_list_post, R.menu.list_post);
    }

    @InjectView(R.id.post_fab)
    FloatingActionButton mPostFab;

    @InjectView(android.R.id.list)
    BloatedRecyclerView mPostRecyclerView;

    @Inject
    ListPostPresenter mListPostPresenter;

    @Inject
    Launcher mLauncher;

    private PostAdapter mPostAdapter;

    private static ListPostFragment mListPostFragment;

    public static ListPostFragment newInstance() {
        if (mListPostFragment == null) {
            mListPostFragment = new ListPostFragment();
        }
        return mListPostFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialize();
    }

    private void intialize() {
        mListPostPresenter.setView(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mPostAdapter = new PostAdapter();
        mPostRecyclerView.setFocusable(true);
        mPostRecyclerView.setFocusableInTouchMode(true);
        mPostRecyclerView.setAdapter(mPostAdapter);
        mPostRecyclerView.setHasFixedSize(true);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewItemTouchListenerAdapter recyclerViewItemTouchListenerAdapter =
                new RecyclerViewItemTouchListenerAdapter(mPostRecyclerView.recyclerView,
                        this);
        mPostRecyclerView.recyclerView.addOnItemTouchListener(recyclerViewItemTouchListenerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListPostPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mListPostPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListPostPresenter.destroy();
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
    public void renderPostList(List<PostModel> postModel) {
        if (!Utility.isCollectionEmpty(postModel)) {
            mPostAdapter.setItems(postModel);
        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int i) {
        // TODO: Launch post detail view
    }

    @Override
    public void onItemLongClick(RecyclerView recyclerView, View view, int i) {

    }

    @Override
    public void showError(String s) {
        Snackbar snackbar = Snackbar.make(getView(), R.string.retry,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, e -> mListPostPresenter.loadPostViaApi()
                );
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getAppContext().getResources().getColor(R.color.orange));
        snackbar.show();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }
}
