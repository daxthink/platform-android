package com.ushahidi.android.data.repository.datasource.form;

import com.ushahidi.android.data.database.FormDatabaseHelper;
import com.ushahidi.android.data.entity.FormEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormDatabaseDataSource implements FormDataSource {

    private final FormDatabaseHelper mFormDatabaseHelper;

    public FormDatabaseDataSource(@NonNull FormDatabaseHelper formDatabaseHelper) {
        mFormDatabaseHelper = formDatabaseHelper;
    }

    @Override
    public Observable<List<FormEntity>> getForms(Long deploymentId) {
        return mFormDatabaseHelper.getForms(deploymentId);
    }

    @Override
    public Observable<Long> putForm(FormEntity formEntity) {
        return null;
    }
}
