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
            String selection = " mFormId = ?  AND mDeploymentId = ?";
            String args[] = {String.valueOf(formId), String.valueOf(deploymentId)};
            final List<FormAttributeEntity> formAttributeEntities = cupboard()
                    .withDatabase(getReadableDatabase())
                    .query(FormAttributeEntity.class)
                    .withSelection(selection, args)
                    .list();

            if (formAttributeEntities != null && formAttributeEntities.size() > 0) {
                subscriber.onNext(formAttributeEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new FormAttributeNotFoundException());
            }
        });

    }

    public Observable<Long> putFormAttributeEntity(List<FormAttributeEntity> formAttributeEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                try {
                    cupboard().withDatabase(getWritableDatabase()).put(formAttributeEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                int row = formAttributeEntity.size();
                subscriber.onNext((long) row);
                subscriber.onCompleted();
            }
        });
    }

    public void put(List<FormAttributeEntity> formAttributeEntity) {
        cupboard().withDatabase(getWritableDatabase()).put(formAttributeEntity);
    }

    /**
     * Clears all entries in the table
     */
    public void clearEntries() {
        cupboard().withDatabase(getWritableDatabase()).delete(FormAttributeEntity.class, null);
    }
}
