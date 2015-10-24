package com.ushahidi.android.data.repository.datasource.formstage;

import com.ushahidi.android.data.database.FormStageDatabaseHelper;
import com.ushahidi.android.data.entity.FormStageEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageDatabaseDataSource implements FormStageDataSource {

    private final FormStageDatabaseHelper mFormStageDatabaseHelper;

    public FormStageDatabaseDataSource(
            @NonNull FormStageDatabaseHelper formStageDatabaseHelper) {
        mFormStageDatabaseHelper = formStageDatabaseHelper;
    }

    @Override
    public Observable<List<FormStageEntity>> getFormStages(Long deploymentId, Long formId) {
        return mFormStageDatabaseHelper.getFormStageEntity(deploymentId, formId);
    }
}
