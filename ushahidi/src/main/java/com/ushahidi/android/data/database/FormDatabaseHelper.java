package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.exception.FormNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class FormDatabaseHelper extends BaseDatabaseHelper {

    /**
     * Default constructor
     *
     * @param context The calling context. Cannot be a null value
     */
    @Inject
    public FormDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Gets form lists
     *
     * @param deploymentId The deployment the form entity is attached to
     * @return An Observable that emits a list of {@link FormEntity}
     */
    public Observable<List<FormEntity>> getForms(Long deploymentId) {
        return Observable.create(subscriber -> {
            final List<FormEntity> formEntities = cupboard()
                    .withDatabase(getReadableDatabase()).query(FormEntity.class).list();
            if (formEntities != null) {
                subscriber.onNext(formEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new FormNotFoundException());
            }
        });
    }

    /**
     * Gets form lists
     *
     * @param deploymentId The deployment the form entity is attached to
     * @param formId       The formId to retrieve the form by
     * @return An Observable that emits a list of {@link FormEntity}
     */
    public Observable<FormEntity> getForm(Long deploymentId, Long formId) {
        return Observable.create(subscriber -> {
            final FormEntity formEntity = cupboard().withDatabase(getReadableDatabase())
                    .query(FormEntity.class)
                    .withSelection("mDeploymentId = ? ", String.valueOf(deploymentId)).byId(formId)
                    .get();

            if (formEntity != null) {
                // Get form attribute
                final List<FormAttributeEntity> formAttributeEntities = getFormAttributeEntity(
                        formEntity);
                formEntity.setFormAttributeEntity(formAttributeEntities);
                subscriber.onNext(formEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new FormNotFoundException());
            }
        });
    }


    /**
     * Saves a {@link FormEntity} into the db
     *
     * @param formEntity The form to save to the db
     * @return The row affected
     */
    public Observable<Long> put(FormEntity formEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Long row = null;
                try {
                    row = cupboard().withDatabase(getWritableDatabase()).put(formEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(row);
                subscriber.onCompleted();
            }

        });
    }

    /**
     * Since {@link cupboad()} doesn't really support relationship, have to manually
     * query for form attribute entities and add them to the form entity
     *
     * @param formEntity The form entity
     * @return The form attribute entity
     */
    private List<FormAttributeEntity> getFormAttributeEntity(FormEntity formEntity) {

        return cupboard().withDatabase(getReadableDatabase())
                .query(FormAttributeEntity.class)
                .withSelection("mDeploymentId = ?", String.valueOf(formEntity.getDeploymentId()))
                .withSelection("mFormId = ?", String.valueOf(formEntity._id)).list();

    }
}
