/*
 * Copyright (c) 2014 Ushahidi. 
 *
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

package com.ushahidi.android.presenter;

import com.google.common.base.Preconditions;

import com.squareup.otto.Subscribe;
import com.ushahidi.android.R;
import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.core.entity.Post;
import com.ushahidi.android.core.entity.Tag;
import com.ushahidi.android.core.exception.ErrorWrap;
import com.ushahidi.android.core.repository.IGeoJsonRepository;
import com.ushahidi.android.core.repository.IPostRepository;
import com.ushahidi.android.core.repository.ITagRepository;
import com.ushahidi.android.core.usecase.Search;
import com.ushahidi.android.core.usecase.geojson.FetchGeoJson;
import com.ushahidi.android.core.usecase.post.FetchPost;
import com.ushahidi.android.core.usecase.post.ListPost;
import com.ushahidi.android.core.usecase.tag.FetchTag;
import com.ushahidi.android.data.api.service.GeoJsonService;
import com.ushahidi.android.data.api.service.PostService;
import com.ushahidi.android.data.api.service.TagService;
import com.ushahidi.android.data.repository.datasource.geojson.GeoJsonDataSourceFactory;
import com.ushahidi.android.data.repository.datasource.post.PostDataSourceFactory;
import com.ushahidi.android.data.repository.datasource.tag.TagDataSourceFactory;
import com.ushahidi.android.exception.ErrorMessageFactory;
import com.ushahidi.android.model.PostModel;
import com.ushahidi.android.model.mapper.PostModelDataMapper;
import com.ushahidi.android.state.IDeploymentState;
import com.ushahidi.android.ui.prefs.Prefs;
import com.ushahidi.android.ui.view.ILoadViewData;
import com.ushahidi.android.util.ApiServiceUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link com.ushahidi.android.presenter.IPresenter} facilitates interactions between
 * {@link com.ushahidi.android.ui.activity.PostActivity} and {@link PostModel}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
//TODO: Refactor how some of the depencecies are injected in a clean way
public class ListPostPresenter implements IPresenter {

    private final PostModelDataMapper mPostModelDataMapper;

    private final IPostRepository mPostRepository;

    private final ITagRepository mTagRepository;

    private final ListPost mListPost;

    private final FetchPost mFetchPost;

    private final FetchTag mFetchTag;

    private final Prefs mPrefs;

    private final Search<Post> mSearch;

    private final ApiServiceUtil mApiServiceUtil;

    private final IDeploymentState mDeploymentState;

    private final PostDataSourceFactory mPostDataSourceFactory;

    private final TagDataSourceFactory mTagDataSourceFactory;

    private final FetchGeoJson mFetchGeoJson;

    private final GeoJsonDataSourceFactory mGeoJsonDataSourceFactory;

    private final IGeoJsonRepository mGeoJsonRepository;

    private ListPost.Callback mListCallback = new ListPost.Callback() {

        @Override
        public void onPostListLoaded(List<Post> listPost) {
            showPostsListInView(listPost);
            hideViewLoading();
        }

        @Override
        public void onError(ErrorWrap error) {
            hideViewLoading();
            showErrorMessage(error);
        }
    };

    private FetchPost.Callback mCallback = new FetchPost.Callback() {

        @Override
        public void onPostFetched(List<Post> listPost) {
            showPostsListInView(listPost);
            //After a successful post fetch via the API, pull post with GeoJson
            setGeoJsonService(createGeoJsonService());
            mFetchGeoJson.execute(mPrefs.getActiveDeploymentId().get(), mFetchGeoJsonCallback);
        }

        @Override
        public void onError(ErrorWrap error) {
            hideViewLoading();
            showErrorMessage(error);
        }
    };

    private FetchTag.Callback mFetchTagCallback = new FetchTag.Callback() {

        @Override
        public void onTagFetched(List<Tag> listTag) {
            //After a successful tag fetch via the API, pull post
            setPostService(createPostService());
            mFetchPost.execute(mPrefs.getActiveDeploymentId().get(), mCallback);
        }

        @Override
        public void onError(ErrorWrap error) {
            hideViewLoading();
            showErrorMessage(error);
        }
    };

    private Search.Callback<Post> mSearchCallback = new Search.Callback<Post>() {

        @Override
        public void onError(ErrorWrap error) {
            mView.showEmptySearchResultsInfo();
            showErrorMessage(error);
        }

        @Override
        public void onEntityFound(List<Post> entityList) {
            if (entityList.size() > 0) {
                showPostsListInView(entityList);
            } else {
                mView.showEmptySearchResultsInfo();
            }
        }
    };

    private FetchGeoJson.Callback mFetchGeoJsonCallback = new FetchGeoJson.Callback() {

        @Override
        public void onGeoJsonFetched(GeoJson geoJson) {
            hideViewLoading();
        }

        @Override
        public void onError(ErrorWrap error) {
            hideViewLoading();
            showErrorMessage(error);
        }
    };

    private View mView;

    @Inject
    public ListPostPresenter(ListPost listPost,
            FetchTag fetchTag,
            Search<Post> search,
            FetchPost fetchPost,
            FetchGeoJson fetchGeoJson,
            PostModelDataMapper postModelDataMapper,
            IPostRepository postRepository,
            ITagRepository tagRepository,
            PostDataSourceFactory postDataSourceFactory,
            TagDataSourceFactory tagDataSourceFactory,
            Prefs prefs,
            ApiServiceUtil apiServiceUtil,
            IDeploymentState deploymentState,
            GeoJsonDataSourceFactory geoJsonDataSourceFactory,
            IGeoJsonRepository geoJsonRepository
    ) {
        mListPost = Preconditions.checkNotNull(listPost, "ListPost cannot be null");
        mFetchTag = fetchTag;
        mFetchGeoJson = fetchGeoJson;
        mSearch = Preconditions.checkNotNull(search, "Search cannot be null");
        mFetchPost = Preconditions.checkNotNull(fetchPost, "Fetch Post listing");
        mPostModelDataMapper = Preconditions
                .checkNotNull(postModelDataMapper, "PostModelDataMapper cannot be null");
        mPostRepository = Preconditions.checkNotNull(postRepository,
                "Post repository cannot be null.");
        mTagRepository = Preconditions.checkNotNull(tagRepository, "Tag Repository cannot be null");
        mPrefs = Preconditions.checkNotNull(prefs, "Preferences cannot be null");
        mPostDataSourceFactory = Preconditions
                .checkNotNull(postDataSourceFactory, "Post data source factory cannot be null.");
        mTagDataSourceFactory = Preconditions
                .checkNotNull(tagDataSourceFactory, "Tag data source factory cannot be null.");
        mApiServiceUtil = apiServiceUtil;
        mDeploymentState = deploymentState;
        mGeoJsonDataSourceFactory = geoJsonDataSourceFactory;
        mGeoJsonRepository = geoJsonRepository;
    }

    private void setPostService(PostService postService) {
        mPostDataSourceFactory.setPostService(postService);
        mListPost.setPostRepository(mPostRepository);
        mFetchPost.setPostRepository(mPostRepository);
        mSearch.setRepository(mPostRepository);
    }

    public void setTagService(TagService tagService) {
        mTagDataSourceFactory.setTagService(tagService);
        mFetchTag.setTagRepository(mTagRepository);
    }

    private void setGeoJsonService(GeoJsonService geoJsonService) {
        mGeoJsonDataSourceFactory.setGeoJsonService(geoJsonService);
        mFetchGeoJson.setGeoJsonRepository(mGeoJsonRepository);
    }

    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null.");
        }
        mView = view;
    }

    private PostService createPostService() {
        return mApiServiceUtil.createService(PostService.class,
                mPrefs.getActiveDeploymentUrl().get(), mPrefs.getAccessToken().get());
    }

    private TagService createTagService() {
        return mApiServiceUtil
                .createService(TagService.class, mPrefs.getActiveDeploymentUrl().get(),
                        mPrefs.getAccessToken().get());
    }

    private GeoJsonService createGeoJsonService() {
        return mApiServiceUtil.createService(GeoJsonService.class,
                mPrefs.getActiveDeploymentUrl().get(), mPrefs.getAccessToken().get());
    }

    @Override
    public void resume() {
        mDeploymentState.registerEvent(this);
        loadPostListFromLocalCache();
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    public void onDetach() {
        mCallback = null;
        mFetchGeoJsonCallback = null;
        mFetchTagCallback = null;
        mSearchCallback = null;
    }

    @Override
    public void pause() {
        mDeploymentState.unregisterEvent(this);
    }

    public void init() {
        setTagService(createTagService());
        setPostService(createPostService());
        setGeoJsonService(createGeoJsonService());
    }

    private void loadPostListFromLocalCache() {
        showViewLoading();
        getPostListFromLocalCache();
    }

    @Subscribe
    public void onActivatedDeploymentChanged(
            IDeploymentState.ActivatedDeploymentChangedEvent event) {
        loadPostListFromLocalCache();
    }

    public void loadfromLocalCache() {
        loadPostListFromLocalCache();
    }

    public void onPostClicked(PostModel postModel) {
        mView.viewPost(postModel);
    }

    private void showViewLoading() {
        mView.showLoading();
    }

    private void hideViewLoading() {
        mView.hideLoading();
    }

    private void showViewRetry(String message) {
        mView.showRetry(message);
    }

    private void showErrorMessage(ErrorWrap errorWrap) {
        if (mView.getAppContext() != null) {
            String errorMessage = ErrorMessageFactory.create(mView.getAppContext(),
                    errorWrap.getException());
            if (errorMessage.equals(mView.getAppContext()
                    .getString(R.string.exception_message_no_connection))) {
                showViewRetry(errorMessage);
            } else {
                mView.showError(errorMessage);
            }

        }
    }

    private void showPostsListInView(List<Post> listPosts) {
        final List<PostModel> postModelsList =
                mPostModelDataMapper.map(listPosts);
        mView.renderPostList(postModelsList);
    }

    private void getPostListFromLocalCache() {
        mListPost.execute(mPrefs.getActiveDeploymentId().get(), mListCallback);
    }

    public void fetchPostFromApi() {
        // Fetches Tag first
        setTagService(createTagService());
        mFetchTag.execute(mFetchTagCallback);
    }

    public void search(String search) {
        mSearch.execute(search, mSearchCallback);
    }

    public interface View extends ILoadViewData {

        /**
         * Render a post list in the UI.
         *
         * @param postModel The collection of {@link com.ushahidi.android.model.PostModel} that
         *                  will
         *                  be shown.
         */
        void renderPostList(List<PostModel> postModel);

        /**
         * View {@link com.ushahidi.android.model.PostModel} details
         *
         * @param postModel The post model to view
         */
        void viewPost(PostModel postModel);

        /**
         * Refreshes the existing items in the list view without any visual loaders
         */
        void showEmptySearchResultsInfo();

    }
}
