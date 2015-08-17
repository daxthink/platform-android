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
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.components.post.ListPostComponent;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.presenter.post.ListPostPresenter;
import com.ushahidi.android.presentation.state.NoAccessTokenEvent;
import com.ushahidi.android.presentation.state.ReloadPostEvent;
import com.ushahidi.android.presentation.state.RxEventBus;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.post.ListPostView;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;
import com.ushahidi.android.presentation.view.ui.adapter.PostAdapter;
import com.ushahidi.android.presentation.view.ui.navigation.Launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.subscriptions.CompositeSubscription;

import static rx.android.app.AppObservable.bindFragment;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListPostFragment extends BaseRecyclerViewFragment<PostModel, PostAdapter> implements
        ListPostView, RecyclerViewItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    private static ListPostFragment mListPostFragment;

    @Bind(R.id.loading_progress_bar)
    ProgressBar mProgressBar;

    @Bind(android.R.id.list)
    BloatedRecyclerView mPostRecyclerView;

    @Bind(android.R.id.empty)
    TextView mEmptyView;

    @Inject
    ListPostPresenter mListPostPresenter;

    @Inject
    Launcher mLauncher;

    RxEventBus mRxEventBus;

    private PostAdapter mPostAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private CompositeSubscription mSubscriptions;

    private List<PostModel> mPostModelList;

    public ListPostFragment() {
        super(PostAdapter.class, R.layout.fragment_list_post, R.menu.list_post);
    }

    public static ListPostFragment newInstance() {
        if (mListPostFragment == null) {
            mListPostFragment = new ListPostFragment();
        }
        return mListPostFragment;
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
        subscribeToRxEventBus();
    }

    private void subscribeToRxEventBus() {
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(bindFragment(this, mRxEventBus.toObservable())
                .subscribe(event -> {
                    if (event instanceof ReloadPostEvent) {
                        ReloadPostEvent reloadPostEvent
                                = (ReloadPostEvent) event;
                        if (reloadPostEvent != null) {
                            mListPostPresenter.loadLocalDatabase();
                        }
                    } else if (event instanceof NoAccessTokenEvent) {

                    }
                }));
    }

    private void initialize() {
        getListPostComponent(ListPostComponent.class).inject(this);
        mListPostPresenter.setView(this);
        initRecyclerView();
        mRxEventBus = UshahidiApplication.getRxEventBusInstance();
    }

    private void initRecyclerView() {
        mPostAdapter = new PostAdapter();
        mPostRecyclerView.setFocusable(true);
        mPostRecyclerView.setFocusableInTouchMode(true);
        mPostRecyclerView.setAdapter(mPostAdapter);
        mPostRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mPostRecyclerView.addItemDividerDecoration(getActivity());
        mPostRecyclerView.setLayoutManager(mLinearLayoutManager);
        RecyclerViewItemTouchListenerAdapter recyclerViewItemTouchListenerAdapter
                = new RecyclerViewItemTouchListenerAdapter(mPostRecyclerView.recyclerView, this);
        mPostRecyclerView.recyclerView.addOnItemTouchListener(recyclerViewItemTouchListenerAdapter);
        // Upon  successful refresh, disable swipe to refresh
        mPostRecyclerView
                .setDefaultOnRefreshListener(() -> {
                    mPostRecyclerView.setRefreshing(true);
                    mListPostPresenter.loadPostViaApi();
                    mPostRecyclerView.recyclerView.smoothScrollToPosition(0);
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mListPostPresenter.resume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSubscriptions.unsubscribe();
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
        mEmptyView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPostRecyclerView.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_sort_by_title) {
            sortByTitle();
            return true;
        } else if (id == R.id.menu_sort_by_date) {
            sortByDate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sortByDate() {
        if (mPostAdapter != null) {
            mPostAdapter.sortByDate();
        }
    }

    public void sortByTitle() {
        if (mPostAdapter != null) {
            mPostAdapter.sortByTitle();
        }
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
    public void renderPostList(List<PostModel> postModel) {
        if (!Utility.isCollectionEmpty(postModel)) {
            mPostModelList = postModel;
            mPostAdapter.setItems(postModel);
        }
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
        Snackbar snackbar = Snackbar.make(getView(), s, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, e -> mListPostPresenter.loadPostViaApi());
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getAppContext().getResources().getColor(R.color.orange));
        snackbar.show();
    }

    private void showLoginPrompt() {
        Snackbar snackbar = Snackbar
                .make(getView(), getString(R.string.not_logged_in), Snackbar.LENGTH_LONG)
                .setAction(R.string.login, e -> mLauncher.launchLogin());
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getAppContext().getResources().getColor(R.color.orange));
        snackbar.show();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    private <C> C getListPostComponent(Class<C> componentType) {
        return componentType.cast(((PostActivity) getActivity()).getListPostComponent());
    }
}
