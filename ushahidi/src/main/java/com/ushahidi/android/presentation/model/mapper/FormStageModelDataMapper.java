package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.domain.entity.FormStage;
import com.ushahidi.android.presentation.model.FormStageModel;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public FormStageModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link FormStageModel} to {@link FormStage}
     *
     * @param formStageModel The form entity to be mapped
     * @return The mapped form entity
     */
    @Nullable
    public FormStage map(FormStageModel formStageModel) {
        FormStage formStage = null;
        if (formStageModel != null) {
            formStage = new FormStage();
            formStage._id = formStageModel._id;
            formStage.setPriority(formStageModel.getPriority());
            formStage.setDeploymentId(formStageModel.getDeploymentId());
            formStage.setFormId(formStageModel.getFormId());
            formStage.setRequired(formStageModel.getRequired());
            formStage.setLabel(formStageModel.getLabel());
        }
        return formStage;
    }

    /**
     * Maps {@link FormStage} to {@link FormStageModel}
     *
     * @param formStage The form to be mapped
     * @return The mapped form
     */
    @Nullable
    public FormStageModel map(FormStage formStage) {
        FormStageModel formStageModel = null;
        if (formStage != null) {
            formStageModel = new FormStageModel();
            formStageModel._id = formStage._id;
            formStageModel.setPriority(formStage.getPriority());
            formStageModel.setDeploymentId(formStage.getDeploymentId());
            formStageModel.setFormId(formStage.getFormId());
            formStageModel.setRequired(formStage.getRequired());
            formStageModel.setLabel(formStage.getLabel());
        }
        return formStageModel;
    }

    /**
     * Maps a list of {@link FormStageModel} to a list of {@link FormStage}
     *
     * @param formStageList The form entity list
     * @return The mapped form entity list
     */
    @Nullable
    public List<FormStageModel> map(List<FormStage> formStageList) {
        List<FormStageModel> formStageModelList = null;
        if (formStageList != null) {
            formStageModelList = new ArrayList<>();
            for (FormStage formStage : formStageList) {
                FormStageModel formStageModel = map(formStage);
                formStageModelList.add(formStageModel);
            }
        }
        return formStageModelList;
    }
}
