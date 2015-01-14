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

    /**
     * Helper method to convert dips to pixels.
     */
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

}
