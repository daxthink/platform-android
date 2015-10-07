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
import com.expandable.view.ExpandableView;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.post.AddPostComponent;
import com.ushahidi.android.presentation.model.FormAttributeModel;
import com.ushahidi.android.presentation.model.FormStageModel;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.model.TagModel;
import com.ushahidi.android.presentation.presenter.formattribute.ListFormAttributePresenter;
import com.ushahidi.android.presentation.presenter.formstage.ListFormStagePresenter;
import com.ushahidi.android.presentation.presenter.post.AddPostPresenter;
import com.ushahidi.android.presentation.presenter.tags.ListTagPresenter;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.formattribute.ListFormAttributeView;
import com.ushahidi.android.presentation.view.formstage.ListFormStageView;
import com.ushahidi.android.presentation.view.post.AddPostView;
import com.ushahidi.android.presentation.view.tags.ListTagsView;
import com.ushahidi.android.presentation.view.ui.form.Form;
import com.ushahidi.android.presentation.view.ui.navigation.Launcher;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import timber.log.Timber;

/**
 * Fragment for adding a new post
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostFragment extends BaseFragment implements AddPostView {

    private static final String ARGUMENT_KEY_FORM_ID
            = "com.ushahidi.android.ARGUMENT_KEY_FORM_ID";

    private static final String ARGUMENT_KEY_FORM_STAGE_ID
            = "com.ushahidi.android.ARGUMENT_KEY_FORM_STAGE_ID";

    @Bind(R.id.add_post_title)
    EditText title;

    @Bind(R.id.add_post_description)
    EditText description;

    @Bind(R.id.form_attributes)
    ViewGroup mFormAttributeViewGroup;

    @Bind(R.id.categories)
    ViewGroup mCategories;

    @Inject
    AddPostPresenter mAddPostPresenter;

    @Inject
    ListTagPresenter mListTagPresenter;

    @Inject
    ListFormAttributePresenter mListFormAttributePresenter;

    @Inject
    ListFormStagePresenter mListFormStagePresenter;

    @Inject
    Launcher mLauncher;

    private LinearLayout mCustomFormsContainer;

    private Long mFormId;

    private Long mFormStageId;

    private Form mForm;

    private List<FormAttributeModel> mFormAttributeModels;

    /**
     * Add Deployment  Fragment
     */
    public AddPostFragment() {
        super(R.layout.fragment_add_post, R.menu.add_deployment);
    }

    public static AddPostFragment newInstance(Long formId, Long formStageId) {
        AddPostFragment addPostFragment = new AddPostFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(ARGUMENT_KEY_FORM_ID, formId);
        arguments.putLong(ARGUMENT_KEY_FORM_STAGE_ID, formStageId);
        addPostFragment.setArguments(arguments);
        return addPostFragment;
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
    }

    @Override
    public void onPause() {
        super.onPause();
        mAddPostPresenter.pause();
        mListFormAttributePresenter.pause();
        mListFormStagePresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddPostPresenter.destroy();
        mListFormStagePresenter.destroy();
        mListFormAttributePresenter.destroy();
    }

    private void initialize() {
        getComponent(AddPostComponent.class).inject(this);
        mAddPostPresenter.setView(this);
        mFormId = getArguments().getLong(ARGUMENT_KEY_FORM_ID);
        mFormStageId = getArguments().getLong(ARGUMENT_KEY_FORM_STAGE_ID);
        initializeTagsView();
        initializeFormAttributeView();
    }

    private void initializeFormAttributeView() {
        mCustomFormsContainer = new LinearLayout(getAppContext());
        mCustomFormsContainer.setOrientation(LinearLayout.VERTICAL);
        mCustomFormsContainer.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        mForm = new Form(getActivity(), mCustomFormsContainer);
        mListFormAttributePresenter.setView(new ListFormAttributeView() {
            @Override
            public void renderFormAttribute(List<FormAttributeModel> formModel) {
                mFormAttributeModels = formModel;
                initListFormStageView();
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
                showSnackbar(getView(), s);
            }

            @Override
            public Context getAppContext() {
                return getActivity().getApplicationContext();
            }
        });
        mListFormAttributePresenter.getFormOnline(mFormId);
    }

    private void initListFormStageView() {
        mListFormStagePresenter.setView(new ListFormStageView() {
            @Override
            public void showError(String message) {

            }

            @Override
            public Context getAppContext() {
                return getContext();
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
            public void renderFormStage(List<FormStageModel> formModels) {
                if (!Utility.isCollectionEmpty(formModels)) {
                    mFormAttributeViewGroup.setVisibility(View.VISIBLE);
                    for (FormStageModel formStage : formModels) {
                        ExpandableView stage = new ExpandableView(getAppContext());
                        stage.fillData(0, formStage.getLabel(), true);
                        for (FormAttributeModel formAttributeModel : mFormAttributeModels) {
                            if (formStage._id == formAttributeModel.getFormStageId()) {
                                mForm.renderForm(formAttributeModel);
                            }
                        }
                        mForm.getContainer();
                        stage.addContentView(mCustomFormsContainer);
                        mFormAttributeViewGroup.addView(stage);
                    }
                }
            }
        });
        mListFormStagePresenter.getFormOnline(mFormId);
    }


    private void initializeTagsView() {
        mListTagPresenter.setView(new ListTagsView() {
            @Override
            public void renderTagList(List<TagModel> tagModels) {
                if (!Utility.isCollectionEmpty(tagModels)) {
                    mCategories.setVisibility(View.VISIBLE);
                    for (TagModel tag : tagModels) {
                        Timber.i("RenderTags", "Tag: " + tag.getTag());
                        CheckBox checkBox = new CheckBox(getAppContext());
                        int id = Resources.getSystem()
                                .getIdentifier("btn_check_holo_light", "drawable", "android");
                        checkBox.setButtonDrawable(id);
                        checkBox.setTag(tag._id);
                        checkBox.setText(tag.getTag());
                        checkBox.setTextColor(getResources().getColor(R.color.black_dark));
                        mCategories.addView(checkBox);
                    }
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
