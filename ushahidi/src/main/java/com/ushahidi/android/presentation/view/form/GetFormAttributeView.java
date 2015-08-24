package com.ushahidi.android.presentation.view.form;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.FormAttributeModel;

/**
 * Shows form details for
 *
 * @author Henry Addo
 */
public interface GetFormAttributeView extends LoadDataView {

    /**
     * Render a form in the UI.
     *
     * @param formModel The {@link FormAttributeModel} that will be shown.
     */
    void renderFormAttribute(FormAttributeModel formModel);
}
