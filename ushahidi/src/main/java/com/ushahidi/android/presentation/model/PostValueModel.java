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
 * PostValue model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostValueModel extends Model implements Parcelable {

    /**
     * Creates a {@link PostValueModel} parcelable object
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostValueModel> CREATOR
            = new Parcelable.Creator<PostValueModel>() {
        @Override
        public PostValueModel createFromParcel(Parcel in) {
            return new PostValueModel(in);
        }

        @Override
        public PostValueModel[] newArray(int size) {
            return new PostValueModel[size];
        }
    };

    private String mValues;

    private long mDeploymentId;

    /**
     * Default constructor
     */
    public PostValueModel() {

    }

    /**
     * Constructs a {@link PostValueModel} with initialized value retried from the passed {@link
     * Parcel}
     *
     * @param in The parcel
     */
    protected PostValueModel(Parcel in) {
        mValues = in.readString();
        mDeploymentId = in.readLong();
    }

    public String getValues() {
        return mValues;
    }

    public void setValues(String values) {
        mValues = values;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "PostValue{"
                + "mValues='" + mValues + '\''
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mValues);
        dest.writeLong(mDeploymentId);
    }
}