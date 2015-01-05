package com.mbsurfer.ui.adapter;

import com.mbsurfer.R;
import com.mbsurfer.app.MBSConfiguration;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class HomeMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU_ITEM = 1;

    private List<MBSConfiguration.HomeMenuOption> mDataSet;
    private View.OnClickListener mOnClickListener;

    public HomeMenuAdapter(MBSConfiguration.HomeMenuOption[] menuOptions,
                           View.OnClickListener clickListener){
        this.updateContent(menuOptions);
        this.mOnClickListener = clickListener;
    }

    private void updateContent(MBSConfiguration.HomeMenuOption[] fullMenuOptions){
        List<MBSConfiguration.HomeMenuOption> reducedMenuOptions = new ArrayList<>();
        for(MBSConfiguration.HomeMenuOption o : fullMenuOptions){
            if(o.isVisible()) { reducedMenuOptions.add(o);  }
        }
        this.mDataSet = reducedMenuOptions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_home_header, parent, false);
            return new HeaderViewHolder(rootView);
        } else {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_home_option, parent, false);
            rootView.setOnClickListener(this.mOnClickListener);
            return new MenuItemViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(this.getItemViewType(position) == TYPE_HEADER){

        } else {
            MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
            MBSConfiguration.HomeMenuOption option = this.getItem(position);
            menuItemHolder.mImgIcon.get().setImageResource(option.getIconRes());
            menuItemHolder.mLblTitle.get().setText(option.getTitleRes());
        }
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_MENU_ITEM;
    }

    public MBSConfiguration.HomeMenuOption getItem(int position){
        return this.mDataSet.get(position - 1);
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private WeakReference<ImageView> mImgProfilePicture;
        private WeakReference<TextView> mLblProfileName;

        public HeaderViewHolder(View rootView){
            super(rootView);
            /*
            this.mImgProfilePicture = new WeakReference<ImageView>(
                    (ImageView) rootView.findViewById(R.id.img_profile_picture));
            this.mLblProfileName = new WeakReference<TextView>(
                    (TextView) rootView.findViewById(R.id.lbl_profile_name));
            */
        }

    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private WeakReference<ImageView> mImgIcon;
        private WeakReference<TextView> mLblTitle;

        public MenuItemViewHolder(View rootView) {
            super(rootView);
            this.mImgIcon = new WeakReference<ImageView>(
                    (ImageView) rootView.findViewById(R.id.img_icon));
            this.mLblTitle = new WeakReference<TextView>(
                    (TextView) rootView.findViewById(R.id.lbl_title));
        }
    }

}

