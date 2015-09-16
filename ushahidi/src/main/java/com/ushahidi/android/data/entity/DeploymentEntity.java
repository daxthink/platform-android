/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.data.entity;

import com.google.gson.annotations.SerializedName;
import com.ushahidi.android.domain.entity.Privilege;

import java.util.List;

import nl.qbusict.cupboard.annotation.Ignore;

/**
 * Deployment Data Entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentEntity {

    /**
     * The id field has an underscore to ensure any existing id value will be
     * replaced. if _id is null then a new entity will be created. This is {@link cupboard()} way
     * of avoiding duplicated IDs
     */
    public Long _id;

    private Status mStatus;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("timezone")
    private String mTimeZone;

    @SerializedName("language")
    private String mLanguage;

    @SerializedName("date_format")
    private String mDateFormat;

    @SerializedName("client_url")
    private String mClientUrl;

    @SerializedName("allowed_privileges")
    @Ignore
    private List<Privilege> mAllowedPrivileges;

    @SerializedName("image_header")
    private String mImageHeader;

    @SerializedName("owner_name")
    private String mOwnerName;

    @SerializedName("site_name")
    private String mSiteName;

    /**
     * Default constructor
     */
    public DeploymentEntity() {
        mStatus = Status.DEACTIVATED;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getDateFormat() {
        return mDateFormat;
    }

    public void setDateFormat(String dateFormat) {
        mDateFormat = dateFormat;
    }

    public String getClientUrl() {
        return mClientUrl;
    }

    public void setClientUrl(String clientUrl) {
        mClientUrl = clientUrl;
    }

    public String getImageHeader() {
        return mImageHeader;
    }

    public void setImageHeader(String imageHeader) {
        mImageHeader = imageHeader;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public String getSiteName() {
        return mSiteName;
    }

    public void setSiteName(String siteName) {
        mSiteName = siteName;
    }

    public List<Privilege> getAllowedPrivileges() {
        return mAllowedPrivileges;
    }

    public void setAllowedPrivileges(List<Privilege> allowedPrivileges) {
        this.mAllowedPrivileges = allowedPrivileges;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public enum Status {
        /** Represents an activated deployment **/
        ACTIVATED,
        /** Represents de-activated deployment **/
        DEACTIVATED
    }

    @Override
    public String toString() {
        return "DeploymentEntity {"
                + ", mStatus='" + mStatus
                + ", mUrl='" + mUrl
                + ", mName='" + mName
                + ", mDescription='" + mDescription
                + ", mEmail=" + mEmail
                + ", mTimezone='" + mTimeZone
                + ", mLanguage='" + mLanguage
                + ", mDateFormat=" + mDateFormat
                + ", mClientUrl=" + mClientUrl
                + ", mAllowedPrivileges=" + mAllowedPrivileges
                + ", mImageHeader=" + mImageHeader
                + ", mOwnerName=" + mOwnerName
                + ", mSiteName=" + mSiteName
                + '}';
    }

}