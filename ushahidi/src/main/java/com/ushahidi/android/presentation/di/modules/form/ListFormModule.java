package com.ushahidi.android.presentation.di.modules.form;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.form.ListFormUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger modules that provides list form related objects
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class ListFormModule {

    /**
     * Provides {@link ListFormUsecase} object annotated with the name "formList"
     *
     * @param listFormUsecase The list form use case
     * @return The list form use case
     */
    @Provides
    @ActivityScope
    @Named("formList")
    ListFormUsecase provideListFormUseCase(ListFormUsecase listFormUsecase) {
        return listFormUsecase;
    }
}
