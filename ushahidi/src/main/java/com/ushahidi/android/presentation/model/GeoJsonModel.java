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
 * GeoJson Data Entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GeoJsonModel extends Model implements Parcelable {

    /**
     * Creates a parcelable {@link GeoJsonModel} type
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GeoJsonModel> CREATOR
            = new Parcelable.Creator<GeoJsonModel>() {
        @Override
        public GeoJsonModel createFromParcel(Parcel in) {
            return new GeoJsonModel(in);
        }

        @Override
        public GeoJsonModel[] newArray(int size) {
            return new GeoJsonModel[size];
        }
    };

    private String mGeoJson;

    private Long mDeploymentId;

    /**
     * Default constructor
     */
    public GeoJsonModel() {

    }

    /**
     * Constructs a {@link GeoJsonModel} with initialized value retried from the passed {@link
     * Parcel}
     *
     * @param in The parcel
     */
    protected GeoJsonModel(Parcel in) {
        mGeoJson = in.readString();
        mDeploymentId = in.readByte() == 0x00 ? null : in.readLong();
    }

    public String getGeoJson() {
        return mGeoJson;
    }

    public void setGeoJson(String geoJson) {
        mGeoJson = geoJson;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "GeoJsonModel{"
                + "mID='" + _id + '\''
                + "mDeploymentId='" + mDeploymentId + '\''
                + ", geojson='" + mGeoJson + '\''
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGeoJson);
        if (mDeploymentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mDeploymentId);
        }
    }

}