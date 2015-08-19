package com.ushahidi.android.domain.usecase.formattribute;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.FormAttributeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.android.domain.entity.FormAttribute} from local storage or via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GetFormAttributeUsecase extends Usecase {

    private final FormAttributeRepository mFormAttributeRepository;

    private Long mDeploymentId = null;

    private Long mFormAttributeId;

    /**
     * Default constructor
     *
     * @param formAttributeRepository The FormAttributeJson Repository
     * @param threadExecutor          The thread executor
     * @param postExecutionThread     The post execution thread
     */
    @Inject
    protected GetFormAttributeUsecase(FormAttributeRepository formAttributeRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFormAttributeRepository = formAttributeRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.GeoJson}
     * and where to fetch it from.
     *
     * @param deploymentId    The deploymentId associated with the GeoJson
     * @param formAttributeId The ID of the form attribute
     */
    public void setGetFormAttribute(Long deploymentId, Long formAttributeId) {
        mDeploymentId = deploymentId;
        mFormAttributeId = formAttributeId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFormAttributeId == null) {
            throw new RuntimeException(
                    "Deployment id and form attribute id cannot be null. You must call setGetFormAttribute(...)");
        }
        return mFormAttributeRepository.getFormAttribute(mDeploymentId, mFormAttributeId);
    }

}
