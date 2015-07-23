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

package com.ushahidi.android.data.entity;

import com.google.gson.annotations.SerializedName;

import com.addhen.android.raiburari.data.entity.DataEntity;

import java.util.Date;
import java.util.List;

import nl.qbusict.cupboard.annotation.Ignore;

/**
 * Post data entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostEntity extends DataEntity {

    @SerializedName("parent")
    @Ignore // Make cupboard ignore this field
    private Parent parent;

    private transient Long mParent;

    @SerializedName("user")
    private UserEntity mUser;

    @SerializedName("type")
    private Type mType;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("slug")
    private String mSlug;

    @SerializedName("content")
    private String mContent;

    @SerializedName("author_email")
    private String mAuthorEmail;

    @SerializedName("author_realname")
    private String mAuthorRealname;

    @SerializedName("status")
    private Status mStatus;

    @SerializedName("created")
    private Date mCreated;

    @SerializedName("updated")
    private Date mUpdated;

    @SerializedName("values")
    private PostValueEntity mValues;

    @SerializedName("tags")
    @Ignore
    private List<PostTagEntity> mPostTagEntityList;

    private long mDeploymentId;

    private transient List<TagEntity> mTags;

    public Long getParent() {
        return mParent;
    }

    public void setParent(Long parent) {
        mParent = parent;
    }

    public List<PostTagEntity> getPostTagEntityList() {
        return mPostTagEntityList;
    }

    public void setPostTagEntityList(List<PostTagEntity> postTagEntityList) {
        mPostTagEntityList = postTagEntityList;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAuthorRealname() {
        return mAuthorRealname;
    }

    public void setAuthorRealname(String authorRealname) {
        mAuthorRealname = authorRealname;
    }

    public String getAuthorEmail() {
        return mAuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        mAuthorEmail = authorEmail;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }

    public PostValueEntity getValues() {
        return mValues;
    }

    public void setValues(PostValueEntity values) {
        mValues = values;
    }

    public List<TagEntity> getTags() {
        return mTags;
    }

    public void setTags(List<TagEntity> tags) {
        mTags = tags;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "Post{"
                + "mParent=" + mParent
                + ", mType=" + mType
                + ", mTitle='" + mTitle + '\''
                + ", mSlug='" + mSlug + '\''
                + ", mContent='" + mContent + '\''
                + ", mAuthorEmail='" + mAuthorEmail + '\''
                + ", mAuthorRealname='" + mAuthorRealname + '\''
                + ", mStatus=" + mStatus
                + ", mCreated=" + mCreated
                + ", mUpdated=" + mUpdated
                + ", mDeploymentId=" + mDeploymentId
                + ", mValues=" + mValues
                + ", mTags=" + mTags
                + '}';
    }

    public enum Status {
        /**
         * A draft status
         */
        @SerializedName("draft")
        DRAFT("draft"),

        /**
         * A published status
         */
        @SerializedName("published")
        PUBLISHED("published"),

        /**
         * A pending status
         */
        @SerializedName("pending")
        PENDING("pending"),

        /**
         * An unknown status
         */
        @SerializedName("unknown")
        UNKNOWN("unknown");

        private String value;

        /**
         * The value property of the post
         *
         * @param value The value
         */
        Status(String value) {
            this.value = value;
        }

        /**
         * Gets value
         *
         * @return The value
         */
        public String getValue() {
            return value;
        }
    }

    public enum Type {
        /**
         * Report type
         */
        @SerializedName("report")
        REPORT("report"),
        /**
         * Updated type
         */
        @SerializedName("update")
        UPDATE("update"),
        /**
         * Revision
         */
        @SerializedName("revision")
        REVISION("revision"),
        /**
         * Unknown
         */
        @SerializedName("unknown")
        UNKNOWN("unknown");

        private String value;

        /**
         * Default constructor
         *
         * @param value The value
         */
        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Represents the parent property of the a post
     */
    public static class Parent {

        @SerializedName("id")
        private Long id;

        public Long getId() {
            return id;
        }
    }
}
