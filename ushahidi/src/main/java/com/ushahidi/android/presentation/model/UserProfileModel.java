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
 * UserProfileModel
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UserProfileModel extends Model implements Parcelable {

    /**
     * Creates a {@link UserProfileModel} parcelable object
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserProfileModel> CREATOR
            = new Parcelable.Creator<UserProfileModel>() {
        @Override
        public UserProfileModel createFromParcel(Parcel in) {
            return new UserProfileModel(in);
        }

        @Override
        public UserProfileModel[] newArray(int size) {
            return new UserProfileModel[size];
        }
    };

    private String mEmail;

    private String mRealName;

    private String mUsername;

    private Role mRole;

    private long mDeployment;

    private Date mCreated;

    private Date mUpdated;

    /**
     * Default constructor
     */
    public UserProfileModel() {

    }

    /**
     * Constructs a {@link UserProfileModel} with initialized value retried from the passed {@link
     * Parcel}
     *
     * @param in The parcel
     */
    protected UserProfileModel(Parcel in) {
        mEmail = in.readString();
        mRealName = in.readString();
        mUsername = in.readString();
        mRole = (Role) in.readValue(Role.class.getClassLoader());
        mDeployment = in.readLong();
        long tmpMCreated = in.readLong();
        mCreated = tmpMCreated != -1 ? new Date(tmpMCreated) : null;
        long tmpMUpdated = in.readLong();
        mUpdated = tmpMUpdated != -1 ? new Date(tmpMUpdated) : null;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String realName) {
        mRealName = realName;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public Role getRole() {
        return mRole;
    }

    public void setRole(Role role) {
        mRole = role;
    }

    public long getDeploymentId() {
        return mDeployment;
    }

    public void setDeploymentId(long deployment) {
        mDeployment = deployment;
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

    @Override
    public String toString() {
        return "UserProfileModel {"
                + "_id=" + _id
                + ", mEmail='" + mEmail + '\''
                + ", mRealName='" + mRealName + '\''
                + ", mUsername='" + mUsername + '\''
                + ", mRole=" + mRole
                + ", mDeployment=" + mDeployment
                + ", mCreated=" + mCreated
                + ", mUpdated=" + mUpdated
                + '}';
    }

    /**
     * Enum representing the different roles a user has
     */
    public enum Role {
        /**
         * The admin role
         */
        ADMIN("admin"),

        /**
         * The user role
         */
        USER("user");

        private final String value;

        /**
         * Default constructor
         *
         * @param value The role's value
         */
        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEmail);
        dest.writeString(mRealName);
        dest.writeString(mUsername);
        dest.writeValue(mRole);
        dest.writeLong(mDeployment);
        dest.writeLong(mCreated != null ? mCreated.getTime() : -1L);
        dest.writeLong(mUpdated != null ? mUpdated.getTime() : -1L);
    }
}