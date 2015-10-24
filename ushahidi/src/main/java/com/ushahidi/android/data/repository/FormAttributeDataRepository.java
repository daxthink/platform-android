package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.FormAttributeEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.formattribute.FormAttributeDataSource;
import com.ushahidi.android.data.repository.datasource.formattribute.FormAttributeDataSourceFactory;
import com.ushahidi.android.domain.entity.FormAttribute;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormAttributeRepository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeDataRepository implements FormAttributeRepository {

    private final FormAttributeDataSourceFactory mFormAttributeDataSourceFactory;

    private final FormAttributeEntityDataMapper mFormAttributeEntityDataMapper;

    /**
     * Default constructor that creates {@link FormDataRepository}
     *
     * @param formAttributeDataSourceFactory A factory to create the different data source
     *                                       implementations
     * @param formAttributeEntityDataMapper  The @{link FormEntityDataMapper}
     */
    @Inject
    public FormAttributeDataRepository(
            @NonNull FormAttributeDataSourceFactory formAttributeDataSourceFactory,
            FormAttributeEntityDataMapper formAttributeEntityDataMapper) {
        mFormAttributeDataSourceFactory = formAttributeDataSourceFactory;
        mFormAttributeEntityDataMapper = formAttributeEntityDataMapper;
    }

    @Override
    public Observable<List<FormAttribute>> getFormAttributes(Long deploymentId, Long formId,
            From from) {
        FormAttributeDataSource formAttributeDataSource;
        if (from == From.ONLINE) {
            formAttributeDataSource = mFormAttributeDataSourceFactory.createApiDataSource();
        } else {
            formAttributeDataSource = mFormAttributeDataSourceFactory
                    .createDatabaseDataSource();
        }
        return formAttributeDataSource.getFormAttributes(deploymentId, formId)
                .map(mFormAttributeEntityDataMapper::map);
    }
}
