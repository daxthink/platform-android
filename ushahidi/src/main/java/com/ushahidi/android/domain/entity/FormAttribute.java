package com.ushahidi.android.domain.entity;


import com.addhen.android.raiburari.domain.entity.Entity;

import java.util.List;

/**
 * Form attribute
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttribute extends Entity {

    private String mLabel;

    private String mKey;

    private Input mInput;

    private Type mType;

    private Boolean mRequired;

    private Integer mPriority;

    private List<String> mOptions;

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

    public enum Input {

        /** A map widget or input type */
        LOCATION("location"),
        /** A text input field */
        TEXT("text"),
        /** A drop down select input type */
        SELECT("select"),
        /** Date picker */
        DATE("date"),
        /** TextArea input type */
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

        /** A Varchar type */
        VARCHAR("varchar"),
        /** A point type */
        POINT("point"),
        /** A datetime type */
        DATETIME("datetime"),
        /** A text type */
        TEXT("text"),
        /** A geometry type */
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
                + '}';
    }
}
