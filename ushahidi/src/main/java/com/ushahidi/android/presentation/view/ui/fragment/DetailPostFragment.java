package com.ushahidi.android.presentation.view.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.post.GetPostComponent;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.presenter.post.DetailPostPresenter;
import com.ushahidi.android.presentation.util.TagUtility;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.post.DetailPostView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Get post details fragment
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DetailPostFragment extends BaseFragment implements DetailPostView {

    private static final String ARGUMENT_KEY_POST_ID = "com.ushahidi.android.ARGUMENT_KEY_POST_ID";

    private static DetailPostFragment mGetDetailsFragment;

    @Inject
    DetailPostPresenter mDetailPostPresenter;

    @Bind(R.id.loading_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.detail_post_date)
    AppCompatTextView mDateTextView;

    @Bind(R.id.detail_post_content)
    AppCompatTextView mContentTextView;

    @Bind(R.id.detail_post_status)
    AppCompatTextView mStatusTextView;

    @Bind(R.id.post_tags)
    LinearLayout mTagLinearLayout;

    @Bind(R.id.detail_post_tags_container)
    ViewGroup mTagContainer;

    private Long mPostId;

    public DetailPostFragment() {
        super(R.layout.fragment_detail_post, 0);
    }

    public static DetailPostFragment newInstance(Long postId) {
        if (mGetDetailsFragment == null) {
            mGetDetailsFragment = new DetailPostFragment();
        }
        Bundle arguments = new Bundle();
        arguments.putLong(ARGUMENT_KEY_POST_ID, postId);
        mGetDetailsFragment.setArguments(arguments);
        return mGetDetailsFragment;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailPostPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDetailPostPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetailPostPresenter.destroy();
    }

    private void initialize() {
        getComponent(GetPostComponent.class).inject(this);
        mDetailPostPresenter.setView(this);
        mPostId = getArguments().getLong(ARGUMENT_KEY_POST_ID);
        mDetailPostPresenter.getPostDetails(mPostId);
    }

    @Override
    public void showPostModel(PostModel postModel) {
        if (postModel != null) {
            mContentTextView.setText(postModel.getContent());
            mDateTextView.setText(Utility.getRelativeTimeDisplay(postModel.getCreated()));
            mStatusTextView.setText(postModel.getStatus().name());
            // Render post tags
            if (!Utility.isCollectionEmpty(postModel.getTags())) {
                TagUtility.renderTagBade(getAppContext(), mTagContainer, mTagLinearLayout,
                        postModel.getTags());
            }
        }
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String s) {
        showSnabackar(getView(), s);
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }
}
