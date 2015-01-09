package com.mbsurfer.ui.fragment;

import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.ui.MenuHostActivity;
import com.mbsurfer.ui.widget.MBSToolbar;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public abstract class MenuOptionFragment extends BaseFragment {

    private MBSConfiguration.HomeMenuOption mMenuOption;

    public MenuOptionFragment(){
        this.mMenuOption = this.getMenuOption();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getMenuHostActivity().setSubtitle(this.mMenuOption.getTitleRes());
        this.overrideToolbarSetup(this.getMenuHostActivity().getToolbar());
    }

    protected MenuHostActivity getMenuHostActivity(){
        MenuHostActivity menuHostActivity = null;

        try {
            menuHostActivity = (MenuHostActivity) this.getActivity();
        } catch (ClassCastException e) {
            throw e;
        }

        return menuHostActivity;
    }

    protected void overrideToolbarSetup(MBSToolbar toolbar){}

    protected abstract MBSConfiguration.HomeMenuOption getMenuOption();

}
