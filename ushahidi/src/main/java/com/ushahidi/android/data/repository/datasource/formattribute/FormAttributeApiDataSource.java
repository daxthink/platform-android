package com.ushahidi.android.data.repository.datasource.formattribute;

import com.ushahidi.android.data.api.FormAttributeApi;
import com.ushahidi.android.data.database.FormAttributeDatabaseHelper;
import com.ushahidi.android.data.entity.FormAttributeEntity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeApiDataSource implements FormAttributeDataSource {

    private final FormAttributeDatabaseHelper mFormAttributeDatabaseHelper;

    private FormAttributeApi mFormAttributeApi;

    public FormAttributeApiDataSource(@NonNull FormAttributeApi formAttributeApi,
            @NonNull FormAttributeDatabaseHelper formAttributeDatabaseHelper) {
        mFormAttributeApi = formAttributeApi;
        mFormAttributeDatabaseHelper = formAttributeDatabaseHelper;
    }

    @Override
    public Observable<List<FormAttributeEntity>> getFormAttributes(Long deploymentId, Long formId) {
        return mFormAttributeApi.getFormAttributes(formId).doOnNext(
                formsAttributes -> mFormAttributeDatabaseHelper
                        .putFormAttributeEntity(
                                setFormAttributeEntity(formsAttributes, deploymentId)));

    }


    private List<FormAttributeEntity> setFormAttributeEntity(
            List<FormAttributeEntity> formAttributeEntityList,
            Long deploymentId) {
        List<FormAttributeEntity> formAttributeList = new ArrayList<>();
        for (FormAttributeEntity formAttributeEntity : formAttributeEntityList) {
            formAttributeEntity.setDeploymentId(deploymentId);
            formAttributeList.add(formAttributeEntity);
        }
        return formAttributeList;
    }
}
