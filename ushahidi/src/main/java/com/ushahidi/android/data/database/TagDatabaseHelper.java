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

import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.data.exception.TagNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Database helper for manipulating data cached on the device for {@link
 * com.ushahidi.android.data.entity.TagEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class TagDatabaseHelper extends BaseDatabaseHelper {

    public TagDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    public Observable<List<TagEntity>> getTags(Long deploymentId) {
        return Observable.create(subscriber -> {
            final List<TagEntity> tagEntityList = cupboard()
                    .withDatabase(getReadableDatabase()).query(TagEntity.class)
                    .withSelection("mDeploymentId = ?", String.valueOf(deploymentId)).list();
            if (tagEntityList != null) {
                subscriber.onNext(tagEntityList);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new TagNotFoundException());
            }
        });
    }

    public Observable<Long> putTags(List<TagEntity> tags) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                try {
                    cupboard().withDatabase(getWritableDatabase()).put(tags);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                // Pass 1 to fulfill the return type of the observable
                subscriber.onNext(1l);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> deleteTag(TagEntity tagEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Boolean status = false;
                try {
                    status = cupboard().withDatabase(getWritableDatabase()).delete(tagEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(status);
                subscriber.onCompleted();
            }
        });
    }
}
