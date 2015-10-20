package com.ushahidi.android.domain.usecase.formstages;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormStageRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.android.domain.entity.FormStage} from local storage or via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListFormStageUsecase extends Usecase {

    private final FormStageRepository mFormStageRepository;

    private Long mDeploymentId = null;

    private Long mFormId;

    private From mFrom;

    /**
     * Default constructor
     *
     * @param formStageRepository The FormStageJson Repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    public ListFormStageUsecase(FormStageRepository formStageRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFormStageRepository = formStageRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.GeoJson}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param formId       Whether to fetch through the API or the local storage
     */
    public void setListFormStage(Long deploymentId, Long formId, From from) {
        mDeploymentId = deploymentId;
        mFormId = formId;
        mFrom = from;
    }

    @Override
    public Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFormId == null) {
            throw new RuntimeException(
                    "Deployment id and form id cannot be null. You must call setListFormStage(...)");
        }
        return mFormStageRepository.getFormStages(mDeploymentId, mFormId, mFrom);
    }

}
