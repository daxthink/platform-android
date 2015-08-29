/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation;

import com.addhen.android.raiburari.presentation.BaseApplication;
import com.ushahidi.android.presentation.di.component.AppComponent;
import com.ushahidi.android.presentation.state.RxEventBus;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UshahidiApplication extends BaseApplication {

    private static RxEventBus mRxEventBus;

    private AppComponent mAppComponent;

    /**
     * Singleton for {@link RxEventBus}
     *
     * @return The RxEvent bus
     */
    public static RxEventBus getRxEventBusInstance() {
        if (mRxEventBus == null) {
            mRxEventBus = new RxEventBus();
        }
        return mRxEventBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = AppComponent.Initializer.init(this);
        // This is causing all test to fail. Going to investigate later
        //Iconify.with(new FontAwesomeModule());
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
