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
import com.ushahidi.android.data.database.TagDatabaseHelper;
import com.ushahidi.android.data.entity.TagEntity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
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

    private final TagDatabaseHelper mTagDatabaseHelper;

    public TagApiDataSource(@NonNull TagApi tagApi, TagDatabaseHelper tagDatabaseHelper) {
        mTagApi = tagApi;
        mTagDatabaseHelper = tagDatabaseHelper;
    }

    @Override
    public Observable<List<TagEntity>> getTagList(Long deploymentId) {
        return mTagApi.getGeoJson()
                .doOnNext(tag -> mTagDatabaseHelper.putTags(setDeploymentId(tag, deploymentId)));
    }

    @Override
    public Observable<Long> putTag(List<TagEntity> tagList) {
        // Do nothing. For now we're posting tags via the API
        return null;
    }

    @Override
    public Observable<Boolean> deleteTag(TagEntity tagEntity) {
        // Do nothing. For now we're not deleting tags via the API
        return null;
    }

    /**
     * Set the deployment ID for the TagEntity since it's not set by the
     * API
     *
     * @param tagEntities  The TagEntity to set the deployment Id on
     * @param deploymentId The ID of the deployment to set
     * @return observable
     */
    private List<TagEntity> setDeploymentId(List<TagEntity> tagEntities,
            Long deploymentId) {
        List<TagEntity> tagEntityList = new ArrayList<>(tagEntities.size());
        for (TagEntity tagEntity : tagEntities) {
            tagEntity.setDeploymentId(deploymentId);
            tagEntityList.add(tagEntity);
        }
        return tagEntityList;
    }
}
