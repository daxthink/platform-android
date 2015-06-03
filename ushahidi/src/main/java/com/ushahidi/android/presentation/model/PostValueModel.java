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

package com.ushahidi.android.presentation.model;

import com.addhen.android.raiburari.presentation.model.Model;

/**
 * PostValue model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostValueModel extends Model {

    private String mValues;

    private long mDeploymentId;

    public String getValues() {
        return mValues;
    }

    public void setValues(String values) {
        mValues = values;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "PostValue{" +
                "mValues='" + mValues + '\'' +
                ", mDeploymentId=" + mDeploymentId +
                '}';
    }
}
