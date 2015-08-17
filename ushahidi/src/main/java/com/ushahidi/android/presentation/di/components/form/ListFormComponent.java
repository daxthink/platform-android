package com.ushahidi.android.presentation.di.components.form;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.di.components.AppActivityComponent;
import com.ushahidi.android.presentation.di.modules.form.ListFormModule;
import com.ushahidi.android.presentation.presenter.form.ListFormPresenter;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;

import dagger.Component;

/**
 * An {@link ActivityScope} scope components that injects post activity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class,
        ListFormModule.class})
public interface ListFormComponent extends AppActivityComponent {

    /**
     * Injects dependencies into {@link PostActivity}
     *
     * @param postActivity The activity to inject dependencies into
     */
    void inject(PostActivity postActivity);

    /**
     * Provides {@link ListFormPresenter} to sub-graphs
     *
     * @return The form presenter
     */
    ListFormPresenter listFormPresenter();
}
