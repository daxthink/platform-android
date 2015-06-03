package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.data.exception.DeploymentNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentDatabaseHelper extends BaseDatabaseHelper {

    @Inject
    public DeploymentDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    public Observable<DeploymentEntity> getByStatus(final DeploymentEntity.Status status) {
        return Observable.create((subscriber) -> {
            final DeploymentEntity deploymentEntity = get(status);
            if (deploymentEntity != null) {
                subscriber.onNext(deploymentEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DeploymentNotFoundException());
            }
        });
    }

    public Observable<List<DeploymentEntity>> getDeployments() {
        return Observable.create(subscriber -> {
            final List<DeploymentEntity> deploymentEntities = cupboard()
                    .withDatabase(getReadableDatabase()).query(DeploymentEntity.class).list();
            if (deploymentEntities != null) {
                subscriber.onNext(deploymentEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DeploymentNotFoundException());
            }
        });
    }

    public Observable<DeploymentEntity> getDeployment(Long id) {
        return Observable.create(subscriber -> {
            final DeploymentEntity deploymentEntity = cupboard().withDatabase(getReadableDatabase())
                    .query(DeploymentEntity.class)
                    .byId(id).get();
            if (deploymentEntity != null) {
                subscriber.onNext(deploymentEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DeploymentNotFoundException());
            }
        });
    }

    public Observable<Long> put(DeploymentEntity deploymentEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Long row = null;
                try {
                    row = cupboard().withDatabase(getWritableDatabase()).put(deploymentEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(row);
                subscriber.onCompleted();
            }

        });
    }

    public Observable<Long> deleteDeployment(Long deploymentId) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                boolean deleted = false;
                try {
                    deleted = cupboard().withDatabase(getWritableDatabase())
                            .delete(DeploymentEntity.class, deploymentId);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                if (deleted) {
                    subscriber.onNext(1l);
                } else {
                    subscriber.onError(new Exception());
                }
                subscriber.onCompleted();
            }

        });
    }

    private DeploymentEntity get(final DeploymentEntity.Status status) {
        final DeploymentEntity deploymentEntity = cupboard()
                .withDatabase(getReadableDatabase()).query(DeploymentEntity.class)
                .withSelection("mStatus = ?", status.name()).get();
        return deploymentEntity;
    }
}
