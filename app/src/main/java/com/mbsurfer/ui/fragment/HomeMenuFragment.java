package com.mbsurfer.ui.fragment;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;
import com.mbsurfer.ui.MenuHostActivity;
import com.mbsurfer.ui.adapter.HomeMenuAdapter;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class HomeMenuFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = HomeMenuFragment.class.getSimpleName();

    private View mRootView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerMenuOptionsView;
    private RecyclerView.Adapter mRecyclerMenuOptionsAdapter;
    private RecyclerView.LayoutManager mRecyclerMenuOptionsManager;
    private ActionBarDrawerToggle mDrawerToogle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_home_menu, container, false);

        this.mRecyclerMenuOptionsView = (RecyclerView)
                this.mRootView.findViewById(R.id.recycler_menu_options);
        this.mRecyclerMenuOptionsView.setHasFixedSize(true);

        this.mRecyclerMenuOptionsManager = new LinearLayoutManager(this.getMenuHostActivity());
        this.mRecyclerMenuOptionsAdapter = new HomeMenuAdapter(MBSConfiguration.getMenuOptions(), this);

        this.mRecyclerMenuOptionsView.setLayoutManager(this.mRecyclerMenuOptionsManager);
        this.mRecyclerMenuOptionsView.setAdapter(this.mRecyclerMenuOptionsAdapter);

        return this.mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mDrawerLayout = (DrawerLayout)
                this.getMenuHostActivity().findViewById(R.id.drawer_home);
        this.mDrawerLayout.setScrimColor(Color.parseColor("#30000000"));
        this.mDrawerToogle = new ActionBarDrawerToggle(
                this.getMenuHostActivity(), this.mDrawerLayout,
                this.getMenuHostActivity().getToolbar().getToolbarComp(),
                R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                getMenuHostActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getMenuHostActivity().supportInvalidateOptionsMenu();
            }
        };
        this.mDrawerLayout.setDrawerListener(this.mDrawerToogle);

        this.mDrawerToogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(this.mDrawerToogle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private MenuHostActivity getMenuHostActivity(){
        MenuHostActivity menuHostActivity = null;
        try {
            menuHostActivity = (MenuHostActivity) this.getActivity();
        } catch (ClassCastException e) {
            throw e;
        } finally {
            return menuHostActivity;
        }
    }

    @Override
    public void onClick(View v) {
        this.mDrawerLayout.closeDrawer(Gravity.LEFT);
        int position = this.mRecyclerMenuOptionsView.getChildPosition(v);
        this.getMenuHostActivity().onHomeMenuOptionSelected(
                MBSConfiguration.getMenuOptions()[position - 1]);
    }

    public boolean isDrawerOpen(){
        return this.mDrawerLayout != null && this.mDrawerLayout.isDrawerOpen(R.id.fragment_home_menu);
    }

    public void closeDrawer(){
        if(this.isDrawerOpen()){
            this.mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }
}