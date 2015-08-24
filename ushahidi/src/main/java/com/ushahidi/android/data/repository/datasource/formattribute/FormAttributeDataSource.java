package com.ushahidi.android.data.repository.datasource.formattribute;

import com.ushahidi.android.data.entity.FormAttributeEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormAttributeDataSource {

    /**
     * Get a list of {@link FormAttributeEntity} from the database.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param formId       The id of the form to be fetched
     * @return The form details
     */
    Observable<List<FormAttributeEntity>> getFormAttributes(Long deploymentId, Long formId);

    /**
     * Add/Update a {@link FormAttributeEntity}.
     *
     * @param formAttributeEntity The FormAttribute to be saved.
     * @return The row affected
     */
    Observable<Long> putFormAttribute(FormAttributeEntity formAttributeEntity);
}
