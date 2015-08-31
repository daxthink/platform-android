package com.ushahidi.android.data.repository.datasource.form;

import com.ushahidi.android.data.entity.FormEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormDataSource {

    /**
     * Get a list of {@link FormEntity}.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @return The form details
     */
    Observable<List<FormEntity>> getForms(Long deploymentId);

    /**
     * Get a list of {@link FormEntity} from either the database or online.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param formId       The id of the form to be fetched
     * @return The form details
     */
    Observable<FormEntity> getForm(Long deploymentId, Long formId);

    /**
     * Add/Update a {@link FormEntity}.
     *
     * @param formEntity The Form to be saved.
     * @return The row affected
     */
    Observable<Long> putForm(FormEntity formEntity);
}
