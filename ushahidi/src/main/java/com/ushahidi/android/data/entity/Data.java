package com.ushahidi.android.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class Data {

    /**
     * The id field has an underscore to ensure any existing id value will be
     * replaced. if _id is null then a new entity will be created. This is {@link cupboard()} way
     * of avoiding duplicated IDs
     */
    @SerializedName("id")
    public Long _id;
}
