package com.mbsurfer.model;

import com.google.android.gms.maps.model.LatLng;

public class Station {

    private Line mLine;
    private int mIconId;
    private String mName;
    private LatLng mLatLng;

    public Station(Line line, int iconId, String name, double longitude, double latitude) {
        this.mLine = line;
        this.mIconId = iconId;
        this.mName = name;
        this.mLatLng = new LatLng(latitude, longitude);
    }

    public Line getLine() {
            return this.mLine;
    }

    public int getIconId() {
            return this.mIconId;
    }

    public String getName() {
            return this.mName;
    }

    public LatLng getLatLng(){
            return this.mLatLng;
    }
	
}
