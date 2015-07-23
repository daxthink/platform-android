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

import com.ushahidi.android.data.entity.TagEntity;

import java.util.List;

import rx.Observable;

/**
 * Data source for {@link TagEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface TagDataSource {

    /**
     * Gets an {@link Observable} that emits a list of {@link TagEntity}
     *
     * @param deploymentId The deployment ID to fetch tag list by
     * @return The list of tags
     */
    Observable<List<TagEntity>> getTagList(Long deploymentId);

    /**
     * Saves {@link TagEntity} into a data store
     *
     * @param tag The tag entity be saved into the data store
     * @return The row affected
     */
    Observable<Long> putTag(List<TagEntity> tag);

    /**
     * Deletes a tag
     *
     * @param tagEntity The tag entity be deleted
     * @return True if successfully deleted otherwise false
     */
    Observable<Boolean> deleteTag(TagEntity tagEntity);
}
