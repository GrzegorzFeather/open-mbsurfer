package com.mbsurfer.ui.fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.app.menu.UserMenu;
import com.mbsurfer.ui.widget.MBSToolbar;
import com.mbsurfer.util.MBSUtils;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageButton;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MyLocationOptionFragment extends MenuOptionFragment
        implements GoogleMap.OnMyLocationChangeListener, HomeMenuFragment.OnDrawerSlideListener{

    private View mRootView;
    private MapView mMapView;
    private ProgressDialog mLocationProgress;

    private GoogleMap mMap;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MapsInitializer.initialize(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_option_map, container, false);

        this.mMapView = (MapView) this.mRootView.findViewById(R.id.map);
        this.mMapView.onCreate(savedInstanceState);

        return this.mRootView;
    }

    private void setupMap(GoogleMap map){
        this.mMap = map;
        this.mMap.setMyLocationEnabled(true);
        this.mMap.setOnMyLocationChangeListener(this);
        this.mMap.setPadding(0, MBSUtils.getActionBarSize(this.getActivity()), 0, 0);

        UiSettings settings = this.mMap.getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMapToolbarEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setMyLocationButtonEnabled(true);

        Location myLocation = this.mMap.getMyLocation();
        if(myLocation != null){
            this.moveMapCameraTo(myLocation, false);
        } else {
            this.recoverLocation();
        }
    }

    private void moveMapCameraTo(Location location, boolean animate){
        if(location == null) { return; }
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
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mMapView.onPause();
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
    public void onMyLocationChange(Location location) {
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
}
