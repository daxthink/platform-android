package com.ushahidi.android.presentation.di.components.post;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.di.components.AppActivityComponent;
import com.ushahidi.android.presentation.di.modules.post.GetPostModule;
import com.ushahidi.android.presentation.presenter.post.DetailPostPresenter;
import com.ushahidi.android.presentation.view.ui.activity.DetailPostActivity;
import com.ushahidi.android.presentation.view.ui.fragment.DetailPostFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components that injects {@link DetailPostFragment} and the
 * host activity {@link DetailPostActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, GetPostModule.class})
public interface GetPostComponent extends AppActivityComponent {

    /**
     * Injects {@link DetailPostActivity}
     *
     * @param detailPostActivity The post activity
     */
    void inject(DetailPostActivity detailPostActivity);

    /**
     * Injects {@link DetailPostFragment}
     *
     * @param getPostFragment Get the post fragment
     */
    void inject(DetailPostFragment getPostFragment);

    /**
     * Provides {@link DetailPostPresenter} to the sub-graph
     *
     * @return The detail post presenter
     */
    DetailPostPresenter detailPostPresenter();
}
