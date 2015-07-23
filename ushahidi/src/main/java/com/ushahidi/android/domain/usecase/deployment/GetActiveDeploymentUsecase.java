package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case for getting an active deployment.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GetActiveDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    /**
     * Default constructor
     *
     * @param deploymentRepository The deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected GetActiveDeploymentUsecase(DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mDeploymentRepository.getByStatus(Deployment.Status.ACTIVATED);
    }
}
