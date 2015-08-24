package com.ushahidi.android.domain.usecase.form;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.FormRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.android.domain.entity.Form} from local storage.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GetFormUsecase extends Usecase {

    private final FormRepository mFormRepository;

    private Long mDeploymentId = null;

    private Long mFormId;

    /**
     * Default constructor
     *
     * @param formRepository      The FormJson Repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected GetFormUsecase(FormRepository formRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFormRepository = formRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.GeoJson}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param formId       The id of the form to be fetched
     */
    public void setGetForm(Long deploymentId, Long formId) {
        mDeploymentId = deploymentId;
        mFormId = formId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFormId == null) {
            throw new RuntimeException(
                    "Deployment id and form id cannot be null. You must call setGetForm(...)");
        }
        return mFormRepository.getForm(mDeploymentId, mFormId);
    }

}
