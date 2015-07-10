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

package com.ushahidi.android.data.api;

import com.ushahidi.android.data.api.model.Tags;
import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.data.entity.TagEntity;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagApi {

    private final RestfulService mRestfulService;

    @Inject
    public TagApi(@NonNull RestfulService restfulService) {
        mRestfulService = restfulService;
    }

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link GeoJsonEntity}.
     */
    public Observable<List<TagEntity>> getTags() {
        return Observable.create((subscriber) -> mRestfulService.getTags().map(this::setTags)
        );
    }

    /**
     * Sets the {@link TagEntity} entity properties from the {@link Tags}
     *
     * @param tags The Tags model to be set as tag entity.
     */
    private Observable<List<TagEntity>> setTags(Tags tags) {
        return Observable.just(tags.getTags());
    }
}
