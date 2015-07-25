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

package com.ushahidi.android.presentation.util;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.WindowManager;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Generic Utilities for the application
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class Utility {

    private Utility() {
        // No instance
    }

    /**
     * Checks if collection is empty
     *
     * @param collection The collection to be checked
     * @return True if empty otherwise false
     */
    public static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * The collection size
     *
     * @param collection The collection to check it's size
     * @return The size of the collection
     */
    public static int collectionSize(Collection<?> collection) {
        return collection != null ? collection.size() : 0;
    }

    /**
     * Capitalize the first letter
     *
     * @param text The letter to be capitalized
     * @return The capitalized letter
     */
    public static String capitalizeFirstLetter(String text) {
        if (text.length() == 0) {
            return text;
        }

        return text.substring(0, 1).toUpperCase(Locale.getDefault())
                + text.substring(1).toLowerCase(Locale.getDefault());

    }

    /**
     * Validate a string to make sure it's in the appropriate hex color format
     *
     * @param hexColor The string containing the hex color
     * @return True if it's in the appropriate format otherwise false
     */
    public static boolean validateHexColor(String hexColor) {
        if (TextUtils.isEmpty(hexColor)) {
            return false;
        }
        final String hexPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        return Pattern.compile(hexPattern).matcher(hexColor).matches();
    }

    /**
     * To get the screen height
     *
     * @param context The calling context
     * @return The screen height
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * Formats a past {@link Date} into a relative time display
     *
     * @param pastTime The past date
     * @return The formatted the data
     */
    public static String getRelativeTimeDisplay(Date pastTime) {
        long timeNow = System.currentTimeMillis();
        return DateUtils.getRelativeTimeSpanString(pastTime.getTime(), timeNow,
                DateUtils.MINUTE_IN_MILLIS)
                .toString();
    }
}
