package com.ushahidi.android.presentation.model;

import com.addhen.android.raiburari.presentation.model.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Form attribute model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageModel extends Model implements Parcelable {

    /**
     * Creates a {@FormStageModel} parcelable
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FormStageModel> CREATOR
            = new Parcelable.Creator<FormStageModel>() {
        @Override
        public FormStageModel createFromParcel(Parcel in) {
            return new FormStageModel(in);
        }

        @Override
        public FormStageModel[] newArray(int size) {
            return new FormStageModel[size];
        }
    };

    private Long mDeploymentId;

    private Long mFormId;

    private String mLabel;

    private Integer mPriority;

    private Boolean mRequired;

    public FormStageModel() {
    }

    protected FormStageModel(Parcel in) {
        _id = in.readByte() == 0x00 ? null : in.readLong();
        mDeploymentId = in.readByte() == 0x00 ? null : in.readLong();
        mFormId = in.readByte() == 0x00 ? null : in.readLong();
        mLabel = in.readString();
        mPriority = in.readByte() == 0x00 ? null : in.readInt();
        byte mRequiredVal = in.readByte();
        mRequired = mRequiredVal == 0x02 ? null : mRequiredVal != 0x00;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    public Long getFormId() {
        return mFormId;
    }

    public void setFormId(Long formId) {
        mFormId = formId;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public Integer getPriority() {
        return mPriority;
    }

    public void setPriority(Integer priority) {
        mPriority = priority;
    }

    public Boolean getRequired() {
        return mRequired;
    }

    public void setRequired(Boolean required) {
        mRequired = required;
    }

    @Override
    public String toString() {
        return "FormStage{"
                + "mDeploymentId=" + mDeploymentId
                + "mID=" + _id
                + ", mFormId=" + mFormId
                + ", mLabel='" + mLabel + '\''
                + ", mPriority=" + mPriority
                + ", mRequired=" + mRequired
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(_id);
        }
        if (mDeploymentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mDeploymentId);
        }
        if (mFormId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mFormId);
        }
        dest.writeString(mLabel);
        if (mPriority == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mPriority);
        }
        if (mRequired == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (mRequired ? 0x01 : 0x00));
        }
    }
}
