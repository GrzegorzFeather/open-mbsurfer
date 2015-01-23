package com.mbsurfer.ui.fragment;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.ui.widget.MBSToolbar;

import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageButton;


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

    protected HomeMenuFragment.MenuHostActivity getMenuHostActivity(){
        HomeMenuFragment.MenuHostActivity menuHostActivity = null;

        try {
            menuHostActivity = (HomeMenuFragment.MenuHostActivity) this.getActivity();
        } catch (ClassCastException e) {
            throw e;
        }

        return menuHostActivity;
    }

    protected MBSToolbar getToolbar(){
        return this.getMenuHostActivity().getToolbar();
    }

    protected void overrideToolbarSetup(MBSToolbar toolbar){
        Toolbar toolbarComp = toolbar.getToolbarComp();
        toolbar.setTitleColor(this.getResources().getColor(android.R.color.white));
        toolbar.setSubtitleColor(this.getResources().getColor(android.R.color.white));

        ViewGroup.LayoutParams layoutParams = toolbarComp.getLayoutParams();
        layoutParams.height = this.getResources().getDimensionPixelSize(R.dimen.toolbar_height);

        toolbarComp.setLayoutParams(layoutParams);
        toolbarComp.setBackgroundColor(this.getResources().getColor(R.color.mbsPrimary));

        if(toolbarComp.getChildCount() > 0){
            if(toolbarComp.getChildAt(0) instanceof ImageButton){
                ImageButton ib = (ImageButton) toolbarComp.getChildAt(0);
                ib.clearColorFilter();
            }
        }
    }

    protected abstract MBSConfiguration.HomeMenuOption getMenuOption();

    public boolean onBackPressed(){
        return false;
    }

}
