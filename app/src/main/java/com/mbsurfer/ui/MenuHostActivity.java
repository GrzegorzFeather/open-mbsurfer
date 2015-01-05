package com.mbsurfer.ui;

import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.ui.widget.MBSToolbar;

import android.support.v7.app.ActionBarActivity;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public abstract class MenuHostActivity extends ActionBarActivity {

    public abstract MBSToolbar getToolbar();

    public abstract void setSubtitle(int subtitleRes);

    public abstract void onHomeMenuOptionSelected(MBSConfiguration.HomeMenuOption menuOption);

}

