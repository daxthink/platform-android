package com.ushahidi.android.presentation.view.deployment;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.presenter.deployment.DeleteDeploymentPresenter;

/**
 * View that faciliates communication between {@link DeleteDeploymentPresenter}
 * and activity views
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface DeleteDeploymentView extends LoadDataView {

    void onDeploymentDeleted();
}
