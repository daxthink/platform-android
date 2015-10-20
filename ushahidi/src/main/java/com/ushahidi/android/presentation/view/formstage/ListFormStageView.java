package com.ushahidi.android.presentation.view.formstage;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.FormStageModel;

import java.util.List;

/**
 * Shows form details for
 *
 * @author Henry Addo
 */
public interface ListFormStageView extends LoadDataView {

    /**
     * Render a form in the UI.
     *
     * @param formModels The list of {@link FormStageModel} that will be shown.
     */
    void renderFormStage(List<FormStageModel> formModels);
}
