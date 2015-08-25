package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.exception.FormAttributeNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Form attribute database helper
 *
 * @author Henry Addo
 */
public class FormAttributeDatabaseHelper extends BaseDatabaseHelper {

    /**
     * Default constructor
     */
    @Inject
    public FormAttributeDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Gets {@link FormAttributeEntity} associated with form entity
     *
     * @param deploymentId The deployment the form entity is attached to
     * @param formId       The formId to retrieve the form by
     * @return Observable that emits a list of {@link FormAttributeEntity}
     */
    public Observable<List<FormAttributeEntity>> getFormAttributeEntity(Long deploymentId,
            Long formId) {
        return Observable.create(subscriber -> {
            final List<FormAttributeEntity> formAttributeEntities = cupboard()
                    .withDatabase(getReadableDatabase())
                    .query(FormAttributeEntity.class)
                    .withSelection("mDeploymentId = ?", String.valueOf(deploymentId))
                    .withSelection("mFormId = ?", String.valueOf(formId)).list();

            if (formAttributeEntities != null && formAttributeEntities.size() > 0) {
                subscriber.onNext(formAttributeEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new FormAttributeNotFoundException());
            }
        });

    }

    public Observable<Long> putFormAttributeEntity(FormAttributeEntity formAttributeEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Long row = null;
                try {
                    row = cupboard().withDatabase(getWritableDatabase()).put(formAttributeEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(row);
                subscriber.onCompleted();
            }
        });
    }
}
