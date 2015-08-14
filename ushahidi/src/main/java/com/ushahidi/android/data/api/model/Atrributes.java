package com.ushahidi.android.data.api.model;

import com.google.gson.annotations.SerializedName;
import com.ushahidi.android.data.entity.FormAttributeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Atrributes extends Response implements Serializable {
    private static final long serialVersionUID = 198741968948441199L;

    @SerializedName("results")
    private List<FormAttributeEntity> formAttributes;

    public List<FormAttributeEntity> getFormAttributes() {
        return formAttributes;
    }
}
