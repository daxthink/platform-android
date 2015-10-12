package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.domain.entity.FormAttribute;
import com.ushahidi.android.presentation.model.FormAttributeModel;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public FormAttributeModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link FormAttributeModel} to {@link FormAttribute}
     *
     * @param formAttributeModel The form entity to be mapped
     * @return The mapped form entity
     */
    @Nullable
    public FormAttribute map(FormAttributeModel formAttributeModel) {
        FormAttribute formAttribute = null;
        if (formAttributeModel != null) {
            formAttribute = new FormAttribute();
            formAttribute._id = formAttributeModel._id;
            formAttribute.setCardinality(formAttributeModel.getCardinality());
            formAttribute.setPriority(formAttributeModel.getPriority());
            formAttribute.setKey(formAttributeModel.getKey());
            formAttribute.setDeploymentId(formAttributeModel.getDeploymentId());
            formAttribute.setOptions(formAttributeModel.getOptions());
            formAttribute.setFormId(formAttributeModel.getFormId());
            formAttribute.setRequired(formAttributeModel.getRequired());
            formAttribute
                    .setInput(FormAttribute.Input.valueOf(formAttributeModel.getInput().name()));
            formAttribute.setType(FormAttribute.Type.valueOf(formAttributeModel.getType().name()));
            formAttribute.setLabel(formAttributeModel.getLabel());
            formAttribute.setFormStageId(formAttributeModel.getFormStageId());
        }
        return formAttribute;
    }

    /**
     * Maps {@link FormAttribute} to {@link FormAttributeModel}
     *
     * @param formAttribute The form to be mapped
     * @return The mapped form
     */
    @Nullable
    public FormAttributeModel map(FormAttribute formAttribute) {
        FormAttributeModel formAttributeModel = null;
        if (formAttribute != null) {
            formAttributeModel = new FormAttributeModel();
            formAttributeModel._id = formAttribute._id;
            formAttributeModel.setCardinality(formAttribute.getCardinality());
            formAttributeModel.setPriority(formAttribute.getPriority());
            formAttributeModel.setKey(formAttribute.getKey());
            formAttributeModel.setDeploymentId(formAttribute.getDeploymentId());
            formAttributeModel.setOptions(formAttribute.getOptions());
            formAttributeModel.setFormId(formAttribute.getFormId());
            formAttributeModel.setRequired(formAttribute.getRequired());
            formAttributeModel
                    .setInput(FormAttributeModel.Input.valueOf(formAttribute.getInput().name()));
            formAttributeModel
                    .setType(FormAttributeModel.Type.valueOf(formAttribute.getType().name()));
            formAttributeModel.setLabel(formAttribute.getLabel());
            formAttributeModel.setFormStageId(formAttribute.getFormStageId());
        }
        return formAttributeModel;
    }

    /**
     * Maps a list of {@link FormAttributeModel} to a list of {@link FormAttribute}
     *
     * @param formAttributeList The form entity list
     * @return The mapped form entity list
     */
    @Nullable
    public List<FormAttributeModel> map(List<FormAttribute> formAttributeList) {
        List<FormAttributeModel> formAttributeModelList = null;
        if (formAttributeList != null) {
            formAttributeModelList = new ArrayList<>();
            for (FormAttribute formAttribute : formAttributeList) {
                FormAttributeModel formAttributeModel = map(formAttribute);
                formAttributeModelList.add(formAttributeModel);
            }
        }
        return formAttributeModelList;
    }
}
