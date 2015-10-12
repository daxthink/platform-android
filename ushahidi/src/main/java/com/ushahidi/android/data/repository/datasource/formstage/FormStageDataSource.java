package com.ushahidi.android.data.repository.datasource.formstage;

import com.ushahidi.android.data.entity.FormStageEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormStageDataSource {

    /**
     * Get a list of {@link FormStageEntity} from the database.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param formId       The id of the form to be fetched
     * @return The form details
     */
    Observable<List<FormStageEntity>> getFormStages(Long deploymentId, Long formId);
}
