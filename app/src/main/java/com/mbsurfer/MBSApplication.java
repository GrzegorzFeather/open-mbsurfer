package com.mbsurfer;

import com.mbsurfer.app.MBSPreferences;

import android.app.Application;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MBSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MBSPreferences.init(this);
        //MBSConfiguration.setBackendConfiguration(this.getBackendConfiguration());
    }

    //protected abstract BackendConfiguration getBackendConfiguration();

}

