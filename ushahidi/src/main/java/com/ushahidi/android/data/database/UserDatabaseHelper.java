/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.data.database;

import com.ushahidi.android.data.entity.UserEntity;
import com.ushahidi.android.data.exception.GeoJsonNotFoundException;
import com.ushahidi.android.data.exception.TagNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Users database helper
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class UserDatabaseHelper extends BaseDatabaseHelper {

    /**
     * Default constructor
     *
     * @param context The calling context. Cannot be a null value
     */
    @Inject
    public UserDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Gets {@link UserEntity} from the database.
     *
     * @param deploymentId The deployment ID to be used for fetching the {@link UserEntity}
     * @param userEntityId The user ID
     * @return An observable that emits {@link UserEntity}
     */
    public Observable<UserEntity> getUserProfile(Long deploymentId, Long userEntityId) {
        return Observable.create(subscriber -> {
            final UserEntity userEntity = cupboard()
                    .withDatabase(getReadableDatabase()).query(UserEntity.class)
                    .byId(userEntityId)
                    .withSelection("mDeployment = ?", String.valueOf(deploymentId)).get();
            if (userEntity != null) {
                subscriber.onNext(userEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new GeoJsonNotFoundException());
            }
        });
    }

    /**
     * Gets a list of {@link UserEntity}
     *
     * @param deploymentId The deployment Id
     * @return An Observable that emits {@link UserEntity}
     */
    public Observable<List<UserEntity>> getUserProfiles(Long deploymentId) {
        return Observable.create(subscriber -> {
            final List<UserEntity> userEntityList = cupboard()
                    .withDatabase(getReadableDatabase()).query(UserEntity.class)
                    .withSelection("mDeploymentId = ?", String.valueOf(deploymentId)).list();
            if (userEntityList != null) {
                subscriber.onNext(userEntityList);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new TagNotFoundException());
            }
        });
    }

    /**
     * Deletes user profile
     *
     * @param userProfile The user to deleted
     * @return True if successful otherwise false
     */
    public Observable<Boolean> deleteUserProfile(UserEntity userProfile) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Boolean status = false;
                try {
                    status = cupboard().withDatabase(getWritableDatabase()).delete(userProfile);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(status);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * Saves a user entity into the db
     *
     * @param userEntity The user entity to be saved
     * @return The row affected
     */
    public Observable<Long> putUser(UserEntity userEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                try {
                    cupboard().withDatabase(getWritableDatabase()).put(userEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                // Pass 1 to fulfill the return type of the observable
                subscriber.onNext(1l);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * Saves a user entity into the db
     *
     * @param userEntity The user entity
     */
    public void put(UserEntity userEntity) {
        if (!isClosed()) {
            try {
                cupboard().withDatabase(getWritableDatabase()).put(userEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
