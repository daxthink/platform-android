package com.ushahidi.android.presentation.di.components.post;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.di.components.AppActivityComponent;
import com.ushahidi.android.presentation.di.modules.post.AddPostModule;
import com.ushahidi.android.presentation.presenter.post.AddPostPresenter;
import com.ushahidi.android.presentation.view.ui.activity.AddPostActivity;
import com.ushahidi.android.presentation.view.ui.activity.DetailPostActivity;
import com.ushahidi.android.presentation.view.ui.fragment.AddPostFragment;
import com.ushahidi.android.presentation.view.ui.fragment.DetailPostFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components that injects {@link DetailPostFragment} and the
 * host activity {@link DetailPostActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, AddPostModule.class})
public interface AddPostComponent extends AppActivityComponent {

    /**
     * Injects {@link AddPostActivity}
     *
     * @param detailPostActivity The post activity
     */
    void inject(AddPostActivity detailPostActivity);

    /**
     * Injects {@link AddPostFragment}
     *
     * @param getPostFragment Get the post fragment
     */
    void inject(AddPostFragment getPostFragment);

    /**
     * Provides {@link AddPostPresenter} to the sub-graph
     *
     * @return The add post presenter
     */
    AddPostPresenter addPostPresenter();
}
