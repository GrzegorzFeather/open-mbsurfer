package com.mbsurfer.util;

import android.util.Log;


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

}
