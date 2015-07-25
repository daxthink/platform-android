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

import java.util.Date;

/**
 * Tag model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagModel extends Model implements Parcelable {

    /**
     * Creates a {@link TagModel} parcelable object
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TagModel> CREATOR = new Parcelable.Creator<TagModel>() {
        @Override
        public TagModel createFromParcel(Parcel in) {
            return new TagModel(in);
        }

        @Override
        public TagModel[] newArray(int size) {
            return new TagModel[size];
        }
    };

    private Long mParentId;

    private String mTag;

    private String mColor;

    private Type mType;

    private String mIcon;

    private String mDescription;

    private int mPriority;

    private Date mCreated;

    private Long mDeploymentId;

    /**
     * Default constructor
     */
    public TagModel() {
    }

    /**
     * Constructs a {@link TagModel} with initialized value retried from the passed {@link
     * Parcel}
     *
     * @param in The parcel
     */
    protected TagModel(Parcel in) {
        mParentId = in.readByte() == 0x00 ? null : in.readLong();
        mTag = in.readString();
        mColor = in.readString();
        mType = (Type) in.readValue(Type.class.getClassLoader());
        mIcon = in.readString();
        mDescription = in.readString();
        mPriority = in.readInt();
        long tmpMCreated = in.readLong();
        mCreated = tmpMCreated != -1 ? new Date(tmpMCreated) : null;
        mDeploymentId = in.readByte() == 0x00 ? null : in.readLong();
    }

    public Long getParentId() {
        return mParentId;
    }

    public void setParentId(Long parentId) {
        mParentId = parentId;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "Tag{"
                + "mParentId=" + mParentId
                + ", mTag='" + mTag + '\''
                + ", mColor='" + mColor + '\''
                + ", mType=" + mType
                + ", mIcon='" + mIcon + '\''
                + ", mDescription='" + mDescription + '\''
                + ", mPriority=" + mPriority
                + ", mCreated=" + mCreated
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }

    /**
     * Represents the tag entity type
     */
    public enum Type {
        /**
         * The category
         */
        CATEGORY("category"),

        /**
         * The status
         */
        STATUS("status");

        private final String value;

        /**
         * Default constructor
         *
         * @param value The value
         */
        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mParentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mParentId);
        }
        dest.writeString(mTag);
        dest.writeString(mColor);
        dest.writeValue(mType);
        dest.writeString(mIcon);
        dest.writeString(mDescription);
        dest.writeInt(mPriority);
        dest.writeLong(mCreated != null ? mCreated.getTime() : -1L);
        if (mDeploymentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mDeploymentId);
        }
    }
}