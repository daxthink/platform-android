package com.ushahidi.android.data.entity;

import com.addhen.android.raiburari.data.entity.DataEntity;

import java.util.Date;
import java.util.List;

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

    private Long mDeploymentId;

    private List<FormAttributeEntity> mFormAttributeEntities;

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

    public void setFormAttributeEntity(List<FormAttributeEntity> formAttributeEntities) {
        mFormAttributeEntities = formAttributeEntities;
    }

    public List<FormAttributeEntity> getFormAttributeEntities() {
        return mFormAttributeEntities;
    }

    @Override
    public String toString() {
        return "Form{"
                + "mName='" + mName + '\''
                + ", mDescription='" + mDescription + '\''
                + ", mDisabled=" + mDisabled
                + ", mCreated=" + mCreated
                + ", mUpdated=" + mUpdated
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }
}
