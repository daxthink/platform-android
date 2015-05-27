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

    private final Long mDeploymentId;

    @Inject
    protected GetDeploymentUsecase(Long deploymentId, DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentId = deploymentId;
        mDeploymentRepository = deploymentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mDeploymentRepository.getEntity(mDeploymentId);
    }
}
