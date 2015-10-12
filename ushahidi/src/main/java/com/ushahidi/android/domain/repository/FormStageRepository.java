package com.ushahidi.android.domain.repository;

import com.ushahidi.android.domain.entity.FormStage;
import com.ushahidi.android.domain.entity.From;

import java.util.List;

import rx.Observable;

/**
 * Repository for form stages
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormStageRepository {

    /**
     * Get a list of {@link FormStage} from either the database or online.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param formId       The form Id
     * @return The retrieved form attribute.
     */
    Observable<List<FormStage>> getFormStages(Long deploymentId, Long formId, From from);

    /**
     * Add/Update a {@link FormStage}.
     *
     * @param form The FormStage to be saved.
     * @return The row affected
     */
    Observable<Long> putFormStage(FormStage form);

    /**
     * Get a list of {@link FormStage} from either the database or online.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param formStageId  The form attribute's ID
     * @return The retrieved form attribute.
     */
    Observable<FormStage> getFormStage(Long deploymentId, Long formStageId);
}
