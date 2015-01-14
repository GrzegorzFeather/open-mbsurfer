package com.mbsurfer.ui.fragment;

import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.app.menu.UserMenu;


/**
 * Created by GrzegorzFeathers on 1/14/15.
 */
public class LinesMapOptionFragment extends MenuOptionFragment {

    @Override
    protected MBSConfiguration.HomeMenuOption getMenuOption() {
        return UserMenu.LINES_MAP;
    }
}
