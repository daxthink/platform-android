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

package com.ushahidi.android.data.exception;

/**
 * Exception thrown by {@link com.ushahidi.android.data.database.GeoJsonDatabaseHelper} when a
 * {@link com.ushahidi.android.data.entity.GeoJsonEntity} can't be added to the database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddGeoJsonException extends Exception {

    /**
     * Default constructor
     */
    public AddGeoJsonException() {
        super();
    }

    /**
     * Initialize the exception with a custom message
     *
     * @param message The message be shown when the exception is thrown
     */
    public AddGeoJsonException(final String message) {
        super(message);
    }

    /**
     * Initialize the exception with a custom message and the cause of the exception
     *
     * @param message The message to be shown when the exception is thrown
     * @param cause   The cause of the exception
     */
    public AddGeoJsonException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Initialize the exception with a the cause of the exception
     *
     * @param cause The cause of the exception
     */
    public AddGeoJsonException(final Throwable cause) {
        super(cause);
    }
}
