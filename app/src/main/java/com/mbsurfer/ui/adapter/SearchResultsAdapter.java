package com.mbsurfer.ui.adapter;

import com.mbsurfer.R;
import com.mbsurfer.model.Station;

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
 * Created by Jorge Méndez on 26/01/2015.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View.OnClickListener mListener;
    private List<Station> mDataSet;

    public SearchResultsAdapter(View.OnClickListener listener) {
        this.mListener = listener;
        this.mDataSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search_result, parent, false);
        rootView.setOnClickListener(this.mListener);
        return new SearchResultViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Station station = this.getItem(position);
        SearchResultViewHolder searchHolder = (SearchResultViewHolder) holder;
        searchHolder.mImgIcon.get().setImageResource(station.getIconId());
        searchHolder.mLblStation.get().setText(station.getName());
        searchHolder.mLblLine.get().setText(station.getLine().toString());
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.size();
    }

    public void updateDataSet(List<Station> dataSet){
        this.mDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    public void clearDataSet(){
        this.mDataSet.clear();
        this.notifyDataSetChanged();
    }

    public Station getItem(int position){
        return this.mDataSet.get(position);
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private WeakReference<ImageView> mImgIcon;
        private WeakReference<TextView> mLblStation;
        private WeakReference<TextView> mLblLine;

        public SearchResultViewHolder(View viewRoot) {
            super(viewRoot);
            this.mImgIcon = new WeakReference<>((ImageView)
                viewRoot.findViewById(R.id.img_station));
            this.mLblStation = new WeakReference<>((TextView)
                viewRoot.findViewById(R.id.lbl_station));
            this.mLblLine = new WeakReference<>((TextView)
                viewRoot.findViewById(R.id.lbl_line));
        }
    }

}
