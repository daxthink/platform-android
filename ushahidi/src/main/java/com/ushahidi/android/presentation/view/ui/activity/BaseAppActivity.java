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

package com.ushahidi.android.presentation.view.ui.activity;

import com.addhen.android.raiburari.presentation.ui.activity.BaseActivity;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.component.AppComponent;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class BaseAppActivity extends BaseActivity {

    /**
     * Default constructor
     *
     * @param layout The layout to be loaded
     * @param menu   The menu
     */
    public BaseAppActivity(@LayoutRes int layout, @MenuRes int menu) {
        super(layout, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    /**
     * Gets the Main Application component for dependency injection.
     *
     * @return {@link  com.ushahidi.android.presentation.di.component.AppComponent}
     */
    public AppComponent getAppComponent() {
        return ((UshahidiApplication) getApplication()).getAppComponent();
    }
}
