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
import com.addhen.android.raiburari.presentation.ui.listener.SwipeToDismissTouchListener;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.DeleteDeploymentComponent;
import com.ushahidi.android.presentation.di.components.deployment.ListDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.presenter.DeleteDeploymentPresenter;
import com.ushahidi.android.presentation.presenter.ListDeploymentPresenter;
import com.ushahidi.android.presentation.ui.activity.ListDeploymentActivity;
import com.ushahidi.android.presentation.ui.adapter.DeploymentAdapter;
import com.ushahidi.android.presentation.ui.navigation.Launcher;
import com.ushahidi.android.presentation.ui.view.DeleteDeploymentView;
import com.ushahidi.android.presentation.ui.view.ListDeploymentView;
import com.ushahidi.android.presentation.ui.widget.DeploymentRecyclerView;

import android.app.Activity;
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
 * Fragment for showing list of deployments
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListDeploymentFragment
        extends BaseRecyclerViewFragment<DeploymentModel, DeploymentAdapter>
        implements ListDeploymentView, DeleteDeploymentView,
        RecyclerViewItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    @InjectView(R.id.fab)
    FloatingActionButton mFab;

    @InjectView(android.R.id.empty)
    TextView mEmptyView;

    @InjectView(android.R.id.list)
    DeploymentRecyclerView mDeploymentRecyclerView;

    @Inject
    ListDeploymentPresenter mListDeploymentPresenter;

    @Inject
    DeleteDeploymentPresenter mDeleteDeploymentPresenter;

    @Inject
    Launcher mLauncher;

    // Manually creating the deployment adapter because
    // for some weirdness the super class cannot find the custom recyclerviewer
    // in the layout so the adapter is not created.
    private DeploymentAdapter mDeploymentAdapter;

    DeploymentListListener mDeploymentListListener;

    private boolean isDismissToDelete = false;

    private static ListDeploymentFragment mListDeploymentFragment;

    public ListDeploymentFragment() {
        super(DeploymentAdapter.class, R.layout.fragment_list_deployment, 0);
    }

    public static ListDeploymentFragment newInstance() {
        return new ListDeploymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DeploymentListListener) {
            mDeploymentListListener = (DeploymentListListener) activity;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        getDeleteDeploymentComponent(DeleteDeploymentComponent.class).inject(this);
        getComponent(ListDeploymentComponent.class).inject(this);
        mListDeploymentPresenter.setView(this);
        mDeleteDeploymentPresenter.setView(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mDeploymentAdapter = new DeploymentAdapter();
        mDeploymentRecyclerView.setDeleteDeploymentPresenter(mDeleteDeploymentPresenter);
        if (mFab != null) {
            setViewGone(mFab, false);
            mFab.setOnClickListener(v -> mLauncher.launchAddDeployment());
        }
        mDeploymentRecyclerView.setFocusable(true);
        mDeploymentRecyclerView.setFocusableInTouchMode(true);
        mDeploymentRecyclerView.setAdapter(mDeploymentAdapter);
        mDeploymentRecyclerView.setHasFixedSize(true);
        mDeploymentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDeploymentAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setEmptyView();
            }
        });

        RecyclerViewItemTouchListenerAdapter recyclerViewItemTouchListenerAdapter =
                new RecyclerViewItemTouchListenerAdapter(mDeploymentRecyclerView.recyclerView,
                        this);
        mDeploymentRecyclerView.recyclerView
                .addOnItemTouchListener(recyclerViewItemTouchListenerAdapter);
        swipeToDeleteUndo();
        setEmptyView();
    }

    private void setEmptyView() {
        if (mDeploymentAdapter != null && mDeploymentAdapter.getItemCount() == 0) {
            setViewGone(mEmptyView, false);
        } else {
            setViewGone(mEmptyView);
        }
    }

    private void swipeToDeleteUndo() {
        mDeploymentRecyclerView.initAdapter(mDeploymentAdapter);
        mDeploymentRecyclerView
                .setSwipeToDismissCallback(new SwipeToDismissTouchListener.DismissCallbacks() {
                    @Override
                    public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
                        return SwipeToDismissTouchListener.SwipeDirection.BOTH;
                    }

                    @Override
                    public void onDismiss(RecyclerView view,
                            List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {

                        for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                            mDeploymentRecyclerView.mPendingDeletedDeployments.add(
                                    new DeploymentRecyclerView.PendingDeletedDeployment(
                                            data.position,
                                            mDeploymentAdapter.getItem(data.position)));
                            mDeploymentAdapter.removeItem(
                                    mDeploymentAdapter.getItem(data.position));
                        }
                        mDeploymentRecyclerView.deleteItems();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mListDeploymentPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mListDeploymentPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListDeploymentPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Nullify the deployment listener to avoid potential memory leaks
        mDeploymentListListener = null;
    }

    @Override
    public void renderDeploymentList(List<DeploymentModel> deploymentModel) {
        if (deploymentModel != null && mDeploymentAdapter != null) {
            mDeploymentAdapter.setItems(deploymentModel);
        }
    }

    @Override
    public void showLoading() {
        // Do nothing
    }

    @Override
    public void hideLoading() {
        // Do nothing
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
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(getView(), R.string.retry,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, e -> mListDeploymentPresenter.loadDeployments()
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

    @SuppressWarnings("unchecked")
    protected <C> C getDeleteDeploymentComponent(Class<C> componentType) {
        return componentType
                .cast(((ListDeploymentActivity) getActivity()).getDeleteDeploymentComponent());
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        if (mDeploymentAdapter.getItemCount() > 0) {
            DeploymentModel deploymentModel = mDeploymentAdapter.getItem(position);
            if (mDeploymentListListener != null) {
                mDeploymentListListener.onDeploymentClicked(deploymentModel);
            }
        }
    }

    @Override
    public void onItemLongClick(RecyclerView recyclerView, View view, int i) {
        // Do nothing
    }

    @Override
    public void onDeploymentDeleted() {
        if (!isDismissToDelete) {
            mListDeploymentPresenter.loadDeployments();
        }
    }

    /**
     * Listens for deployment list events
     */
    public interface DeploymentListListener {

        void onDeploymentClicked(final DeploymentModel deploymentModel);
    }

}
