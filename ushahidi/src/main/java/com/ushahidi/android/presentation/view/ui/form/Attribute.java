/*
 * Copyright (c) 2014 Ushahidi.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.view.ui.form;

import java.util.List;

/**
 * Form's attribute. This is based off V3's /api/v3/form API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Attribute {

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
}
