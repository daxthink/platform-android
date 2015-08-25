package com.ushahidi.android.presentation.di.modules.post;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.android.domain.usecase.formattribute.ListFormAttributeUsecase;
import com.ushahidi.android.domain.usecase.post.AddPostUsecase;

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
     * Provides {@link AddPostUsecase} object annotated with the name "postList"
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

    /**
     * Provides {@link com.ushahidi.android.domain.usecase.formattribute.GetFormAttributeUsecase}
     * object annotated with the name "formAttributeGet"
     *
     * @param listFormAttributeUsecase Get Form Attribute Usecase
     * @return The get form attribute usecase
     */
    @Provides
    @ActivityScope
    @Named("formAttributeList")
    ListFormAttributeUsecase provideListFormAttributeUsecase(
            ListFormAttributeUsecase listFormAttributeUsecase) {
        return listFormAttributeUsecase;
    }
}
