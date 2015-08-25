package com.ushahidi.android.data.api;

import com.ushahidi.android.data.api.model.FormAttributes;
import com.ushahidi.android.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.android.data.entity.FormAttributeEntity;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeApi {

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param ushAccessTokenManager The access token manager. Cannot be a null value
     */
    @Inject
    public FormAttributeApi(@NonNull UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Gets a {@link FormAttributes}
     *
     * @param formId The form id to use to retrieve the form attribute
     * @return An Observable that emits{@link FormAttributes}
     */
    public Observable<List<FormAttributeEntity>> getFormAttributes(Long formId) {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getFormAttributes(authorizationHeader, formId))
                .flatMap(this::setFormAttributes);
    }

    private Observable<List<FormAttributeEntity>> setFormAttributes(FormAttributes attributes) {
        return Observable.just(attributes.getFormAttributes());
    }
}
