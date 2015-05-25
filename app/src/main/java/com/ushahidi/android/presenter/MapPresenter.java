/*
 *  Copyright (c) 2015 Ushahidi.
 *
 *   This program is free software: you can redistribute it and/or modify it under
 *   the terms of the GNU Affero General Public License as published by the Free
 *   Software Foundation, either version 3 of the License, or (at your option)
 *   any later version.
 *
 *   This program is distributed in the hope that it will be useful, but WITHOUT
 *   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *   FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program in the file LICENSE-AGPL. If not, see
 *   https://www.gnu.org/licenses/agpl-3.0.html
 *
 */

package com.ushahidi.android.presenter;

import com.cocoahero.android.geojson.FeatureCollection;
import com.squareup.otto.Subscribe;
import com.ushahidi.android.R;
import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.core.exception.ErrorWrap;
import com.ushahidi.android.core.repository.IGeoJsonRepository;
import com.ushahidi.android.core.usecase.geojson.FetchGeoJson;
import com.ushahidi.android.core.usecase.geojson.ListGeoJson;
import com.ushahidi.android.data.api.service.GeoJsonService;
import com.ushahidi.android.data.repository.datasource.geojson.GeoJsonDataSourceFactory;
import com.ushahidi.android.exception.ErrorMessageFactory;
import com.ushahidi.android.state.IDeploymentState;
import com.ushahidi.android.ui.prefs.Prefs;
import com.ushahidi.android.ui.view.ILoadViewData;
import com.ushahidi.android.util.ApiServiceUtil;
import com.ushahidi.android.util.GeoJsonLoadUtils;

import org.json.JSONException;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MapPresenter implements IPresenter {

    private final FetchGeoJson mFetchGeoJson;

    private final ListGeoJson mListGeoJson;

    private final Prefs mPrefs;

    private final ApiServiceUtil mApiServiceUtil;

    private final GeoJsonDataSourceFactory mGeoJsonDataSourceFactory;

    private final IGeoJsonRepository mGeoJsonRepository;

    private final IDeploymentState mDeploymentState;

    private View mView;

    public boolean isRefreshing = false;

    private final ListGeoJson.Callback mListGeoJsonCallback = new ListGeoJson.Callback() {

        @Override
        public void onGeoJsonLoaded(GeoJson geoJson) {
            showGeoJsonList(geoJson);
            mView.hideLoading();
        }

        @Override
        public void onError(ErrorWrap error) {
            mView.hideLoading();
            showErrorMessage(error);
        }
    };

    /*private final FetchGeoJson.Callback mFetchGeoJsonCallback = new FetchGeoJson.Callback() {

        @Override
        public void onGeoJsonFetched(GeoJson geoJson) {
            showGeoJsonList(geoJson);
            mView.hideLoading();
        }

        @Override
        public void onError(ErrorWrap error) {
            mView.hideLoading();
            showErrorMessage(error);
        }
    };*/

    @Inject
    public MapPresenter(@NonNull ListGeoJson listPostGeoJson,
            @NonNull FetchGeoJson fetchGeoJson,
            @NonNull Prefs prefs,
            @NonNull ApiServiceUtil apiServiceUtil,
            @NonNull GeoJsonDataSourceFactory geoJsonDataSourceFactory,
            @NonNull IGeoJsonRepository geoJsonRepository,
            @NonNull IDeploymentState deploymentState) {
        mListGeoJson = listPostGeoJson;
        mFetchGeoJson = fetchGeoJson;
        mPrefs = prefs;
        mApiServiceUtil = apiServiceUtil;
        mGeoJsonDataSourceFactory = geoJsonDataSourceFactory;
        mGeoJsonRepository = geoJsonRepository;
        mDeploymentState = deploymentState;
    }

    private void setGeoJsonService(GeoJsonService geoJsonService) {
        mGeoJsonDataSourceFactory.setGeoJsonService(geoJsonService);
        mListGeoJson.setGeoJsonRepository(mGeoJsonRepository);
        mFetchGeoJson.setGeoJsonRepository(mGeoJsonRepository);
    }

    public void setView(@NonNull View view) {
        mView = view;
    }

    private GeoJsonService createGeoJsonService() {
        return mApiServiceUtil.createService(GeoJsonService.class,
                mPrefs.getActiveDeploymentUrl().get(), mPrefs.getAccessToken().get());
    }

    @Override
    public void resume() {
        mDeploymentState.registerEvent(this);
        if (!isRefreshing) {
            loadGeoJsonListFromLocalCache();
        }
    }

    @Override
    public void pause() {
        mDeploymentState.unregisterEvent(this);
    }

    public void init() {
        setGeoJsonService(createGeoJsonService());
    }

    private void loadGeoJsonListFromLocalCache() {
        mView.showLoading();
        getGeoJsonListFromLocalCache();
    }

    private void getGeoJsonListFromLocalCache() {
        mListGeoJson.execute(mPrefs.getActiveDeploymentId().get(), mListGeoJsonCallback);
    }

    /*public void fetchGeoJsonFromApi() {
        setGeoJsonService(createGeoJsonService());
        mFetchGeoJson.execute(mPrefs.getActiveDeploymentId().get(), mFetchGeoJsonCallback);
    }*/

    @Subscribe
    public void onActivatedDeploymentChanged(
            IDeploymentState.ActivatedDeploymentChangedEvent event) {
        loadGeoJsonListFromLocalCache();
    }

    private void showGeoJsonList(GeoJson geoJsons) {
        try {
            if (geoJsons.getGeoJson() != null && !TextUtils.isEmpty(geoJsons.getGeoJson())) {
                FeatureCollection features = GeoJsonLoadUtils.parseGeoJson(geoJsons.getGeoJson());
                ArrayList<Object> uiObjects = GeoJsonLoadUtils.createUIObjectsFromGeoJSONObjects(
                        features, 0, 0);
                mView.showGeoJson(uiObjects);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showErrorMessage(ErrorWrap errorWrap) {
        if (mView.getAppContext() != null) {
            String errorMessage = ErrorMessageFactory.create(mView.getAppContext(),
                    errorWrap.getException());
            if (errorMessage.equals(mView.getAppContext()
                    .getString(R.string.exception_message_no_connection))) {
                mView.showRetry(errorMessage);
            } else {
                mView.showError(errorMessage);
            }

        }
    }

    public interface View extends ILoadViewData {

        /**
         * Renders {@GeoJsonModel} on the map
         */
        void showGeoJson(ArrayList<Object> uiObjects);
    }
}
