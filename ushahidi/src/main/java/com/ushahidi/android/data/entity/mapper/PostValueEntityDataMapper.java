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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.PostEntity;
import com.ushahidi.android.data.entity.PostValueEntity;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.entity.PostValue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostValueEntityDataMapper {

    @Inject
    public PostValueEntityDataMapper() {
        // Do nothing
    }

    public PostValue map(PostValueEntity postEntity) {
        PostValue postValue = null;
        if (postEntity != null) {
            postValue = new PostValue();
            postValue.setValues(postEntity.getValues());
            postValue.setDeploymentId(postEntity.getDeploymentId());
        }
        return postValue;
    }

    public PostValueEntity map(PostValue postValue) {
        PostValueEntity postValueEntity = null;
        if (postValue != null) {
            postValueEntity = new PostValueEntity();
            postValueEntity.setDeploymentId(postValue.getDeploymentId());
            postValueEntity.setValues(postValue.getValues());
        }
        return postValueEntity;
    }

    /**
     * Maps a list {@link PostEntity} into a list of {@link Post}.
     *
     * @param postEntityList List to be mapped.
     * @return {@link Post}
     */
    public List<PostValue> map(List<PostValueEntity> postEntityList) {
        List<PostValue> postValueList = new ArrayList<>();
        PostValue post;
        for (PostValueEntity postValueEntity : postEntityList) {
            post = map(postValueEntity);
            if (post != null) {
                postValueList.add(post);
            }
        }
        return postValueList;
    }
}
