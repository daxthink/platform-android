package com.ushahidi.android.data.repository.datasource.formattribute;

import com.ushahidi.android.data.database.FormAttributeDatabaseHelper;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeDataSourceFactory {

    private final FormAttributeDatabaseHelper mFormAttributeDatabaseHelper;

    /**
     * Default constructor that constructs {@link FormAttributeDataSourceFactory}
     *
     * @param formAttributeDatabaseHelper The form database helper
     */
    @Inject
    FormAttributeDataSourceFactory(
            @NonNull FormAttributeDatabaseHelper formAttributeDatabaseHelper) {
        mFormAttributeDatabaseHelper = formAttributeDatabaseHelper;
    }

    /**
     * Creates {@link FormAttributeDatabaseDataSource}
     *
     * @return The form database source
     */
    public FormAttributeDataSource createDatabaseDataSource() {
        return new FormAttributeDatabaseDataSource(mFormAttributeDatabaseHelper);
    }
}
