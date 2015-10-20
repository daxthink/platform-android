package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.FormStageEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.formstage.FormStageDataSource;
import com.ushahidi.android.data.repository.datasource.formstage.FormStageDataSourceFactory;
import com.ushahidi.android.domain.entity.FormStage;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.FormStageRepository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageDataRepository implements FormStageRepository {

    private final FormStageDataSourceFactory mFormStageDataSourceFactory;

    private final FormStageEntityDataMapper mFormStageEntityDataMapper;

    /**
     * Default constructor that creates {@link FormDataRepository}
     *
     * @param formStageDataSourceFactory A factory to create the different data source
     *                                   implementations
     * @param formStageEntityDataMapper  The @{link FormEntityDataMapper}
     */
    @Inject
    public FormStageDataRepository(
            @NonNull FormStageDataSourceFactory formStageDataSourceFactory,
            FormStageEntityDataMapper formStageEntityDataMapper) {
        mFormStageDataSourceFactory = formStageDataSourceFactory;
        mFormStageEntityDataMapper = formStageEntityDataMapper;
    }

    @Override
    public Observable<List<FormStage>> getFormStages(Long deploymentId, Long formId,
            From from) {
        FormStageDataSource formStageDataSource;
        if (from == From.ONLINE) {
            formStageDataSource = mFormStageDataSourceFactory.createApiDataSource();
        } else {
            formStageDataSource = mFormStageDataSourceFactory
                    .createDatabaseDataSource();
        }
        return formStageDataSource.getFormStages(deploymentId, formId)
                .map(mFormStageEntityDataMapper::map);
    }

    @Override
    public Observable<Long> putFormStage(FormStage form) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<FormStage> getFormStage(Long deploymentId, Long formStageId) {
        throw new UnsupportedOperationException();
    }
}

