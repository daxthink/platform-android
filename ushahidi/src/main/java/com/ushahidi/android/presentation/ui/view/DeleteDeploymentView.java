package com.ushahidi.android.presentation.ui.view;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;

/**
 * View that faciliates communication between {@link com.ushahidi.android.presentation.presenter.DeleteDeploymentPresenter}
 * and activity views
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface DeleteDeploymentView extends LoadDataView {

    void onDeploymentDeleted();
}
