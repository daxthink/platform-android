package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.repository.datasource.form.FormDataSourceFactory;
import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormRepository;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormDataRepository implements FormRepository {

    private final FormDataSourceFactory mFormDataSourceFactory;

    public FormDataRepository(@NonNull FormDataSourceFactory formDataSourceFactory) {
        mFormDataSourceFactory = formDataSourceFactory;
    }

    @Override
    public Observable<List<Form>> getForms(Long deploymentId, From from) {
        return null;
    }

    @Override
    public Observable<Long> putForm(Form form) {
        return null;
    }
}
