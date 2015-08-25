package com.ushahidi.android.data.entity;

import com.google.gson.annotations.SerializedName;

import com.addhen.android.raiburari.data.entity.DataEntity;

import java.util.List;

import nl.qbusict.cupboard.annotation.Ignore;

/**
 * Form attribute
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeEntity extends DataEntity {

    @SerializedName("label")
    private String mLabel;

    @SerializedName("key")
    private String mKey;

    @SerializedName("input")
    private Input mInput;

    @SerializedName("type")
    private Type mType;

    @SerializedName("required")
    private Boolean mRequired;

    @SerializedName("priority")
    private Integer mPriority;

    @SerializedName("cardinality")
    private Integer mCardinality;

    @Ignore
    private List<String> mOptions;

    private Long mDeploymentId;

    private Long mFormId;

    public void setFormId(Long formId) {
        mFormId = formId;
    }

    public Long getFormId() {
        return mFormId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public Input getInput() {
        return mInput;
    }

    public void setInput(Input input) {
        mInput = input;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public Boolean getRequired() {
        return mRequired;
    }

    public void setRequired(Boolean required) {
        mRequired = required;
    }

    public Integer getPriority() {
        return mPriority;
    }

    public void setPriority(Integer priority) {
        mPriority = priority;
    }

    public List<String> getOptions() {
        return mOptions;
    }

    public void setOptions(List<String> options) {
        mOptions = options;
    }

    public void setCardinality(int cardinality) {
        mCardinality = cardinality;
    }

    public Integer getCardinality() {
        return mCardinality;
    }

    public enum Input {

        /**
         * A map widget or input type
         */
        @SerializedName("location")
        LOCATION("location"),

        /**
         * A text input field
         */
        @SerializedName("text")
        TEXT("text"),

        /**
         * A drop down select input type
         */
        @SerializedName("select")
        SELECT("select"),

        /**
         * Date picker
         */
        @SerializedName("date")
        DATE("date"),

        /**
         * Textarea input type
         */
        @SerializedName("textarea")
        TEXTAREA("textarea");

        private String value;

        Input(String value) {
            this.value = value;
        }

        /**
         * Gets value
         *
         * @return The value
         */
        public String getValue() {
            return value;
        }
    }

    public enum Type {

        /**
         * A Varchar type
         */
        @SerializedName("varchar")
        VARCHAR("varchar"),

        /**
         * A point type
         */
        @SerializedName("point")
        POINT("point"),

        /**
         * A datetime type
         */
        @SerializedName("datetime")
        DATETIME("datetime"),

        /**
         * A text type
         */
        @SerializedName("text")
        TEXT("text"),

        /**
         * A geometry type
         */
        @SerializedName("geometry")
        GEOMETRY("geometry");

        private String value;

        Type(String value) {
            this.value = value;
        }

        /**
         * Gets value
         *
         * @return The value
         */
        public String getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "FormAttribute{"
                + "mLabel='" + mLabel + '\''
                + ", mKey='" + mKey + '\''
                + ", mInput=" + mInput
                + ", mType=" + mType
                + ", mRequired=" + mRequired
                + ", mPriority=" + mPriority
                + ", mOptions=" + mOptions
                + ", mCardinality=" + mCardinality
                + '}';
    }
}
