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

package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.TagEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.tag.TagDataSource;
import com.ushahidi.android.data.repository.datasource.tag.TagDataSourceFactory;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.Tag;
import com.ushahidi.android.domain.repository.TagRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Implements {@link TagRepository} to provide tag data related manipulations
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagDataRepository implements TagRepository {

    private final TagEntityDataMapper mTagEntityDataMapper;

    private final TagDataSourceFactory mTagDataSourceFactory;

    @Inject
    public TagDataRepository(TagDataSourceFactory tagDataSourceFactory,
            TagEntityDataMapper tagEntityDataMapper) {
        mTagDataSourceFactory = tagDataSourceFactory;
        mTagEntityDataMapper = tagEntityDataMapper;
    }

    @Override
    public Observable<List<Tag>> getTagList(Long deploymentId, From from) {
        TagDataSource tagDataSource;
        if (from.equals(From.ONLINE)) {
            tagDataSource = mTagDataSourceFactory.createTagApiDataSource();
        } else {
            tagDataSource = mTagDataSourceFactory.createTagDatabaseDataSource();
        }
        return tagDataSource.getTagList(deploymentId)
                .map((tagEntities -> mTagEntityDataMapper.map(tagEntities)));
    }

    @Override
    public Observable<Long> putTag(List<Tag> entity) {
        final TagDataSource tagDataSource = mTagDataSourceFactory.createTagDatabaseDataSource();
        return tagDataSource.putTag(mTagEntityDataMapper.unmap(entity));
    }

    @Override
    public Observable<Boolean> deleteTag(Tag tag) {
        final TagDataSource tagDataSource = mTagDataSourceFactory.createTagDatabaseDataSource();
        return tagDataSource.deleteTag(mTagEntityDataMapper.map(tag));
    }
}
