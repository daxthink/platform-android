package com.ushahidi.android.data.repository.datasource.formstage;

import com.ushahidi.android.data.api.FormStageApi;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.database.FormStageDatabaseHelper;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageDataSourceFactory {

    private final FormStageDatabaseHelper mFormStageDatabaseHelper;

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor that constructs {@link FormStageDataSourceFactory}
     *
     * @param formStageDatabaseHelper The form database helper
     */
    @Inject
    FormStageDataSourceFactory(
            @NonNull FormStageDatabaseHelper formStageDatabaseHelper,
            @NonNull UshAccessTokenManager ushAccessTokenManager) {
        mFormStageDatabaseHelper = formStageDatabaseHelper;
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Creates {@link FormStageDatabaseDataSource}
     *
     * @return The form database source
     */
    public FormStageDataSource createDatabaseDataSource() {
        return new FormStageDatabaseDataSource(mFormStageDatabaseHelper);
    }

    public FormStageDataSource createApiDataSource() {
        FormStageApi formStageApi = new FormStageApi(mUshAccessTokenManager);
        return new FormStageApiDataSource(formStageApi, mFormStageDatabaseHelper);
    }
}
