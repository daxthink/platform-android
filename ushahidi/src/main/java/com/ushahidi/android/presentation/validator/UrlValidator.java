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

package com.ushahidi.android.presentation.validator;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates a given URL
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UrlValidator implements Validator {

    @Override
    public boolean isValid(CharSequence text) {
        return valid(text.toString());
    }

    private boolean valid(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        final Pattern pattern = Patterns.WEB_URL;
        final Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
