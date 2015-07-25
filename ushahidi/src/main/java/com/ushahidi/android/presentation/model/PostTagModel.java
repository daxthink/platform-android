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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostTagModel extends Model implements Parcelable {

    /**
     * Creates a {@link PostTagModel} parcelable object
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostTagModel> CREATOR
            = new Parcelable.Creator<PostTagModel>() {
        @Override
        public PostTagModel createFromParcel(Parcel in) {
            return new PostTagModel(in);
        }

        @Override
        public PostTagModel[] newArray(int size) {
            return new PostTagModel[size];
        }
    };

    private Long mPostId;

    private Long mTagId;

    private long mDeploymentId;

    /**
     * Constructs a {@link PostValueModel} with initialized value retried from the passed {@link
     * Parcel}
     *
     * @param in The parcel
     */
    protected PostTagModel(Parcel in) {
        mPostId = in.readByte() == 0x00 ? null : in.readLong();
        mTagId = in.readByte() == 0x00 ? null : in.readLong();
        mDeploymentId = in.readLong();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mPostId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mPostId);
        }
        if (mTagId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mTagId);
        }
        dest.writeLong(mDeploymentId);
    }
}