package com.mbsurfer.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.TypedValue;


/**
 * Created by GrzegorzFeathers on 1/6/15.
 */
public class MBSUtils {

    public static enum LogLevel {
        DEBUG, ERROR, WARNING
    }

    public static void log(String tag, String message){
        log(LogLevel.DEBUG, tag, message, null);
    }

    public static void log(LogLevel level, String tag, String message, Throwable t){
        switch(level) {
            case DEBUG: {
                Log.d(tag, message);
                break;
            }
            case ERROR: {
                if(t == null){
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, t);
                }
                break;
            }
            case WARNING: {
                Log.w(tag, message);
                break;
            }
        }
    }

    public static int dipsToPix(Context ctx, float dps) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps,
                ctx.getResources().getDisplayMetrics());
    }

    public static int getActionBarSize(Context ctx){
        final TypedArray styledAttributes = ctx.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarSize;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of separation
        dist = dist * 1852; // 1852 meters per nautical mile
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
