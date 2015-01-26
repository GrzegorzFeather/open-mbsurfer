package com.mbsurfer.ui.widget;

import com.mbsurfer.R;
import com.mbsurfer.model.Station;
import com.mbsurfer.util.MBSUtils;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by GrzegorzFeathers on 1/23/15.
 */
public class SlidingLinearLayout extends CardView {

    private static final String TAG = SlidingLinearLayout.class.getSimpleName();

    private enum Status {
        OPEN, FIRST_LEVEL, CLOSED;
    }

    private static final Status defaultStatus = Status.CLOSED;

    private Station mFromStation = null;
    private TextView mLblFrom;
    private ImageView mImageFrom;

    private Station mToStation = null;
    private TextView mLblTo;
    private ImageView mImageTo;

    private Status mCurrentStatus = defaultStatus;
    private float mFirstLevelTranslation = 0f;
    private float mFullTranslation = 0f;

    public SlidingLinearLayout(Context context) {
        this(context, null);
    }

    public SlidingLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_directions, this, true);

        this.mLblFrom = (TextView) this.findViewById(R.id.lbl_from);
        this.mImageFrom = (ImageView) this.findViewById(R.id.img_from);

        this.mLblTo = (TextView) this.findViewById(R.id.lbl_to);
        this.mImageTo = (ImageView) this.findViewById(R.id.img_to);

        this.findViewById(R.id.btn_swap).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                swapStations();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        MBSUtils.log(TAG, "onLayout: Current Status: " + this.mCurrentStatus);
        this.mFullTranslation = Math.abs(t - b);
        this.mFirstLevelTranslation = this.mFullTranslation - this.findViewById(R.id.layout_first_level).getHeight();
        switch (this.mCurrentStatus){
            case CLOSED:
                MBSUtils.log(TAG, "onLayout - Will close");
                this.open(false);
                break;
            case FIRST_LEVEL:
                MBSUtils.log(TAG, "onLayout - Will open first level");
                this.openFirstLevel(false);
                break;
            case OPEN:
                MBSUtils.log(TAG, "onLayout - Will open full");
                this.close(false);
                break;
        }
    }

    public void close(){
        this.close(true);
    }

    private void close(boolean animate){
        this.mCurrentStatus = Status.CLOSED;
        if(animate){
            ViewCompat.animate(this)
                    .translationY(this.mFullTranslation)
                    .setDuration(300)
                    .setListener(null)
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, this.mFullTranslation);
        }
    }

    public void openFirstLevel(){
        this.openFirstLevel(true);
    }

    private void openFirstLevel(boolean animate){
        MBSUtils.log(TAG, "openFirstLevel: Current Status: " + this.mCurrentStatus
                + ", First Level Translation: " + this.mFirstLevelTranslation);

        this.mCurrentStatus = Status.FIRST_LEVEL;
        if(animate){
            ViewCompat.animate(this)
                    .translationY(this.mFirstLevelTranslation)
                    .setDuration(300)
                    .setListener(null)
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, this.mFirstLevelTranslation);
        }
    }

    public void open(){
        this.open(true);
    }

    private void open(boolean animate){
        this.mCurrentStatus = Status.OPEN;
        if(animate){
            ViewCompat.animate(this)
                    .translationY(5f)
                    .setDuration(300)
                    .setListener(null)
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, 5f);
        }
    }

    public void setFromStation(Station from){
        this.setFromStation(from, true);
    }

    public void setFromStation(Station from, boolean animate) {
        this.mFromStation = from;
        this.setupDirections();
        this.openFirstLevel(animate);
    }

    public void setToStation(Station to) {
        this.setToStation(to, true);
    }

    public void setToStation(Station to, boolean animate) {
        this.mToStation = to;
        this.setupDirections();
        this.openFirstLevel(animate);
    }

    private void setupDirections(){
        if(this.mFromStation != null){
            this.mLblFrom.setText(this.mFromStation.getName());
            this.mImageFrom.setImageResource(this.mFromStation.getIconId());
        } else {
            this.clearFrom();
        }

        if(this.mToStation != null){
            this.mLblTo.setText(this.mToStation.getName());
            this.mImageTo.setImageResource(this.mToStation.getIconId());
        } else {
            this.clearTo();
        }
    }

    public void clearFrom(){
        this.mFromStation = null;
        this.mLblFrom.setText("");
        this.mImageFrom.setImageResource(R.drawable.ic_search);
    }

    public void clearTo(){
        this.mToStation = null;
        this.mLblTo.setText("");
        this.mImageTo.setImageResource(R.drawable.ic_search);
    }

    public void clearDirections(){
        this.clearFrom();
        this.clearTo();
    }

    private void swapStations(){
        Station temp = this.mFromStation;
        this.mFromStation = this.mToStation;
        this.mToStation = temp;
        this.setupDirections();
    }

    public boolean isOpened(){
        return this.mCurrentStatus.equals(Status.OPEN);
    }

    public boolean isClosed(){
        return this.mCurrentStatus.equals(Status.CLOSED);
    }

}
