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

package com.ushahidi.android.presentation.view.ui.fragment;

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

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.cocoahero.android.geojson.FeatureCollection;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.UshahidiApplication;
import com.ushahidi.android.presentation.di.components.post.MapPostComponent;
import com.ushahidi.android.presentation.model.ClusterMarkerModel;
import com.ushahidi.android.presentation.model.GeoJsonModel;
import com.ushahidi.android.presentation.presenter.post.MapPostPresenter;
import com.ushahidi.android.presentation.state.ReloadPostEvent;
import com.ushahidi.android.presentation.state.RxEventBus;
import com.ushahidi.android.presentation.util.GeoJsonLoadUtility;
import com.ushahidi.android.presentation.view.post.MapPostView;
import com.ushahidi.android.presentation.view.ui.activity.PostActivity;

import org.json.JSONException;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

import static rx.android.app.AppObservable.bindFragment;

/**
 * Provides Google maps as a fragment in a {@link android.support.v4.view.ViewPager}. Has support
 * for clustering markers and drawing polygons.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MapPostFragment extends BaseFragment
        implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<ClusterMarkerModel>,
        ClusterManager.OnClusterInfoWindowClickListener<ClusterMarkerModel>,
        ClusterManager.OnClusterItemInfoWindowClickListener<ClusterMarkerModel>, MapPostView {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static MapPostFragment mMapPostFragment;

    @Inject
    MapPostPresenter mMapPostPresenter;

    RxEventBus mRxEventBus;

    private ClusterManager<ClusterMarkerModel> mClusterManager;

    private MapFragment mMapFragment;

    private GoogleMap mMap;

    private HashMap<Marker, ClusterMarkerModel> markers = new HashMap<>();

    private CompositeSubscription mSubscriptions;

    private Snackbar mSnackbar;

    public MapPostFragment() {
        super(R.layout.map_post, 0);
    }

    public static MapPostFragment newInstance() {
        if (mMapPostFragment == null) {
            mMapPostFragment = new MapPostFragment();
        }
        return mMapPostFragment;
    }

    public void onResume() {
        super.onResume();
        // Set up Google map
        setUpMapIfNeeded();
        mMapPostPresenter.resume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mSubscriptions = new CompositeSubscription();

        mSubscriptions
                .add(bindFragment(this, mRxEventBus.toObservable())
                        .subscribe(event -> {
                            if (event instanceof ReloadPostEvent) {
                                ReloadPostEvent reloadPostEvent
                                        = (ReloadPostEvent) event;
                                if (reloadPostEvent != null) {
                                    mMapPostPresenter.loadGeoJsonFromDb();
                                }
                            }
                        }));
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapPostPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapPostPresenter.destroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        mRxEventBus = UshahidiApplication.getRxEventBusInstance();
    }

    private void initialize() {
        checkPlayServices();
        getMapPostComponent(MapPostComponent.class).inject(this);
        mMapPostPresenter.setView(this);
        mMapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.post_map);
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
                        new WeakReference<>(mClusterManager),
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
     * prompt user about it.
     *
     * @return boolean
     */
    private void checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                getActivity().finish();
            }
        }
    }

    @Override
    public void showGeoJson(GeoJsonModel geoJsonModel) {
        FeatureCollection featureCollection;
        ArrayList<Object> uiObjects = new ArrayList<>();
        try {
            featureCollection = GeoJsonLoadUtility
                    .parseGeoJson(geoJsonModel.getGeoJson());
            uiObjects = GeoJsonLoadUtility
                    .createUIObjectsFromGeoJSONObjects(featureCollection,
                            getResources().getColor(R.color.white),
                            getResources().getColor(R.color.red));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        // Do nothing
    }

    @Override
    public void hideLoading() {
        // Do nothing
    }

    @Override
    public void showRetry() {
        mSnackbar = Snackbar
                .make(getView(), getString(R.string.geojson_not_found), Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, e -> mMapPostPresenter.loadGeoJsonFromOnline());
        setSnackbarTextColor();
    }

    @Override
    public void hideRetry() {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
    }

    @Override
    public void showError(String s) {
        mSnackbar = Snackbar.make(getView(), s, Snackbar.LENGTH_LONG);
        setSnackbarTextColor();
    }

    /**
     * Change Snackbar text color to orange by finding the TextView associated with
     * it. A bit of a hack to get this working as it doesn't come with a native API for doing this.
     */
    private void setSnackbarTextColor() {
        View view = mSnackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getAppContext().getResources().getColor(R.color.orange));
        mSnackbar.show();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    private <C> C getMapPostComponent(Class<C> componentType) {
        return componentType.cast(((PostActivity) getActivity()).getMapPostComponent());
    }

    @Override
    public boolean onClusterClick(Cluster<ClusterMarkerModel> cluster) {
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<ClusterMarkerModel> cluster) {
        // Do nothing
    }

    @Override
    public void onClusterItemInfoWindowClick(ClusterMarkerModel clusterMarkerModel) {
        //TODO launch post detail view
        //For now show a toast with the title
        showToast(clusterMarkerModel.getTitle());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
            squareTextView.setTextColor(mContext.getResources().getColor(R.color.black_darker));
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
            markerOptions.snippet(geoJsonModel.getDescription());
            markerOptions.title(geoJsonModel.getTitle());
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
