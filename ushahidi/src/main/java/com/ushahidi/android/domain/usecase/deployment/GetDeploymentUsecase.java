package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.android.domain.entity.Deployment} use case
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GetDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    private Long mDeploymentId;

    private String mUrl = null;

    /**
     * Default constructor
     *
     * @param deploymentRepository The deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected GetDeploymentUsecase(DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    /**
     * Sets the deployment id to be used to fetch the {@link com.ushahidi.android.domain.entity.Deployment}
     *
     * @param deploymentId the deployment id
     */
    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    /**
     * Sets the deployment url to be used to fetch the {@link com.ushahidi.android.data.entity.DeploymentEntity}
     *
     * @param url The url associated with the deployment
     */
    public void setDeploymentUrl(String url) {
        mUrl = url;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mUrl == null) {
            if (mDeploymentId == null) {
                throw new IllegalStateException("Either the deploymentId or the deploymentUrl should be a non null "
                        + "value");
            }
            return mDeploymentRepository.getEntity(mDeploymentId);
        }
        return mDeploymentRepository.getDeploymentEntity(mUrl);
    }

}