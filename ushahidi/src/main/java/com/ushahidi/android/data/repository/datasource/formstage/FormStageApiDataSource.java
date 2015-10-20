package com.ushahidi.android.data.repository.datasource.formstage;

import com.ushahidi.android.data.api.FormStageApi;
import com.ushahidi.android.data.database.FormStageDatabaseHelper;
import com.ushahidi.android.data.entity.FormStageEntity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageApiDataSource implements FormStageDataSource {

    private final FormStageDatabaseHelper mFormStageDatabaseHelper;

    private FormStageApi mFormStageApi;

    public FormStageApiDataSource(@NonNull FormStageApi formStageApi,
            @NonNull FormStageDatabaseHelper formStageDatabaseHelper) {
        mFormStageApi = formStageApi;
        mFormStageDatabaseHelper = formStageDatabaseHelper;
    }

    @Override
    public Observable<List<FormStageEntity>> getFormStages(Long deploymentId, Long formId) {
        return mFormStageApi.getFormStages(formId).doOnNext(
                formsStages -> mFormStageDatabaseHelper
                        .putFormStageEntity(
                                setFormStageEntity(formsStages, deploymentId)));

    }


    private List<FormStageEntity> setFormStageEntity(
            List<FormStageEntity> formStageEntityList,
            Long deploymentId) {
        List<FormStageEntity> formStageList = new ArrayList<>();
        for (FormStageEntity formStageEntity : formStageEntityList) {
            formStageEntity.setDeploymentId(deploymentId);
            formStageList.add(formStageEntity);
        }
        return formStageList;
    }
}
