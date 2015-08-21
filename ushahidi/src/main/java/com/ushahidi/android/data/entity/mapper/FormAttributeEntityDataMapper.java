package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.domain.entity.FormAttribute;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public FormAttributeEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link FormEntity} to {@link Form}
     *
     * @param formAttributeEntity The form entity to be mapped
     * @return The mapped form entity
     */
    @Nullable
    public FormAttribute map(FormAttributeEntity formAttributeEntity) {
        FormAttribute formAttribute = null;
        if (formAttributeEntity != null) {
            formAttribute = new FormAttribute();
            formAttribute._id = formAttributeEntity._id;
            formAttribute.setCardinality(formAttributeEntity.getCardinality());
            formAttribute.setPriority(formAttributeEntity.getPriority());
            formAttribute.setKey(formAttributeEntity.getKey());
            formAttribute.setDeploymentId(formAttributeEntity.getDeploymentId());
            formAttribute.setOptions(formAttributeEntity.getOptions());
            formAttribute.setFormId(formAttributeEntity.getFormId());
            formAttribute.setRequired(formAttributeEntity.getRequired());
            formAttribute.setInput(FormAttribute.Input.valueOf(formAttribute.getInput().name()));
            formAttribute.setType(FormAttribute.Type.valueOf(formAttribute.getType().name()));
            formAttribute.setLabel(formAttributeEntity.getLabel());
        }
        return formAttribute;
    }

    /**
     * Maps {@link Form} to {@link FormEntity}
     *
     * @param formAttribute The form to be mapped
     * @return The mapped form
     */
    @Nullable
    public FormAttributeEntity map(FormAttribute formAttribute) {
        FormAttributeEntity formAttributeEntity = null;
        if (formAttribute != null) {
            formAttributeEntity = new FormAttributeEntity();
            formAttributeEntity._id = formAttribute._id;
            formAttributeEntity.setCardinality(formAttribute.getCardinality());
            formAttributeEntity.setPriority(formAttribute.getPriority());
            formAttributeEntity.setKey(formAttribute.getKey());
            formAttributeEntity.setDeploymentId(formAttribute.getDeploymentId());
            formAttributeEntity.setOptions(formAttribute.getOptions());
            formAttributeEntity.setFormId(formAttribute.getFormId());
            formAttributeEntity.setRequired(formAttribute.getRequired());
            formAttributeEntity
                    .setInput(FormAttributeEntity.Input.valueOf(formAttribute.getInput().name()));
            formAttributeEntity
                    .setType(FormAttributeEntity.Type.valueOf(formAttribute.getType().name()));
            formAttributeEntity.setLabel(formAttribute.getLabel());
        }
        return formAttributeEntity;
    }

    /**
     * Maps a list of {@link FormEntity} to a list of {@link Form}
     *
     * @param formAttributeEntityList The form entity list
     * @return The mapped form entity list
     */
    @Nullable
    public List<FormAttribute> map(List<FormAttributeEntity> formAttributeEntityList) {
        List<FormAttribute> formAttributeList = null;
        if (formAttributeEntityList != null) {
            formAttributeList = new ArrayList<>();
            for (FormAttributeEntity formAttributeEntity : formAttributeEntityList) {
                FormAttribute formAttribute = map(formAttributeEntity);
                formAttributeList.add(formAttribute);
            }
        }
        return formAttributeList;
    }
}
