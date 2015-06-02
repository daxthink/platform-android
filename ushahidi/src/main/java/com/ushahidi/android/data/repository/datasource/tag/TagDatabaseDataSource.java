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

import com.ushahidi.android.data.database.TagDatabaseHelper;
import com.ushahidi.android.data.entity.TagEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * Data source for manipulating {@link com.ushahidi.android.data.entity.TagEntity} data to and from
 * the database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagDatabaseDataSource implements TagDataSource {

    private final TagDatabaseHelper mTagDatabaseHelper;

    public TagDatabaseDataSource(
            @NonNull TagDatabaseHelper tagDatabaseHelper) {
        mTagDatabaseHelper = tagDatabaseHelper;
    }

    @Override
    public Observable<List<TagEntity>> getTagList(Long deploymentId) {
        return mTagDatabaseHelper.getTags(deploymentId);
    }

    @Override
    public Observable<Long> putTag(List<TagEntity> tagEntityList) {
        return mTagDatabaseHelper.putTags(tagEntityList);
    }

    @Override
    public Observable<Boolean> deleteTag(TagEntity tagEntity) {
        return mTagDatabaseHelper.deleteTag(tagEntity);
    }
}
