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

package com.ushahidi.android.data.api.account;

import com.google.gson.annotations.SerializedName;

/**
 * Platform Session
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Session {

    private long id;

    @SerializedName("deployment_id")
    private long deploymentId;

    public Session(long id, long deploymentId) {
        this.id = id;
        this.deploymentId = deploymentId;
    }

    public long getDeploymentId() {
        return deploymentId;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Session session = (Session) o;

        if (id != session.id) {
            return false;
        }

        if (deploymentId != session.deploymentId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != 0 ? (int) id : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
