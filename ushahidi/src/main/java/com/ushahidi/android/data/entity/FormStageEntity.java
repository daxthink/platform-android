package com.ushahidi.android.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageEntity extends Data {

    private Long mDeploymentId;

    @SerializedName("form_id")
    private Long mFormId;

    @SerializedName("label")
    private String mLabel;

    @SerializedName("priority")
    private Integer mPriority;

    @SerializedName("required")
    private Boolean mRequired;

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
                + ", mFormId=" + mFormId
                + ", mLabel='" + mLabel + '\''
                + ", mPriority=" + mPriority
                + ", mRequired=" + mRequired
                + '}';
    }
}
