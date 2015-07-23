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

package com.ushahidi.android.presentation.model;

import com.addhen.android.raiburari.presentation.model.Model;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostTagModel extends Model {

    private Long mPostId;

    private Long mTagId;

    private long mDeploymentId;

    public Long getPostId() {
        return mPostId;
    }

    public void setPostId(Long postId) {
        mPostId = postId;
    }

    public Long getTagId() {
        return mTagId;
    }

    public void setTagId(Long tagId) {
        mTagId = tagId;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "PostTagModel{"
                + "mPostId=" + mPostId
                + ", mTagId=" + mTagId
                + ", _id=" + _id
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }
}
