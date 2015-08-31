package com.ushahidi.android.presentation.view.form;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.FormModel;

import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface ListFormView extends LoadDataView {

    /**
     * Render a form list in the UI.
     *
     * @param formModel The collection of {@link FormModel} that will be shown.
     */
    void renderFormList(List<FormModel> formModel);
}
