package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.domain.entity.Form;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Transforms data between domain layer and presentation layer
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public FormEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link FormEntity} to {@link Form}
     *
     * @param formEntity The form entity to be mapped
     * @return The mapped form entity
     */
    public Form map(FormEntity formEntity) {
        Form form = null;
        if (formEntity != null) {
            form = new Form();
            form._id = formEntity._id;
            form.setCreated(formEntity.getCreated());
            form.setUpdated(formEntity.getUpdated());
            form.setDescription(formEntity.getDescription());
            form.setName(formEntity.getName());
            form.setDeploymentId(formEntity.getDeploymentId());
            form.setDisabled(formEntity.isDisabled());
        }
        return form;
    }

    /**
     * Maps {@link Form} to {@link FormEntity}
     *
     * @param form The form to be mapped
     * @return The mapped form
     */
    public FormEntity map(Form form) {
        FormEntity formEntity = null;
        if (form != null) {
            formEntity = new FormEntity();
            formEntity._id = form._id;
            formEntity.setCreated(form.getCreated());
            formEntity.setUpdated(form.getUpdated());
            formEntity.setDeploymentId(form.getDeploymentId());
            formEntity.setName(form.getName());
            formEntity.setDescription(form.getDescription());
            formEntity.setDisabled(form.isDisabled());
        }
        return formEntity;
    }

    /**
     * Maps a list of {@link FormEntity} to a list of {@link Form}
     *
     * @param formEntityList The form entity list
     * @return The mapped form entity list
     */
    public List<Form> map(List<FormEntity> formEntityList) {
        List<Form> formList = null;
        if (formEntityList != null) {
            formList = new ArrayList<>();
            for (FormEntity formEntity : formEntityList) {
                Form form = map(formEntity);
                formList.add(form);
            }
        }
        return formList;
    }
}
