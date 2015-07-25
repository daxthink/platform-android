package com.ushahidi.android.presentation.presenter.post;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.usecase.post.GetPostUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.PostModelDataMapper;
import com.ushahidi.android.presentation.view.post.DetailPostView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Presenter for fetching post details and presents it to the view
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DetailPostPresenter implements Presenter {

    private final GetPostUsecase mGetPostUsecase;

    private final PrefsFactory mPrefsFactory;

    private final PostModelDataMapper mPostModelDataMapper;

    private DetailPostView mDetailPostView;

    /**
     * Default constructor
     *
     * @param getPostUsecase      The get post use case
     * @param postModelDataMapper The post model data mapper
     * @param prefsFactory        The preference factory
     */
    @Inject
    public DetailPostPresenter(@Named("postGet") GetPostUsecase getPostUsecase,
            PostModelDataMapper postModelDataMapper, PrefsFactory prefsFactory) {
        mGetPostUsecase = getPostUsecase;
        mPostModelDataMapper = postModelDataMapper;
        mPrefsFactory = prefsFactory;
    }

    public void setView(DetailPostView detailPostView) {
        mDetailPostView = detailPostView;
    }

    public void getPostDetails(Long postId) {
        mGetPostUsecase.setGetPost(mPrefsFactory.getActiveDeploymentId().get(), postId);
        mGetPostUsecase.execute(new GetPostSubscriber());
    }

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void destroy() {
        mGetPostUsecase.unsubscribe();
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mDetailPostView.getAppContext(),
                errorHandler.getException());
        mDetailPostView.showError(errorMessage);
    }

    private class GetPostSubscriber extends DefaultSubscriber<Post> {

        @Override
        public void onStart() {
            mDetailPostView.showLoading();
        }

        @Override
        public void onCompleted() {
            mDetailPostView.hideLoading();
        }

        @Override
        public void onNext(Post post) {
            mDetailPostView.showPostModel(mPostModelDataMapper.map(post));
        }

        @Override
        public void onError(Throwable e) {
            mDetailPostView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    }
}
