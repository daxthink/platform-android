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

package com.ushahidi.android.core.usecase.geojson;

import com.ushahidi.android.core.entity.GeoJson;
import com.ushahidi.android.core.exception.ErrorWrap;
import com.ushahidi.android.core.repository.IGeoJsonRepository;
import com.ushahidi.android.core.task.PostExecutionThread;
import com.ushahidi.android.core.task.ThreadExecutor;

/**
 * Fetches Post GEOJSON via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FetchGeoJson implements IFetchGeoJson {

    private IGeoJsonRepository mGeoJsonRepository;

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private final IGeoJsonRepository.GeoJsonListCallback mRepositoryCallback
            = new IGeoJsonRepository.GeoJsonListCallback() {
        @Override
        public void onGeoJsonListLoaded(GeoJson geoJson) {
            notifySuccess(geoJson);
        }

        @Override
        public void onError(ErrorWrap errorWrap) {
            notifyFailure(errorWrap);
        }
    };

    private IFetchGeoJson.Callback mCallback;

    private long mDeploymentId;

    /**
     * Default constructor.
     *
     * @param threadExecutor      {@link ThreadExecutor} used to execute this use case in a
     *                            background thread.
     * @param postExecutionThread {@link PostExecutionThread} used to post updates when the use
     *                            case
     *                            has been executed.
     */
    public FetchGeoJson(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        if (threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(long deploymentId, IFetchGeoJson.Callback callback) {
        if (deploymentId < 0 || callback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        mDeploymentId = deploymentId;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    public void setGeoJsonRepository(IGeoJsonRepository geoJsonRepository) {

        if (geoJsonRepository == null) {
            throw new IllegalArgumentException("IGeoJsonRepository cannot be null");
        }
        this.mGeoJsonRepository = geoJsonRepository;
    }

    @Override
    public void run() {
        if (mGeoJsonRepository == null) {
            throw new NullPointerException("You must call setGeoJsonRepository(...)");
        }
        mGeoJsonRepository.getGeoJsonListViaApi(mDeploymentId, mRepositoryCallback);
    }

    private void notifySuccess(final GeoJson geoJson) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGeoJsonFetched(geoJson);
            }
        });
    }

    private void notifyFailure(final ErrorWrap errorWrap) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(errorWrap);
            }
        });
    }
}
