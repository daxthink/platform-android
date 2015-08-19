package com.ushahidi.android.domain.repository;

import com.ushahidi.android.domain.entity.FormAttribute;
import com.ushahidi.android.domain.entity.From;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormAttributeRepository {

    /**
     * Get a list of {@link FormAttribute} from either the database or online.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param from         Where to get the formattribute from. Either from local storage or online
     * @return The retrieved form attribute.
     */
    Observable<List<FormAttribute>> getFormAttributes(Long deploymentId, From from);

    /**
     * Add/Update a {@link FormAttribute}.
     *
     * @param form The FormAttribute to be saved.
     * @return The row affected
     */
    Observable<Long> putFormAttribute(FormAttribute form);
}
