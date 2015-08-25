package com.ushahidi.android.presentation.model;

import com.addhen.android.raiburari.presentation.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Holds the raw V3's /api/v3/form JSON response
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormModel extends Model implements Parcelable {

    /**
     * Creates a {@link FormModel} parcelable object
     */
    public static final Parcelable.Creator<FormModel> CREATOR
            = new Parcelable.Creator<FormModel>() {
        @Override
        public FormModel createFromParcel(Parcel in) {
            return new FormModel(in);
        }

        @Override
        public FormModel[] newArray(int size) {
            return new FormModel[size];
        }
    };

    private String mName;

    private String mDescription;

    private boolean mDisabled;

    private Date mCreated;

    private Date mUpdated;

    private Long mDeploymentId;

    /**
     * Default constructor
     */
    public FormModel() {
        // Do nothing
    }

    protected FormModel(Parcel in) {
        _id = in.readLong();
        mName = in.readString();
        mDescription = in.readString();
        mDisabled = in.readByte() != 0x00;
        long tmpMCreated = in.readLong();
        mCreated = tmpMCreated != -1 ? new Date(tmpMCreated) : null;
        long tmpMUpdated = in.readLong();
        mUpdated = tmpMUpdated != -1 ? new Date(tmpMUpdated) : null;
        mDeploymentId = in.readByte() == 0x00 ? null : in.readLong();
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
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

    public boolean isDisabled() {
        return mDisabled;
    }

    public void setDisabled(boolean disabled) {
        mDisabled = disabled;
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
        return "Form{"
                + "_id='" + _id + '\''
                + "mName='" + mName + '\''
                + ", mDescription='" + mDescription + '\''
                + ", mDisabled=" + mDisabled
                + ", mCreated=" + mCreated
                + ", mUpdated=" + mUpdated
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mDisabled ? 0x01 : 0x00));
        dest.writeLong(mCreated != null ? mCreated.getTime() : -1L);
        dest.writeLong(mUpdated != null ? mUpdated.getTime() : -1L);
        if (mDeploymentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mDeploymentId);
        }
    }
}