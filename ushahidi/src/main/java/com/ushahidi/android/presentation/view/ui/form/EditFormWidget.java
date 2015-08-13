/*
 * Copyright (c) 2014 Ushahidi.
 *
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

package com.ushahidi.android.presentation.view.ui.form;

import com.ushahidi.android.R;
import com.ushahidi.android.presentation.view.ui.form.validator.RequiredValidator;
import com.ushahidi.android.presentation.view.ui.form.validator.Validator;

import android.content.Context;
import android.widget.EditText;

/**
 * EditText widget for inputting basic texts
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class EditFormWidget extends FormWidget {

    private EditText mInput;

    private Context mContext;

    /**
     * Default constructor that constructs {@link EditFormWidget}
     *
     * @param context The calling context
     * @param name    The name of the form
     * @param label   The label
     */
    public EditFormWidget(Context context, String name, String label) {
        super(context, name, label);
        mContext = context;

        mInput = new EditText(context);
        mInput.setLayoutParams(DEFAULT_LAYOUT_PARAMS);
        mLayout.addView(mInput);
    }

    @Override
    public String getValue() {
        return mInput.getText().toString();
    }

    @Override
    public void setValue(String value) {
        mInput.setText(value);
    }

    @Override
    public boolean validate() {
        // Validate if required is set
        if (isRequired()) {
            final String requiredMessage = mContext.getString(R.string.required_field_error);
            RequiredValidator requiredValidator = new RequiredValidator(requiredMessage);
            if (!requiredValidator.isValid(this)) {
                mInput.setError(requiredValidator.getErrorMessage());
                return false;
            }
        }

        for (Validator validator : mValidators) {
            if (!validator.isValid(this)) {
                mInput.setError(validator.getErrorMessage());
                return false;
            }
        }

        return true;
    }

}
