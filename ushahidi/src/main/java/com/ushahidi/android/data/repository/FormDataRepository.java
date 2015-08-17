package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.FormEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.form.FormDataSource;
import com.ushahidi.android.data.repository.datasource.form.FormDataSourceFactory;
import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormRepository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class FormDataRepository implements FormRepository {

    private final FormDataSourceFactory mFormDataSourceFactory;

    private final FormEntityDataMapper mFormEntityDataMapper;

    /**
     * Default constructor that creates {@link FormDataRepository}
     *
     * @param formDataSourceFactory A factory to create the different data source implementations
     * @param formEntityDataMapper  The @{link FormEntityDataMapper}
     */
    @Inject
    public FormDataRepository(@NonNull FormDataSourceFactory formDataSourceFactory,
            FormEntityDataMapper formEntityDataMapper) {
        mFormDataSourceFactory = formDataSourceFactory;
        mFormEntityDataMapper = formEntityDataMapper;
    }

    @Override
    public Observable<List<Form>> getForms(Long deploymentId, From from) {
        FormDataSource formDataSource = null;
        if (from.equals(From.DATABASE)) {
            formDataSource = mFormDataSourceFactory.createDatabaseDataSource();
        }
        return formDataSource.getForms(deploymentId).map(mFormEntityDataMapper::map);
    }

    @Override
    public Observable<Long> putForm(Form form) {
        final FormDataSource formDataSource = mFormDataSourceFactory.createDatabaseDataSource();
        return formDataSource.putForm(mFormEntityDataMapper.map(form));
    }
}
