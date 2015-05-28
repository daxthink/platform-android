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
import com.addhen.android.raiburari.presentation.ui.widget.MovableFab;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.deployment.ListDeploymentComponent;
import com.ushahidi.android.presentation.model.DeploymentModel;
import com.ushahidi.android.presentation.presenter.DeleteDeploymentPresenter;
import com.ushahidi.android.presentation.presenter.ListDeploymentPresenter;
import com.ushahidi.android.presentation.ui.adapter.DeploymentAdapter;
import com.ushahidi.android.presentation.ui.view.DeleteDeploymentView;
import com.ushahidi.android.presentation.ui.view.ListDeploymentView;
import com.ushahidi.android.presentation.ui.widget.DeploymentRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
    MovableFab mFab;

    @InjectView(android.R.id.empty)
    TextView mEmptyView;

    @Inject
    ListDeploymentPresenter mListDeploymentPresenter;

    @Inject
    DeleteDeploymentPresenter mDeleteDeploymentPresenter;

    DeploymentListListener mDeploymentListListener;

    private DeploymentRecyclerView mDeploymentRecyclerView;

    private boolean isDismissToDelete = false;

    public ListDeploymentFragment() {
        super(DeploymentAdapter.class, R.layout.fragment_list_deployment, 0);
    }

    public static ListDeploymentFragment newInstance() {
        ListDeploymentFragment listDeploymentFragment = new ListDeploymentFragment();
        return listDeploymentFragment;
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
        getComponent(ListDeploymentComponent.class).inject(this);
        mListDeploymentPresenter.setView(this);
        mListDeploymentPresenter.setView(this);
        mDeploymentRecyclerView = (DeploymentRecyclerView) mBloatedRecyclerView;
        mDeploymentRecyclerView.setDeleteDeploymentPresenter(mDeleteDeploymentPresenter);
        if (mFab != null) {
            setViewGone(mFab, false);
            mFab.setOnClickListener(v -> {
                if (mDeploymentListListener != null) {
                    mDeploymentListListener.onFabClicked();
                }
            });
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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
        mDeploymentRecyclerView.setMovableFab(mFab);
        mDeploymentRecyclerView.setAdapter(mRecyclerViewAdapter);
        swipeToDeleteUndo();
        setEmptyView();
    }

    private void setEmptyView() {
        if (mRecyclerViewAdapter != null && mRecyclerViewAdapter.getItemCount() == 0) {
            setViewGone(mEmptyView, false);
        } else {
            setViewGone(mEmptyView);
        }
    }

    private void swipeToDeleteUndo() {
        mDeploymentRecyclerView.initAdapter(mRecyclerViewAdapter);

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
                                            data.position
                                            , mRecyclerViewAdapter.getItem(data.position)));
                            mRecyclerViewAdapter.removeItem(
                                    mRecyclerViewAdapter.getItem(data.position));
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
        if (deploymentModel != null && mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.setItems(deploymentModel);
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
    public void showError(String s) {
        showToast(s);
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        if (mRecyclerViewAdapter.getItemCount() > 0) {
            DeploymentModel deploymentModel = mRecyclerViewAdapter.getItem(position);
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

        void onFabClicked();
    }

}
