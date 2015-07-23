package com.ushahidi.android.presentation.view.deployment;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;

/**
 * View that facilitates communication between
 * {@link com.ushahidi.android.presentation.presenter.deployment.DeleteDeploymentPresenter}
 * and activity views
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface DeleteDeploymentView extends LoadDataView {

    /**
     * Shows that a deployment has been successfully deleted
     */
    void onDeploymentDeleted();
}
