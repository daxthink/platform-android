package com.ushahidi.android.data.api;

import com.ushahidi.android.data.api.service.RestfulService;
import com.ushahidi.android.data.entity.DeploymentEntity;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentApi {

    public Observable<DeploymentEntity> getDeploymentConfig(String url) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        RestfulService restfulService = restAdapter.create(RestfulService.class);
        return restfulService.getDeploymentConfig();
    }

}