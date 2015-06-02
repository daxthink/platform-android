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

package com.ushahidi.android.data.repository.datasource.tag;

import com.ushahidi.android.data.api.TagApi;
import com.ushahidi.android.data.entity.TagEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Data source for manipulating {@link com.ushahidi.android.data.entity.TagEntity} data to and from
 * the API.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagApiDataSource implements TagDataSource {

    private final TagApi mTagApi;

    public TagApiDataSource(@NonNull TagApi tagApi) {
        mTagApi = tagApi;
    }

    @Override
    public Observable<List<TagEntity>> getTagList() {
        return mTagApi.getGeoJson();
    }

    @Override
    public Observable<Long> putTag(TagEntity tag) {
        // Do nothing. For now we're posting tags via the API
        return null;
    }

    @Override
    public Observable<Long> deleteTag(Long id) {
        // Do nothing. For now we're not deleting tags via the API
        return null;
    }
}
