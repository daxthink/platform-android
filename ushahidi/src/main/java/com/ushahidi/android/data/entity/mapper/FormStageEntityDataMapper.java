package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.FormStageEntity;
import com.ushahidi.android.domain.entity.FormStage;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public FormStageEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link FormStageEntity} to {@link FormStage}
     *
     * @param formStageEntity The form entity to be mapped
     * @return The mapped form entity
     */
    @Nullable
    public FormStage map(FormStageEntity formStageEntity) {
        FormStage formStage = null;
        if (formStageEntity != null) {
            formStage = new FormStage();
            formStage._id = formStageEntity._id;
            formStage.setPriority(formStageEntity.getPriority());
            formStage.setDeploymentId(formStageEntity.getDeploymentId());
            formStage.setFormId(formStageEntity.getFormId());
            formStage.setRequired(formStageEntity.getRequired());
            formStage.setLabel(formStageEntity.getLabel());
        }
        return formStage;
    }

    /**
     * Maps {@link FormStage} to {@link FormStageEntity}
     *
     * @param formStage The form to be mapped
     * @return The mapped form
     */
    @Nullable
    public FormStageEntity map(FormStage formStage) {
        FormStageEntity formStageEntity = null;
        if (formStage != null) {
            formStageEntity = new FormStageEntity();
            formStageEntity._id = formStage._id;
            formStageEntity.setPriority(formStage.getPriority());
            formStageEntity.setDeploymentId(formStage.getDeploymentId());
            formStageEntity.setFormId(formStage.getFormId());
            formStageEntity.setRequired(formStage.getRequired());
            formStageEntity.setLabel(formStage.getLabel());
        }
        return formStageEntity;
    }

    /**
     * Maps a list of {@link FormStageEntity} to a list of {@link FormStage}
     *
     * @param formStageEntityList The form entity list
     * @return The mapped form entity list
     */
    @Nullable
    public List<FormStage> map(List<FormStageEntity> formStageEntityList) {
        List<FormStage> formStageList = null;
        if (formStageEntityList != null) {
            formStageList = new ArrayList<>();
            for (FormStageEntity formStageEntity : formStageEntityList) {
                FormStage formStage = map(formStageEntity);
                formStageList.add(formStage);
            }
        }
        return formStageList;
    }
}
