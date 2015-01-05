package com.mbsurfer.ui;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.ui.fragment.HomeMenuFragment;
import com.mbsurfer.ui.widget.MBSToolbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class HomeActivity extends MenuHostActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final int DEFAULT_FRAGMENT_TRANSITION = FragmentTransaction.TRANSIT_FRAGMENT_FADE;

    private MBSToolbar mToolbar;

    private HomeMenuFragment mMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_home);
        this.setupHome();
    }

    private void setupHome(){
        this.mMenuFragment = (HomeMenuFragment) this.getSupportFragmentManager()
                .findFragmentByTag(HomeMenuFragment.TAG);

        this.mToolbar = (MBSToolbar) this.findViewById(R.id.toolbar);

        // TODO: Add menu
        //this.mToolbar.inflateMenu(R.menu.main);
        this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });

        this.mToolbar.setTitle(R.string.app_name);
        this.pushToStack(MBSConfiguration.getDefaultMenuOption().getContentClass(),
                         null, -1, false);
    }

    @Override
    public MBSToolbar getToolbar() {
        return this.mToolbar;
    }

    public void setSubtitle(int subtitleRes){
        this.mToolbar.setSubtitle(subtitleRes);
    }

    @Override
    public void onHomeMenuOptionSelected(MBSConfiguration.HomeMenuOption menuOption) {
        if(menuOption.equals(MBSConfiguration.getDefaultMenuOption())){
            this.clearStack();
        } else {
            this.replaceStack(menuOption.getContentClass(), null);
        }

        this.supportInvalidateOptionsMenu();
    }

    public void clearStack(){
        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void replaceStack(Class<? extends Fragment> newContentClass, Bundle extras){
        this.clearStack();
        this.pushToStack(newContentClass, extras);
    }

    public void pushToStack(Fragment fragment, String tag){
        this.pushToStack(fragment, tag, DEFAULT_FRAGMENT_TRANSITION);
    }

    public void pushToStack(Fragment fragment, String tag, int transition){
        this.pushToStack(fragment, tag, transition, true);
    }

    private void pushToStack(Fragment fragment, String tag, int transition, boolean addToBackStack){
        FragmentManager fm = this.getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment, tag);

        if(transition > 0){
            transaction.setTransition(transition);
        }
        if(addToBackStack){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    private void pushToStack(Class<? extends Fragment> fragmentClass, Bundle extras,
                             int transition, boolean addToBackStack){
        Fragment fragment = this.getFragmentInstance(fragmentClass, extras);

        if(fragment == null){
            Log.d(TAG, "Unable to create the fragment instance");
            return;
        }

        this.pushToStack(fragment, fragmentClass.getName(), transition, addToBackStack);
    }

    public void pushToStack(Class<? extends Fragment> fragmentClass, Bundle extras, int transition){
        this.pushToStack(fragmentClass, extras, transition, true);
    }

    public void pushToStack(Class<? extends Fragment> fragmentClass, Bundle extras) {
        this.pushToStack(fragmentClass, extras, DEFAULT_FRAGMENT_TRANSITION);
    }

    public void singlePop(){
        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack();
    }

    private Fragment getFragmentInstance(Class<? extends Fragment> fragmentClass, Bundle extras){
        Fragment fragment = null;

        try {
            fragment = fragmentClass.newInstance();
            if(extras != null){
                fragment.setArguments(extras);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public void onBackPressed() {
        if(this.mMenuFragment == null || !this.mMenuFragment.isDrawerOpen()){
            super.onBackPressed();
        } else {
            this.mMenuFragment.closeDrawer();
        }
    }
}