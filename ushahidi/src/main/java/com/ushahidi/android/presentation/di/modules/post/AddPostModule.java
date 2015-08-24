package com.ushahidi.android.presentation.di.modules.post;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.post.AddPostUsecase;
import com.ushahidi.android.domain.usecase.post.GetPostUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Provides injectable modules scoped with {@link ActivityScope} to add post related classes
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class AddPostModule {

    /**
     * Provides {@link GetPostUsecase} object annotated with the name "postList"
     *
     * @param addPostUsecase Add Post Usecase
     * @return The add post Usecase
     */
    @Provides
    @ActivityScope
    @Named("postAdd")
    AddPostUsecase provideAddPostUseCase(AddPostUsecase addPostUsecase) {
        return addPostUsecase;
    }
}
