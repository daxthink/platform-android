package com.ushahidi.android.domain.entity;


import com.addhen.android.raiburari.domain.entity.Entity;

/**
 * Form stages entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStage extends Entity {

    private Long mDeploymentId;

    private Long mFormId;

    private String mLabel;

    private Integer mPriority;

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
