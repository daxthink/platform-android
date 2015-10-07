package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.FormStageEntity;
import com.ushahidi.android.data.exception.FormStageNotFoundException;

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
public class FormStageDatabaseHelper extends BaseDatabaseHelper {

    /**
     * Default constructor
     */
    @Inject
    public FormStageDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Gets {@link FormStageEntity} associated with form entity
     *
     * @param deploymentId The deployment the form entity is attached to
     * @param formId       The formId to retrieve the form by
     * @return Observable that emits a list of {@link FormStageEntity}
     */
    public Observable<List<FormStageEntity>> getFormStageEntity(Long deploymentId,
            Long formId) {
        return Observable.create(subscriber -> {
            final List<FormStageEntity> formStageEntities = cupboard()
                    .withDatabase(getReadableDatabase())
                    .query(FormStageEntity.class)
                    .withSelection("mDeploymentId = ?", String.valueOf(deploymentId))
                    .withSelection("mFormId = ?", String.valueOf(formId)).list();

            if (formStageEntities != null && formStageEntities.size() > 0) {
                subscriber.onNext(formStageEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new FormStageNotFoundException());
            }
        });

    }

    public Observable<Long> putFormStageEntity(List<FormStageEntity> formStageEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                try {
                    cupboard().withDatabase(getWritableDatabase()).put(formStageEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                int row = formStageEntity.size();
                subscriber.onNext((long) row);
                subscriber.onCompleted();
            }
        });
    }

    public void put(List<FormStageEntity> formStageEntity) {
        cupboard().withDatabase(getWritableDatabase()).put(formStageEntity);
    }

    /**
     * Clears all entries in the table
     */
    public void clearEntries() {
        cupboard().withDatabase(getWritableDatabase()).delete(FormStageEntity.class, null);
    }
}
