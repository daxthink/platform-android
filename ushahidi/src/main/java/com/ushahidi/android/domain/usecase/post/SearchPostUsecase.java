package com.ushahidi.android.domain.usecase.post;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.PostRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case for searching for {@link com.ushahidi.android.domain.entity.Post}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class SearchPostUsecase extends Usecase {

    private final PostRepository mPostRepository;

    private Long mDeploymentId = null;

    private String mQuery = null;

    /**
     * Default constructor
     *
     * @param postRepository      The post repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected SearchPostUsecase(PostRepository postRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mPostRepository = postRepository;
    }

    public void setSearchPost(Long deploymentId, String query) {
        mDeploymentId = deploymentId;
        mQuery = query;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mQuery == null) {
            throw new RuntimeException(
                    "Deployment id and query cannot be null. You must call setSearchPost(...)");
        }
        return mPostRepository.search(mDeploymentId, mQuery);
    }
}
