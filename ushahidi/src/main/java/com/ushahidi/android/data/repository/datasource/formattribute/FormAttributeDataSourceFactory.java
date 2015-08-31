package com.ushahidi.android.data.repository.datasource.formattribute;

import com.ushahidi.android.data.api.FormAttributeApi;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.database.FormAttributeDatabaseHelper;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeDataSourceFactory {

    private final FormAttributeDatabaseHelper mFormAttributeDatabaseHelper;

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor that constructs {@link FormAttributeDataSourceFactory}
     *
     * @param formAttributeDatabaseHelper The form database helper
     */
    @Inject
    FormAttributeDataSourceFactory(
            @NonNull FormAttributeDatabaseHelper formAttributeDatabaseHelper,
            @NonNull UshAccessTokenManager ushAccessTokenManager) {
        mFormAttributeDatabaseHelper = formAttributeDatabaseHelper;
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Creates {@link FormAttributeDatabaseDataSource}
     *
     * @return The form database source
     */
    public FormAttributeDataSource createDatabaseDataSource() {
        return new FormAttributeDatabaseDataSource(mFormAttributeDatabaseHelper);
    }

    public FormAttributeDataSource createApiDataSource() {
        FormAttributeApi formAttributeApi = new FormAttributeApi(mUshAccessTokenManager);
        return new FormAttributeApiDataSource(formAttributeApi, mFormAttributeDatabaseHelper);
    }
}
