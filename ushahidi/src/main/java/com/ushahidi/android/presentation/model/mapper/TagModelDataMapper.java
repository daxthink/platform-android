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

import com.ushahidi.android.domain.entity.Tag;
import com.ushahidi.android.presentation.model.TagModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Maps {@link Tag} onto {@link TagModel}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public TagModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link Tag} to {@link TagModel}
     *
     * @param tag The {@link Tag} to be mapped
     * @return The {@link TagModel} model
     */
    public TagModel map(@NonNull Tag tag) {
        TagModel tagModel = new TagModel();
        tagModel._id = tag._id;
        tagModel.setDeploymentId(tag.getDeploymentId());
        tagModel.setDescription(tag.getDescription());
        tagModel.setPriority(tag.getPriority());
        tagModel.setCreated(tag.getCreated());
        tagModel.setIcon(tag.getIcon());
        tagModel.setParentId(tag.getParentId());
        tagModel.setColor(tag.getColor());
        tagModel.setType(TagModel.Type.valueOf(tag.getType().name()));
        tagModel.setTag(tag.getTag());

        return tagModel;
    }

    /**
     * Maps {@link TagModel} to {@link Tag}
     *
     * @param tagModel The {@link TagModel} to be mapped
     * @return The {@link Tag} model
     */
    public Tag map(@NonNull TagModel tagModel) {
        Tag tag = new Tag();
        tag._id = tagModel._id;
        tag.setDeploymentId(tagModel.getDeploymentId());
        tag.setDescription(tagModel.getDescription());
        tag.setPriority(tagModel.getPriority());
        tag.setParentId(tagModel.getParentId());
        tag.setCreated(tagModel.getCreated());
        tag.setIcon(tagModel.getIcon());
        tag.setTag(tagModel.getTag());
        tag.setColor(tagModel.getColor());
        tag.setType(Tag.Type.valueOf(tagModel.getType().name()));
        return tag;
    }

    /**
     * Maps a list {@link TagModel} into a list of {@link Tag}.
     *
     * @param tagList List to be mapped.
     * @return {@link Tag}
     */
    public List<TagModel> map(@NonNull List<Tag> tagList) {
        List<TagModel> tagModelList = new ArrayList<>();
        TagModel tag;
        for (Tag tagEntity : tagList) {
            tag = map(tagEntity);
            if (tag != null) {
                tagModelList.add(tag);
            }
        }
        return tagModelList;
    }

    /**
     * Maps a list {@link Tag} into a list of {@link TagModel}.
     *
     * @param tagModelList List to be unmapped.
     * @return {@link Tag}
     */
    public List<Tag> unmap(List<TagModel> tagModelList) {
        List<Tag> tagList = new ArrayList<>();
        Tag tag;
        for (TagModel tagModel : tagModelList) {
            tag = map(tagModel);
            if (tag != null) {
                tagList.add(tag);
            }
        }
        return tagList;
    }
}
