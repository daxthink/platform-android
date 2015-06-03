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

package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.data.entity.PostEntity;
import com.ushahidi.android.domain.entity.Post;
import com.ushahidi.android.domain.entity.PostValue;
import com.ushahidi.android.presentation.model.PostValueModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostValueModelDataMapper {

    @Inject
    public PostValueModelDataMapper() {
        // Do nothing
    }

    public PostValueModel map(@NonNull PostValue postValue) {
        PostValueModel postValueModel = new PostValueModel();
        postValueModel.setValues(postValue.getValues());
        postValueModel.setDeploymentId(postValue.getDeploymentId());
        return postValueModel;
    }

    public PostValue map(@NonNull PostValueModel postValueModel) {
        PostValue postValue = new PostValue();
        postValue.setDeploymentId(postValueModel.getDeploymentId());
        postValue.setValues(postValueModel.getValues());
        return postValue;
    }

    /**
     * Maps a list {@link PostEntity} into a list of {@link Post}.
     *
     * @param postValueList List to be mapped.
     * @return {@link Post}
     */
    public List<PostValueModel> map(@NonNull List<PostValue> postValueList) {
        List<PostValueModel> postValueModelList = new ArrayList<>();
        PostValueModel postModel;
        for (PostValue postValue : postValueList) {
            postModel = map(postValue);
            if (postModel != null) {
                postValueModelList.add(postModel);
            }
        }
        return postValueModelList;
    }
}
