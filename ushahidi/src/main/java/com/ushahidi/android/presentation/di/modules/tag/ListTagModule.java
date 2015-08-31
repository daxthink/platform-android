package com.ushahidi.android.presentation.di.modules.tag;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.tag.ListTagUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger modules that provides list tag related objects
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class ListTagModule {

    /**
     * Provides {@link ListTagUsecase} object annotated with the name "postList"
     *
     * @param listTagUsecase The list post use case
     * @return The list tag use case
     */
    @Provides
    @ActivityScope
    @Named("tagList")
    ListTagUsecase provideListTagUseCase(ListTagUsecase listTagUsecase) {
        return listTagUsecase;
    }
}
