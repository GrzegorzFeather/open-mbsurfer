package com.mbsurfer.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MBSPreferences {

    private static String PREFS_POSTFIX = "mbs_prefs";

    // General purpose
    public static final String APP_CONFIG = "app_configuration";

    // Access
    public static final String AUTH_TOKEN = "auth_token";
    public static final String USER = "user";

    private static SharedPreferences preferences = null;
    private static SharedPreferences.Editor editor = null;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName() + PREFS_POSTFIX,
                                                   Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static String loadString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static int loadInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static long loadLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public static boolean loadBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static Set<String> loadStringSet(String key, Set<String> defValue){
        return preferences.getStringSet(key, defValue);
    }

    public static void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public static void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveStringSet(String key, Set<String> value){
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static boolean containsPreference(String key) {
        return preferences.contains(key);
    }

    public static void clearPreferences(){
        editor.clear();
        editor.commit();
    }

}
