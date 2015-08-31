/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.view.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.post.AddPostComponent;
import com.ushahidi.android.presentation.model.FormAttributeModel;
import com.ushahidi.android.presentation.model.FormModel;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.model.TagModel;
import com.ushahidi.android.presentation.presenter.formattribute.ListFormAttributePresenter;
import com.ushahidi.android.presentation.presenter.post.AddPostPresenter;
import com.ushahidi.android.presentation.presenter.tags.ListTagPresenter;
import com.ushahidi.android.presentation.view.formattribute.ListFormAttributeView;
import com.ushahidi.android.presentation.view.post.AddPostView;
import com.ushahidi.android.presentation.view.tags.ListTagsView;
import com.ushahidi.android.presentation.view.ui.form.Form;
import com.ushahidi.android.presentation.view.ui.navigation.Launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Fragment for adding a new post
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostFragment extends BaseFragment implements AddPostView {

    private static final String ARGUMENT_KEY_FORM_MODEL
            = "com.ushahidi.android.ARGUMENT_KEY_FORM_MODEL";

    private static AddPostFragment mAddPostFragment;

    @Bind(R.id.add_post_title)
    EditText title;

    @Bind(R.id.add_post_description)
    EditText description;

    @Bind(R.id.form_attributes)
    ViewGroup mFormAttributeViewGroup;

    @Bind(R.id.post_steps_container)
    ViewGroup mPostStepsContainer;

    @Bind(R.id.post_steps)
    LinearLayout mPostStepsView;

    @Inject
    AddPostPresenter mAddPostPresenter;

    @Inject
    ListTagPresenter mListTagPresenter;

    @Inject
    ListFormAttributePresenter mListFormAttributePresenter;

    @Inject
    Launcher mLauncher;

    private FormModel mFormModel;

    private Form mForm;

    /**
     * Add Deployment  Fragment
     */
    public AddPostFragment() {
        super(R.layout.fragment_add_post, R.menu.add_deployment);
    }

    public static AddPostFragment newInstance(FormModel formModel) {
        if (mAddPostFragment == null) {
            mAddPostFragment = new AddPostFragment();
        }

        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_KEY_FORM_MODEL, formModel);
        mAddPostFragment.setArguments(arguments);
        return mAddPostFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAddPostPresenter.resume();
        //mListFormAttributePresenter.getFormDb(mFormModel._id);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAddPostPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddPostPresenter.destroy();
    }

    private void initialize() {
        getComponent(AddPostComponent.class).inject(this);
        mAddPostPresenter.setView(this);
        mFormModel = getArguments().getParcelable(ARGUMENT_KEY_FORM_MODEL);
        intializeTagsView();
        initializeFormAttributeView();
    }

    private void initializeFormAttributeView() {
        mForm = new Form(getActivity(), mFormAttributeViewGroup);
        mListFormAttributePresenter.setView(new ListFormAttributeView() {
            @Override
            public void renderFormAttribute(List<FormAttributeModel> formModel) {
                // TODO: show custom form UI
                mForm.renderForm(formModel);
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
                showSnabackar(getView(), s);
            }

            @Override
            public Context getAppContext() {
                return getActivity().getApplicationContext();
            }
        });

        mListFormAttributePresenter.getFormOnline(mFormModel._id);
    }

    private void intializeTagsView() {
        mListTagPresenter.setView(new ListTagsView() {
            @Override
            public void renderTagList(List<TagModel> tagModel) {
                //todo Render tags
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
                // Do nothing
            }

            @Override
            public Context getAppContext() {
                return getActivity().getApplicationContext();
            }
        });
        mListTagPresenter.loadTags();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplication();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @OnClick(R.id.save_post)
    public void onClickValidate() {
        submit();
    }

    @OnEditorAction(R.id.add_post_description)
    boolean onEditorAction(TextView textView, int actionId) {
        if (textView == description) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    submit();
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private void submit() {
        title.setError(null);
        if (TextUtils.isEmpty(title.getText().toString())) {
            title.setError(getString(R.string.validation_message_no_deployment_title));
            return;
        }
        if (TextUtils.isEmpty(description.getText().toString())) {
            description.setError(getString(R.string.validation_message_invalid_url));
            return;
        }
        PostModel postModel = new PostModel();
        postModel.setTitle(title.getText().toString());
        postModel.setContent(description.getText().toString());
        mAddPostPresenter.addPost(postModel);
    }

    @OnClick(R.id.add_post_cancel)
    public void onClickCancel() {
        getActivity().finish();
    }


    public void setPost(@NonNull PostModel postModel) {
        title.setText(postModel.getTitle());
        description.setText(postModel.getContent());
    }

    @Override
    public void onPostSuccessfullyAdded(Long row) {
        getActivity().finish();
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
}
