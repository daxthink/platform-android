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
 * User account entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserAccountModel extends Model implements Parcelable {

    /**
     * Creates a {@link UserAccountModel} parcelable object
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserAccountModel> CREATOR
            = new Parcelable.Creator<UserAccountModel>() {
        @Override
        public UserAccountModel createFromParcel(Parcel in) {
            return new UserAccountModel(in);
        }

        @Override
        public UserAccountModel[] newArray(int size) {
            return new UserAccountModel[size];
        }
    };

    private String mAccountName;

    private String mPassword;

    /**
     * Default constructor
     */
    public UserAccountModel() {

    }

    /**
     * Constructs a {@link UserAccountModel} with initialized value retried from the passed {@link
     * Parcel}
     *
     * @param in The parcel
     */
    protected UserAccountModel(Parcel in) {
        mAccountName = in.readString();
        mPassword = in.readString();
    }

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public String toString() {
        return "UserAccount{"
                + "mAccountName='" + mAccountName + '\''
                + ", mPassword='" + mPassword
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAccountName);
        dest.writeString(mPassword);
    }
}