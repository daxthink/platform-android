package com.ushahidi.android.data.api.model;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.android.data.entity.FormStageEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStages extends Response implements Serializable {

    private static final long serialVersionUID = 198741968948441199L;

    @SerializedName("results")
    private List<FormStageEntity> mFormStages;

    public List<FormStageEntity> getFormStages() {
        return mFormStages;
    }
}
