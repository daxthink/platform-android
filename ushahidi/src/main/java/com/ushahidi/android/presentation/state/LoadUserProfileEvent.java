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

package com.ushahidi.android.presentation.state;

import com.ushahidi.android.presentation.model.UserProfileModel;

/**
 * Event class used by {@link RxEventBus} to trigger the app to load user profile
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LoadUserProfileEvent {

    private UserProfileModel mUserProfileModel;

    /**
     * Default constructor
     *
     * @param userProfileModel The user profile to be passed to the event listener
     */
    public LoadUserProfileEvent(UserProfileModel userProfileModel) {
        mUserProfileModel = userProfileModel;
    }

    public UserProfileModel getUserProfileModel() {
        return mUserProfileModel;
    }
}
