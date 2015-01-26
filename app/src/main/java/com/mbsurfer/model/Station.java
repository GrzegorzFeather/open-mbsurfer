package com.mbsurfer.model;

import com.google.android.gms.maps.model.LatLng;

import java.text.Normalizer;
import java.util.Locale;


public class Station {

    public static final Locale defaultLocale = new Locale("es");

    private Line mLine;
    private int mIconId;
    private String mName;
    private String mNormalizedName;
    private LatLng mLatLng;

    public Station(Line line, int iconId, String name, double longitude, double latitude) {
        this.mLine = line;
        this.mIconId = iconId;
        this.mName = name;
        this.mNormalizedName = Normalizer.normalize(
                name.toLowerCase(defaultLocale),
                Normalizer.Form.NFD).replaceAll("[^a-zA-Z ]", "");
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

    public String getNormalizedName(){
        return this.mNormalizedName;
    }
}
