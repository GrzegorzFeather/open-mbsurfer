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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MapOptionFragment extends MenuOptionFragment
        implements GoogleMap.OnMyLocationChangeListener{

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMap(googleMap);
            }
        });
    }

    private void setupMap(GoogleMap map){
        this.mMap = map;
        this.mMap.setMyLocationEnabled(true);
        this.mMap.setOnMyLocationChangeListener(this);
        this.mMapView.setAnimation(null);
        this.mMapView.animate();

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
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mMapView.onPause();
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
        return UserMenu.MAP;
    }

    @Override
    protected void overrideToolbarSetup(MBSToolbar toolbar) {
        super.overrideToolbarSetup(toolbar);

    }

    @Override
    public void onMyLocationChange(Location location) {

    }

}
