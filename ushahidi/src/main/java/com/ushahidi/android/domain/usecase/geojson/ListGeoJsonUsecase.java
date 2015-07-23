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

package com.ushahidi.android.domain.usecase.geojson;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.repository.GeoJsonRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.android.domain.entity.GeoJson} from local storage or via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListGeoJsonUsecase extends Usecase {

    private final GeoJsonRepository mGeoJsonRepository;

    private Long mDeploymentId = null;

    private From mFrom;

    /**
     * Default constructor
     *
     * @param geoJsonRepository   The GeoJson Repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected ListGeoJsonUsecase(GeoJsonRepository geoJsonRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mGeoJsonRepository = geoJsonRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.android.domain.entity.GeoJson}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param from         Whether to fetch through the API or the local storage
     */
    public void setListGeoJson(Long deploymentId, From from) {
        mDeploymentId = deploymentId;
        mFrom = from;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFrom == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setListGeoJson(...)");
        }
        return mGeoJsonRepository.getGeoJson(mDeploymentId, mFrom);
    }
}
