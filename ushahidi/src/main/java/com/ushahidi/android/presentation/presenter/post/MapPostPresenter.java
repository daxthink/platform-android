/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.presenter.post;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.GeoJson;
import com.ushahidi.android.domain.usecase.geojson.ListGeoJsonUsecase;
import com.ushahidi.android.presentation.exception.ErrorMessageFactory;
import com.ushahidi.android.presentation.model.mapper.GeoJsonModelDataMapper;
import com.ushahidi.android.presentation.view.post.MapPostView;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter for fetching Post with GeoJson
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MapPostPresenter implements Presenter {

    private MapPostView mMapPostView;

    private final ListGeoJsonUsecase mListGeoJsonUsecase;

    private final GeoJsonModelDataMapper mGeoJsonModelDataMapper;

    private final PrefsFactory mPrefsFactory;

    @Inject
    public MapPostPresenter(ListGeoJsonUsecase listGeoJsonUsecase,
            GeoJsonModelDataMapper geoJsonModelDataMapper, PrefsFactory prefsFactory) {
        mListGeoJsonUsecase = listGeoJsonUsecase;
        mGeoJsonModelDataMapper = geoJsonModelDataMapper;
        mPrefsFactory = prefsFactory;
    }

    @Override
    public void resume() {
        loadGeoJsonFromDb();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mListGeoJsonUsecase.unsubscribe();
    }

    public void setView(MapPostView mapPostView) {
        mMapPostView = mapPostView;
    }

    public void loadGeoJsonFromDb() {
        loadGeoJson(From.DATABASE);
    }

    public void loadGeoJsonFromOnline() {
        loadGeoJson(From.ONLINE);
    }

    private void loadGeoJson(From from) {
        mMapPostView.hideRetry();
        mMapPostView.showLoading();
        mListGeoJsonUsecase.setListGeoJson(mPrefsFactory.getActiveDeploymentId().get(), from);
        mListGeoJsonUsecase.execute(new DefaultSubscriber<List<GeoJson>>() {
            @Override
            public void onCompleted() {
                mMapPostView.hideLoading();
            }

            @Override
            public void onNext(List<GeoJson> geoJsons) {
                mMapPostView.hideLoading();
                // TODO: Implement UTIL class for converting to a UiObjects

            }

            @Override
            public void onError(Throwable e) {
                mMapPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mMapPostView.showRetry();
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mMapPostView.getAppContext(), errorHandler.getException());
        mMapPostView.showError(errorMessage);
    }
}
