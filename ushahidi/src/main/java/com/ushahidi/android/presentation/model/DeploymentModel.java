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

package com.ushahidi.android.presentation.model;

import com.addhen.android.raiburari.presentation.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Parcelable Deployment model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentModel extends Model implements Parcelable {

    private String mTitle;

    private Status mStatus;

    private String mUrl;

    public DeploymentModel() {
        mStatus = Status.DEACTIVATED;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "DeploymentModel{" +
                "_id=" + _id +
                ", mTitle='" + mTitle + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

    public DeploymentModel(Parcel in) {
        _id = in.readLong();
        mTitle = in.readString();
        mUrl = in.readString();
        mStatus = Status.valueOf(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<DeploymentModel> CREATOR
            = new Parcelable.Creator<DeploymentModel>() {

        @Override
        public DeploymentModel createFromParcel(Parcel source) {
            return new DeploymentModel(source);
        }

        @Override
        public DeploymentModel[] newArray(int size) {
            return new DeploymentModel[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(getTitle());
        dest.writeString(getUrl());
        dest.writeString(getStatus().name());
    }

    public enum Status {
        ACTIVATED, DEACTIVATED
    }
}
