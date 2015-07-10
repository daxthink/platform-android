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

import java.util.Map;

/**
 * Interface to provide auth management
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface SessionManager<T extends Session> {

    /**
     * @return the active session, restoring saved session if available
     */
    T getActiveSession();

    /**
     * Sets the active session.
     */
    void setActiveSession(T session);

    /**
     * Clears the active session.
     */
    void clearActiveSession();

    /**
     * @return the session associated with the id.
     */
    T getSession(long id, long deploymentId);

    /**
     * Sets the session to associate with the id. If there is no active session, this session also
     * becomes the active session.
     */
    void setSession(T session);

    /**
     * Clears the session associated with the id.
     */
    void clearSession(long id, long deploymentId);

    /**
     * @return the session map containing all managed sessions
     */
    Map<String, T> getSessionMap();
}
