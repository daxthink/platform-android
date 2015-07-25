package com.ushahidi.android.presentation.di.modules.post;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.post.GetPostUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Provides injectable modules scoped with {@link ActivityScope} to detail post related classes
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class GetPostModule {

    /**
     * Provides {@link GetPostUsecase} object annotated with the name "postList"
     *
     * @param getPostUsecase Get post use case
     * @return The get post use case
     */
    @Provides
    @ActivityScope
    @Named("postGet")
    GetPostUsecase provideListPostUseCase(GetPostUsecase getPostUsecase) {
        return getPostUsecase;
    }
}
