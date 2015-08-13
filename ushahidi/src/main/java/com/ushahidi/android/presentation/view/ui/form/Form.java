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

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Sets the widgets and renders them
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Form {

    protected ViewGroup mContainer;

    protected Context mContext;

    private List<FormWidget> mFormWidgets;

    /**
     * Default constructor that constructs {@link Form}
     *
     * @param context   The calling context
     * @param container The layout container
     */
    public Form(Context context, ViewGroup container) {
        mContainer = container;
        mContext = context;
        mFormWidgets = new ArrayList<>();
    }

    /**
     * Gets the form widgets
     *
     * @return The list of form widgets
     */
    public List<FormWidget> getFormWidgets() {
        return mFormWidgets;
    }

    /**
     * Renders one or more form widgets.
     *
     * @param attribute1 The first form attribute that makes up the form widget
     * @param attributes The rest of the form attribute that makes up the form widget
     * @return The container in which the form widgets have been added
     */
    public ViewGroup renderForm(Attribute attribute1, Attribute attribute2,
            Attribute... attributes) {
        generateFormWidget(attribute1);
        generateFormWidget(attribute2);
        for (Attribute attribute : attributes) {
            generateFormWidget(attribute);
        }
        return getContainer();
    }

    /**
     * Validates the form widget's values
     *
     * @return The validation status. True for the field being valid
     */
    public boolean validate() {
        for (FormWidget formWidget : getFormWidgets()) {
            if (!formWidget.validate()) {
                return false;
            }
        }
        return true;
    }

    private void generateFormWidget(Attribute attribute) {
        FormWidget formWidget = null;
        Attribute.Input input = attribute.getInput();
        if (Attribute.Input.TEXT.equals(input)) {
            formWidget = new EditFormWidget(mContext, attribute.getKey(), attribute.getLabel());
            formWidget.setPriority(attribute.getPriority());

        } else if (Attribute.Input.SELECT.equals(input)) {
            formWidget = new SelectFormWidget(mContext, attribute.getKey(), attribute.getLabel(),
                    attribute.getOptions());
            formWidget.setPriority(attribute.getPriority());
        }

        mFormWidgets.add(formWidget);

    }

    private ViewGroup getContainer() {
        Collections.sort(mFormWidgets, new Priority());
        for (FormWidget formWidget : mFormWidgets) {
            mContainer.addView(formWidget.getLayout());
        }

        return mContainer;
    }

    private class Priority implements Comparator<FormWidget> {

        public int compare(FormWidget item, FormWidget widget) {
            return item.getPriority() > widget.getPriority() ? 1 : -1;
        }
    }

}
