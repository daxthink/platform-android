package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.presentation.model.FormModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Transforms data between domain layer and presentation layer
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public FormModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link Form} to {@link FormModel}
     *
     * @param form The form  to be mapped
     * @return The mapped form model
     */
    public FormModel map(Form form) {
        FormModel formModel = null;
        if (form != null) {
            formModel = new FormModel();
            formModel._id = form._id;
            formModel.setCreated(form.getCreated());
            formModel.setUpdated(form.getUpdated());
            formModel.setDescription(form.getDescription());
            formModel.setName(form.getName());
            formModel.setDeploymentId(form.getDeploymentId());
            formModel.setDisabled(form.isDisabled());
        }
        return formModel;
    }

    /**
     * Maps {@link FormModel} to {@link Form}
     *
     * @param formModel The form to be mapped
     * @return The mapped form
     */
    public Form map(FormModel formModel) {
        Form form = null;
        if (formModel != null) {
            form = new Form();
            form._id = formModel._id;
            form.setCreated(formModel.getCreated());
            form.setUpdated(formModel.getUpdated());
            form.setDeploymentId(formModel.getDeploymentId());
            form.setName(formModel.getName());
            form.setDescription(formModel.getDescription());
            form.setDisabled(formModel.isDisabled());
        }
        return form;
    }

    /**
     * Maps a list of {@link Form} to a list of {@link FormModel}
     *
     * @param formList The form entity list
     * @return The mapped form entity list
     */
    public List<FormModel> map(List<Form> formList) {
        List<FormModel> formModelList = null;
        if (formList != null) {
            formModelList = new ArrayList<>();
            for (Form form : formList) {
                FormModel formModel = map(form);
                formModelList.add(formModel);
            }
        }
        return formModelList;
    }
}
