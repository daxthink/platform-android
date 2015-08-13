package com.ushahidi.android.data.entity;

import com.addhen.android.raiburari.data.entity.DataEntity;

import java.util.Date;

/**
 * Holds the raw V3's /api/v3/form JSON response
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormEntity extends DataEntity {

    private String mName;

    private String mDescription;

    private boolean mDisabled;

    private Date mCreated;

    private Date mUpdated;

    private FormAttributeEntity mAttributes;

    private Long mDeploymentId;

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

    public FormAttributeEntity getAttributes() {
        return mAttributes;
    }

    public void setAttributes(FormAttributeEntity attributes) {
        mAttributes = attributes;
    }

    @Override
    public String toString() {
        return "Form{"
                + "mName='" + mName + '\''
                + ", mDescription='" + mDescription + '\''
                + ", mDisabled=" + mDisabled
                + ", mCreated=" + mCreated
                + ", mUpdated=" + mUpdated
                + ", mAttributes='" + mAttributes + '\''
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }
}
