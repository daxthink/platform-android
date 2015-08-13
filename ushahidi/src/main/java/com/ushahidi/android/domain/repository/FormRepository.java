package com.ushahidi.android.domain.repository;

import com.addhen.android.raiburari.domain.repository.Repository;
import com.ushahidi.android.domain.entity.Form;

import rx.Observable;

/**
 * Repository for manipulating {@link Form} data
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormRepository extends Repository<Form> {

    /**
     * Get an {@link Form} by its status.
     *
     * @param status The form status to be used for retrieving form data.
     * @return The form
     */
    Observable<Form> getByStatus(boolean status);
}
