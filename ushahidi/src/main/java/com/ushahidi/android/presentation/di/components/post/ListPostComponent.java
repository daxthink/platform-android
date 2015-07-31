/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.di.components.post;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.di.components.AppActivityComponent;
import com.ushahidi.android.presentation.di.modules.post.ListPostModule;
import com.ushahidi.android.presentation.presenter.post.ListPostPresenter;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;
import com.ushahidi.android.presentation.view.ui.fragment.ListPostFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components that injects {@link ListPostFragment} and the
 * host activity {@link PostActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class,
        ListPostModule.class})
public interface ListPostComponent extends AppActivityComponent {

    /**
     * Injects {@link PostActivity}
     *
     * @param listPostActivity The post activity
     */
    void inject(PostActivity listPostActivity);

    /**
     * Injects {@link ListPostFragment}
     *
     * @param listPostFragment The list post fragment
     */
    void inject(ListPostFragment listPostFragment);

    /**
     * Provides {@link ListPostPresenter} to the sub-graph
     *
     * @return The list post presenter
     */
    ListPostPresenter listPostPresenter();
}
