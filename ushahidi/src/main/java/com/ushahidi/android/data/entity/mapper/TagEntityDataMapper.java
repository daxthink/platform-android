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

import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.domain.entity.Tag;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagEntityDataMapper {

    @Inject
    public TagEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link TagEntity} to {@link Tag}
     *
     * @param tagEntity The {@link TagEntity} to be mapped
     * @return The {@link Tag} entity
     */
    public Tag map(TagEntity tagEntity) {
        Tag tag = null;

        if (tagEntity != null) {
            tag = new Tag();
            tag._id = tagEntity._id;
            tag.setDescription(tagEntity.getDescription());
            tag.setPriority(tagEntity.getPriority());
            tag.setCreated(tagEntity.getCreated());
            tag.setIcon(tagEntity.getIcon());
            tag.setParentId(tagEntity.getParentId());
            tag.setColor(tagEntity.getColor());
            tag.setType(Tag.Type.valueOf(tagEntity.getType().name()));
            tag.setTag(tagEntity.getTag());
        }

        return tag;
    }

    public TagEntity map(Tag tag) {
        TagEntity tagEntity = null;

        if (tag != null) {
            tagEntity = new TagEntity();
            tagEntity._id = tag._id;
            tagEntity.setDescription(tag.getDescription());
            tagEntity.setPriority(tag.getPriority());
            tagEntity.setParentId(tag.getParentId());
            tagEntity.setCreated(tag.getCreated());
            tagEntity.setIcon(tag.getIcon());
            tagEntity.setTag(tag.getTag());
            tagEntity.setColor(tag.getColor());
            tagEntity.setType(TagEntity.Type.valueOf(tag.getType().name()));
        }
        return tagEntity;
    }

    /**
     * Maps a list {@link TagEntity} into a list of {@link Tag}.
     *
     * @param tagEntityList List to be mapped.
     * @return {@link Tag}
     */
    public List<Tag> map(List<TagEntity> tagEntityList) {
        List<Tag> tagList = new ArrayList<>();
        Tag tag;
        if (tagEntityList != null) {
            for (TagEntity tagEntity : tagEntityList) {
                tag = map(tagEntity);
                if (tag != null) {
                    tagList.add(tag);
                }
            }
        }

        return tagList;
    }

    /**
     * Maps a list {@link Tag} into a list of {@link TagEntity}.
     *
     * @param tagList List to be unmapped.
     * @return {@link Tag}
     */
    public List<TagEntity> unmap(List<Tag> tagList) {
        List<TagEntity> tagEntityList = new ArrayList<>();
        TagEntity tagEntity;
        for (Tag tag : tagList) {
            tagEntity = map(tag);
            if (tagEntity != null) {
                tagEntityList.add(tagEntity);
            }
        }

        return tagEntityList;
    }
}
