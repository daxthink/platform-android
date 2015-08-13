package com.ushahidi.android.domain.repository;

import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.GeoJson;

import rx.Observable;

/**
 * Repository for manipulating {@link Form} data
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormRepository {

    /**
     * Get a list of {@link GeoJson} from either the database or online.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.domain.entity.Deployment}
     * @param from         Where to get the form from. Either from local storage or online
     * @return The form details
     */
    Observable<Form> getForm(Long deploymentId, From from);

    /**
     * Add/Update a {@link Form}.
     *
     * @param form The Form to be saved.
     * @return The row affected
     */
    Observable<Long> putForm(Form form);
}
