package com.ushahidi.android.data.api;

import com.ushahidi.android.data.api.model.FormStages;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.entity.FormStageEntity;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageApi {

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param ushAccessTokenManager The access token manager. Cannot be a null value
     */
    @Inject
    public FormStageApi(@NonNull UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Gets a {@link FormStages}
     *
     * @param formId The form id to use to retrieve the form attribute
     * @return An Observable that emits{@link FormStages}
     */
    public Observable<List<FormStageEntity>> getFormStages(Long formId) {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getFormStages(authorizationHeader, formId))
                .flatMap(this::setFormStages);
    }

    private Observable<List<FormStageEntity>> setFormStages(FormStages stages) {
        return Observable.just(stages.getFormStages());
    }
}
