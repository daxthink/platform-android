package com.ushahidi.android.data.api.model;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.entity.FormEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Forms extends Response implements Serializable {

    private static final long serialVersionUID = -1380430061677564230L;

    @SerializedName("results")
    private List<Form> mForms;

    private List<FormAttributeEntity> mFormAttributeEntities = new ArrayList<>();

    public List<FormEntity> getForms() {
        if (mForms != null && mForms.size() > 0) {
            for (Form form : mForms) {
                FormEntity formEntity = new FormEntity();
                formEntity._id = form.id;
                formEntity.setName(form.mName);
                formEntity.setDescription(form.mDescription);
                formEntity.setDisabled(form.mDisabled);
                formEntity.setCreated(form.mCreated);
                formEntity.setUpdated(form.mUpdated);
                setFormAttributeEntities(form.id, form.mAttributes);
                formEntity.setFormAttributeEntity(getFormAttributeEntities());
            }
        }
        return Collections.emptyList();
    }

    private void setFormAttributeEntities(Long formId, Form.Attributes attributes) {

        for (Form.Attributes.FormAttributes attribute : attributes.mFormAttributes) {
            FormAttributeEntity formAttributeEntity = new FormAttributeEntity();
            formAttributeEntity._id = attribute.id;
            formAttributeEntity.setFormId(formId);
            formAttributeEntity
                    .setInput(FormAttributeEntity.Input.valueOf(attribute.mInput.name()));
            formAttributeEntity.setType(FormAttributeEntity.Type.valueOf(attribute.mType.name()));
            formAttributeEntity.setKey(attribute.mKey);
            formAttributeEntity.setLabel(attribute.mLabel);
            formAttributeEntity.setOptions(attribute.mOptions);
            formAttributeEntity.setPriority(attribute.mPriority);
            formAttributeEntity.setRequired(attribute.mRequired);
            mFormAttributeEntities.add(formAttributeEntity);
        }
    }

    private List<FormAttributeEntity> getFormAttributeEntities() {
        return mFormAttributeEntities;
    }

    private static class Form {

        @SerializedName("name")
        private String mName;

        @SerializedName("description")
        private String mDescription;

        @SerializedName("disabled")
        private boolean mDisabled;

        @SerializedName("created")
        private Date mCreated;

        @SerializedName("updated")
        private Date mUpdated;

        @SerializedName("attributes")
        private Attributes mAttributes;

        @SerializedName("id")
        private Long id;

        private static class Attributes {

            @SerializedName("results")
            private List<FormAttributes> mFormAttributes;

            private static class FormAttributes {

                @SerializedName("id")
                private Long id;

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

                private List<String> mOptions;

                private enum Input {

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

                private enum Type {

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
            }
        }
    }
}
