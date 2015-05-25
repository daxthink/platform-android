/*
 * Copyright (c) 2015 Ushahidi.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.ui.fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;

import com.ushahidi.android.R;
import com.ushahidi.android.model.ClusterMarkerModel;
import com.ushahidi.android.presenter.MapPresenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MapPostFragment extends BaseFragment implements
        OnMapReadyCallback, ClusterManager.OnClusterClickListener<ClusterMarkerModel>,
        ClusterManager.OnClusterInfoWindowClickListener<ClusterMarkerModel>,
        ClusterManager.OnClusterItemInfoWindowClickListener<ClusterMarkerModel>,
        MapPresenter.View {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static MapPostFragment mMapPostFragment;

    private ClusterManager<ClusterMarkerModel> mClusterManager;

    private MapFragment mMapFragment;

    private GoogleMap mMap;

    @Inject
    MapPresenter mMapPresenter;

    private HashMap<Marker, ClusterMarkerModel> markers = new HashMap<>();

    /**
     * BaseFragment
     */
    public MapPostFragment() {
        super(R.layout.map_post, 0);
    }

    public static MapPostFragment newInstance() {
        mMapPostFragment = new MapPostFragment();
        return mMapPostFragment;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapPresenter.setView(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    public void onResume() {
        super.onResume();
        // Set up Google map
        setUpMapIfNeeded();
        mMapPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapPresenter.pause();
    }

    @Override
    void initPresenter() {
        // Make sure there is Google play service installed on the user's device
        checkPlayServices();
        mMapPresenter.init();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = mMapFragment.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mClusterManager = new ClusterManager<>(getActivity(), mMap);
                final PostModelRenderer postModelRenderer = new PostModelRenderer(
                        getActivity().getApplicationContext(), new WeakReference<>(mMap),
                        new WeakReference<ClusterManager>(mClusterManager),
                        new WeakReference<>(markers));
                mClusterManager.setRenderer(postModelRenderer);
                mMap.setOnCameraChangeListener(mClusterManager);
                mMap.setOnMarkerClickListener(mClusterManager);
                mMap.setOnInfoWindowClickListener(mClusterManager);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mClusterManager.setOnClusterInfoWindowClickListener(this);
                mClusterManager.setOnClusterItemInfoWindowClickListener(this);
            }
        }
    }


    /**
     * Check if Google play services is installed on the user's device. If it's not
     * prompt user to about it.
     *
     * @return boolean
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onClusterClick(Cluster<ClusterMarkerModel> postModelCluster) {

        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<ClusterMarkerModel> postModelCluster) {
        //Do nothing
    }

    @Override
    public void onClusterItemInfoWindowClick(ClusterMarkerModel geoJsonModel) {
        //TODO launch post detail view
        //For now show a toast with the title
        showToast(geoJsonModel.title);
    }

    @Override
    public void showGeoJson(ArrayList<Object> uiObjects) {
        for (Object uiObj : uiObjects) {
            if (uiObj instanceof PolylineOptions) {
                mMap.addPolyline((PolylineOptions) uiObj);
            } else if (uiObj instanceof PolygonOptions) {
                mMap.addPolygon((PolygonOptions) uiObj);
            } else {
                if (mClusterManager != null) {
                    mClusterManager.addItem((ClusterMarkerModel) uiObj);
                    mClusterManager.cluster();
                }
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Draws custom colored circle for clustered pins.
     */
    private static class PostModelRenderer extends DefaultClusterRenderer<ClusterMarkerModel> {

        private final IconGenerator mClusterIconGenerator;

        private final float mDensity;

        private ShapeDrawable mColoredCircleBackground;

        private Context mContext;

        private WeakReference<HashMap<Marker, ClusterMarkerModel>> mMarkers;

        /**
         * Icons for each bucket.
         */
        private SparseArray<BitmapDescriptor> mIcons = new SparseArray<>();

        public PostModelRenderer(Context context, WeakReference<GoogleMap> map,
                WeakReference<ClusterManager> clusterManager,
                WeakReference<HashMap<Marker, ClusterMarkerModel>> markers) {
            super(context, map.get(), clusterManager.get());
            mContext = context;
            mDensity = context.getResources().getDisplayMetrics().density;
            mClusterIconGenerator = new IconGenerator(mContext);
            mClusterIconGenerator.setContentView(makeSquareTextView());
            mClusterIconGenerator.setTextAppearance(R.style.CustomClusterIcon_TextAppearance);
            mClusterIconGenerator.setBackground(makeClusterBackground());
            mMarkers = markers;
        }

        private SquareTextView makeSquareTextView() {
            SquareTextView squareTextView = new SquareTextView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            squareTextView.setLayoutParams(layoutParams);
            squareTextView.setId(R.id.text);
            squareTextView.setTextColor(mContext.getResources().getColor(R.color.body_text_1));
            int twelveDpi = (int) (12 * mDensity);
            squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
            return squareTextView;
        }

        private LayerDrawable makeClusterBackground() {
            mColoredCircleBackground = new ShapeDrawable(new OvalShape());
            ShapeDrawable outline = new ShapeDrawable(new OvalShape());

            outline.getPaint().setColor(mContext.getResources()
                    .getColor(R.color.cluster_solid_color)); // Solid red
            LayerDrawable background = new LayerDrawable(
                    new Drawable[]{outline, mColoredCircleBackground});
            int strokeWidth = (int) (mDensity * 5);
            background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
            return background;
        }

        private int getColor(int clusterSize) {
            final float hueRange = 100;
            final float sizeRange = 300;
            final float size = Math.min(clusterSize, sizeRange);
            final float hue = (sizeRange - size) * (sizeRange - size) / (sizeRange * sizeRange)
                    * hueRange;
            return Color.HSVToColor(new float[]{
                    hue, 0f, 1f
            });
        }

        @Override
        protected void onBeforeClusterItemRendered(ClusterMarkerModel geoJsonModel,
                MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(geoJsonModel, markerOptions);
            markerOptions.snippet(geoJsonModel.description);
            markerOptions.title(geoJsonModel.title);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<ClusterMarkerModel> cluster,
                MarkerOptions markerOptions) {
            super.onBeforeClusterRendered(cluster, markerOptions);
            int bucket = getBucket(cluster);
            BitmapDescriptor descriptor = mIcons.get(bucket);

            if (descriptor == null) {
                mColoredCircleBackground.getPaint().setColor(getColor(bucket));
                descriptor = BitmapDescriptorFactory
                        .fromBitmap(mClusterIconGenerator.makeIcon(getClusterText(bucket)));
                mIcons.put(bucket, descriptor);
            }

            markerOptions.anchor(.5f, .5f);
            markerOptions.icon(descriptor);

        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }

        protected void onClusterRendered(Cluster<ClusterMarkerModel> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
            for (ClusterMarkerModel geoJsonModel : cluster.getItems()) {
                mMarkers.get().put(marker, geoJsonModel);
            }
        }
    }
}
