package com.mbsurfer.ui.fragment;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.app.menu.UserMenu;
import com.mbsurfer.ui.widget.MBSToolbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MapOptionFragment extends MenuOptionFragment {

    private View mRootView;
    private MapView mMapView;

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
        toolbar.setSubtitle("Probando Override");
    }
}
