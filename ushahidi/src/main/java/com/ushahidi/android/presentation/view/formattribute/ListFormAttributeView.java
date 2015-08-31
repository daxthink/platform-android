package com.ushahidi.android.presentation.view.formattribute;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.FormAttributeModel;

import java.util.List;

/**
 * Shows form details for
 *
 * @author Henry Addo
 */
public interface ListFormAttributeView extends LoadDataView {

    /**
     * Render a form in the UI.
     *
     * @param formModels The list of {@link FormAttributeModel} that will be shown.
     */
    void renderFormAttribute(List<FormAttributeModel> formModels);
}
