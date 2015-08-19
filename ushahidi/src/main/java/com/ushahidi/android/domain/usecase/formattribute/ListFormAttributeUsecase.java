package com.ushahidi.android.domain.usecase.formattribute;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormAttributeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.android.domain.entity.FormAttribute} from local storage or via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListFormAttributeUsecase extends Usecase {

    private final FormAttributeRepository mFormAttributeRepository;

    private Long mDeploymentId = null;

    private From mFrom;

    /**
     * Default constructor
     *
     * @param formAttributeRepository The FormAttributeJson Repository
     * @param threadExecutor          The thread executor
     * @param postExecutionThread     The post execution thread
     */
    @Inject
    protected ListFormAttributeUsecase(FormAttributeRepository formAttributeRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFormAttributeRepository = formAttributeRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.GeoJson}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param from         Whether to fetch through the API or the local storage
     */
    public void setListFormAttribute(Long deploymentId, From from) {
        mDeploymentId = deploymentId;
        mFrom = from;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFrom == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setListFormAttribute(...)");
        }
        return mFormAttributeRepository.getFormAttributes(mDeploymentId, mFrom);
    }

}
