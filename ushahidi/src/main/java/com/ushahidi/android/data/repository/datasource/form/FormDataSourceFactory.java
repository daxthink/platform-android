package com.ushahidi.android.data.repository.datasource.form;

import com.ushahidi.android.data.database.FormDatabaseHelper;
import com.ushahidi.android.data.database.FormDatabaseHelper;
import com.ushahidi.android.data.repository.datasource.form.FormDataSource;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormDataSourceFactory {
    private final FormDatabaseHelper mFormDatabaseHelper;

    /**
     * Default constructor that constructs {@link FormDataSourceFactory}
     *
     * @param formDatabaseHelper The form database helper
     */
    @Inject
    FormDataSourceFactory(@NonNull FormDatabaseHelper formDatabaseHelper) {
        mFormDatabaseHelper = formDatabaseHelper;
    }

    /**
     * Creates {@link FormDatabaseDataSource}
     *
     * @return The form database source
     */
    public FormDataSource createDatabaseDataSource() {
        return new FormDatabaseDataSource(mFormDatabaseHelper);
    }
}
