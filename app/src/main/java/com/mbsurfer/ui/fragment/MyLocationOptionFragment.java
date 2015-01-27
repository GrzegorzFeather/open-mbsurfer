package com.mbsurfer.ui.fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.app.menu.UserMenu;
import com.mbsurfer.model.Line;
import com.mbsurfer.model.Station;
import com.mbsurfer.ui.widget.MBSToolbar;
import com.mbsurfer.ui.widget.SlidingLinearLayout;
import com.mbsurfer.util.MBSUtils;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MyLocationOptionFragment extends MenuOptionFragment
        implements GoogleMap.OnMyLocationChangeListener, HomeMenuFragment.OnDrawerSlideListener {

    private View mRootView;
    private MapView mMapView;
    private SlidingLinearLayout mDirectionsLayout;

    private GoogleMap mMap;

    private boolean mIsFirstRecommendation = true;
    private Map<String, Station> mStationMarkers = new HashMap<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MapsInitializer.initialize(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mIsFirstRecommendation = true;

        this.mRootView = inflater.inflate(R.layout.fragment_option_map, container, false);

        this.mMapView = (MapView) this.mRootView.findViewById(R.id.map);
        this.mMapView.onCreate(savedInstanceState);

        this.mDirectionsLayout = (SlidingLinearLayout) this.mRootView.findViewById(R.id.layout_directions);

        return this.mRootView;
    }

    private void setupMap(final GoogleMap map){
        this.mMap = map;

        this.mMap.setMyLocationEnabled(true);
        this.mMap.setOnMyLocationChangeListener(this);
        this.mMap.setPadding(0, MBSUtils.getActionBarSize(this.getActivity()), 0, 0);
        this.mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mDirectionsLayout.setFromStation(mStationMarkers.get(marker.getId()));
                return false;
            }
        });

        LinesInfoWindowManager infoWindowManager = new LinesInfoWindowManager(this.getActivity());
        this.mMap.setInfoWindowAdapter(infoWindowManager);
        this.mMap.setOnInfoWindowClickListener(infoWindowManager);

        UiSettings settings = this.mMap.getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMapToolbarEnabled(true);
        settings.setMyLocationButtonEnabled(true);

        Location myLocation = this.mMap.getMyLocation();
        if(myLocation != null){
            this.moveMapCameraTo(myLocation, false);
        } else {
            this.recoverLocation();
        }
    }

    private void drawLinesInMap(GoogleMap map){
        this.mStationMarkers.clear();
        for(Line l : Line.values()){
            for(Station s : l.getStations()){
                Marker marker = map.addMarker(new MarkerOptions()
                      .title(s.getLine().toString())
                      .snippet(s.getName())
                      .draggable(false)
                      .position(s.getLatLng())
                      .icon(s.getLine().getMarkerBitmapDescriptor()));
                this.mStationMarkers.put(marker.getId(), s);
            }
        }
    }

    private void moveMapCameraTo(final Location location, boolean animate){
        if(location == null) { return; }

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showClosestStation(location);
            }
        });

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()),
                animate ? this.mMap.getCameraPosition().zoom : 16.5f);
        if(animate){
            this.mMap.animateCamera(cu, 100, null);
        } else {
            this.mMap.moveCamera(cu);
        }
    }

    private void recoverLocation(){
        if(this.getActivity() == null){ return; }
        LocationManager lm = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(lm == null){ return; }

        Location lastKnown = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(lastKnown != null){
            this.moveMapCameraTo(lastKnown, false);
        } else {
            lastKnown = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnown != null){
                this.moveMapCameraTo(lastKnown, false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mMapView.onResume();
        this.getMenuHostActivity().addOnDrawerSlideListener(this);
        this.mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMap(googleMap);
                drawLinesInMap(googleMap);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mMapView.onPause();
        if(this.mMap != null){
            this.mMap.setOnMyLocationChangeListener(null);
        }
        this.getMenuHostActivity().removeOnDrawerSlideListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mMapView.onLowMemory();
    }

    @Override
    protected MBSConfiguration.HomeMenuOption getMenuOption() {
        return UserMenu.MY_LOCATION;
    }

    @Override
    protected void overrideToolbarSetup(MBSToolbar toolbar) {
        Toolbar toolbarComp = toolbar.getToolbarComp();
        toolbar.setTitleColor(this.getResources().getColor(R.color.mbsPrimary));
        toolbar.setSubtitleColor(this.getResources().getColor(R.color.mbsPrimary));

        ViewGroup.LayoutParams layoutParams = toolbarComp.getLayoutParams();
        layoutParams.height = MBSUtils.getActionBarSize(this.getActivity());

        toolbarComp.setLayoutParams(layoutParams);
        toolbarComp.setBackgroundColor(this.getResources().getColor(android.R.color.transparent));

        if(toolbarComp.getChildCount() > 0){
            if(toolbarComp.getChildAt(0) instanceof ImageButton){
                ImageButton ib = (ImageButton) toolbarComp.getChildAt(0);
                ib.setColorFilter(this.getResources().getColor(R.color.mbsPrimary));
            }
        }
    }

    @Override
    public void onMyLocationChange(final Location location) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showClosestStation(location);
            }
        });
    }

    private void showClosestStation(Location location){
        double distance = Double.MAX_VALUE;
        Station closestStation = null;

        double calculatedDistance;
        for(Line l : Line.values()){
            for(Station s : l.getStations()){
                calculatedDistance = SphericalUtil.computeDistanceBetween(
                        new LatLng(location.getLatitude(), location.getLongitude()),
                        s.getLatLng());
                if(calculatedDistance < distance){
                    distance = calculatedDistance;
                    closestStation = s;
                }
            }
        }

        if(this.mIsFirstRecommendation){
            this.mIsFirstRecommendation = false;
            this.mDirectionsLayout.setFromStation(closestStation, false);
        }
    }

    @Override
    public void onDrawerSlide(float slideOffset) {
        int alpha = 0;

        if(slideOffset >= 1.0f){
            alpha = 255;
        } else if(slideOffset <= 0.0f){
            alpha = 0;
        } else {
            alpha = Math.round(slideOffset * 255);
        }

        int toolbarColor = this.getResources().getColor(R.color.mbsPrimary);
        this.getToolbar().getToolbarComp().setBackgroundColor(
                Color.argb(alpha, Color.red(toolbarColor),
                           Color.green(toolbarColor),
                           Color.blue(toolbarColor)));

        int toolbarChildrenColor = (Integer) new ArgbEvaluator().evaluate(
                slideOffset, this.getResources().getColor(R.color.mbsPrimary),
                this.getResources().getColor(android.R.color.white));

        this.getToolbar().setTitleColor(toolbarChildrenColor);
        this.getToolbar().setSubtitleColor(toolbarChildrenColor);

        Toolbar toolbar = this.getToolbar().getToolbarComp();
        if(toolbar.getChildCount() > 0){
            if(toolbar.getChildAt(0) instanceof ImageButton){
                ImageButton ib = (ImageButton) toolbar.getChildAt(0);
                ib.setColorFilter(toolbarChildrenColor);
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        switch (this.mDirectionsLayout.getCurrentStatus()){
            case FIRST_LEVEL:
                this.mDirectionsLayout.close();
                return true;
            case OPEN_FULL:
                this.mDirectionsLayout.openFirstLevel();
                return true;
            case CLOSED: default:
                return super.onBackPressed();
        }
    }

    private class LinesInfoWindowManager implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

        private Context mContext;
        private View mRootView;
        private ImageView mImgStation;
        private TextView mLblLine;
        private TextView mLblStation;
        private Button mBtnOrigin;
        private Button mBtnDestination;

        public LinesInfoWindowManager(Context context){
            this.mContext = context;
            this.mRootView = LayoutInflater.from(this.mContext).inflate(
                    R.layout.layout_station_info_window, null, false);
            this.mImgStation = (ImageView) this.mRootView.findViewById(R.id.img_station);
            this.mLblLine = (TextView) this.mRootView.findViewById(R.id.lbl_line);
            this.mLblStation = (TextView) this.mRootView.findViewById(R.id.lbl_station);
            this.mBtnOrigin = (Button) this.mRootView.findViewById(R.id.btn_origin);
            this.mBtnDestination = (Button) this.mRootView.findViewById(R.id.btn_destination);

            this.mBtnOrigin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDirectionsLayout.setFromStation(mStationMarkers.get(((Marker) v.getTag()).getId()));
                }
            });
            this.mBtnDestination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDirectionsLayout.setToStation(mStationMarkers.get(((Marker)v.getTag()).getId()));
                }
            });
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            Station station = mStationMarkers.get(marker.getId());

            this.mLblLine.setText(station.getLine().toString());
            this.mLblStation.setText(station.getName());
            this.mImgStation.setImageResource(station.getIconId());

            this.mBtnOrigin.setTag(marker);
            this.mBtnDestination.setTag(marker);

            return this.mRootView;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
        }
    }
}
