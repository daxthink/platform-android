package com.ushahidi.android.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Privilege entity. read, create, update, delete and search are the known values.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public enum Privilege {

    /**
     * Read privileges
     */
    @SerializedName("read")
    READ("read"),

    /**
     * Create Privileges
     */
    @SerializedName("create")
    CREATE("create"),

    /**
     * Update Privileges
     */
    @SerializedName("update")
    UPDATE("update"),

    /**
     * Delete Privileges
     */
    @SerializedName("delete")
    DELETE("delete"),

    /**
     * Search Privileges
     */
    @SerializedName("search")
    SEARCH("search");

    private String value;

    /**
     * Default constructor
     *
     * @param value The value
     */
    Privilege(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}