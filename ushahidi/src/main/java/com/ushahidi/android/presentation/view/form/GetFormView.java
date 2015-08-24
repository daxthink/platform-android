package com.ushahidi.android.presentation.view.form;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.FormModel;

/**
 * Shows form details for
 *
 * @author Henry Addo
 */
public interface GetFormView extends LoadDataView {

    /**
     * Render a form in the UI.
     *
     * @param formModel The {@link FormModel} that will be shown.
     */
    void renderFormList(FormModel formModel);
}
