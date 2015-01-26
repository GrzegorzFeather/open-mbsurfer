package com.mbsurfer.ui.adapter;

import com.mbsurfer.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;


/**
 * Created by Jorge Méndez on 26/01/2015.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public SearchResultsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(this.mContext).inflate(0, parent, false);
        return new SearchResultViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private WeakReference<ImageView> mImgIcon;
        private WeakReference<TextView> mLblStation;

        public SearchResultViewHolder(View viewRoot) {
            super(viewRoot);
            this.mImgIcon = new WeakReference<ImageView>((ImageView)
                viewRoot.findViewById(R.id.img_icon));
            this.mLblStation = new WeakReference<TextView>((TextView)
                viewRoot.findViewById(R.id.lbl_station));
        }
    }

}
