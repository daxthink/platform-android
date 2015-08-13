package com.ushahidi.android.data.entity;

import java.util.List;

/**
 * Form attribute
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeEntity {

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

    public static class Input {

        /** A map widget or input type */
        public static final String LOCATION = "location";

        /** A text input field */
        public static final String TEXT = "text";

        /** A drop down select input type */
        public static final String SELECT = "select";

        /** Date picker */
        public static final String DATE = "date";

        /** Textarea input type */
        public static final String TEXTAREA = "textarea";
    }

    public static class Type {

        /** A Varchar type */
        public static final String VARCHAR = "varchar";

        /** A point type */
        public static final String POINT = "point";

        /** A datetime type */
        public static final String DATETIME = "datetime";

        /** A text type */
        public static final String TEXT = "text";

        /** A geometry type */
        public static final String GEOMETRY = "geometry";
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
