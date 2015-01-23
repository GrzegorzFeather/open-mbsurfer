package com.mbsurfer.ui.widget;

import com.mbsurfer.R;
import com.mbsurfer.model.Station;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
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

    private enum Status {
        OPEN, CLOSED;
    }

    private static final Status defaultStatus = Status.CLOSED;

    private Station mFromStation = null;
    private TextView mLblFrom;
    private ImageView mImageFrom;

    private Station mToStation = null;
    private TextView mLblTo;
    private ImageView mImageTo;

    private Status mCurrentStatus = defaultStatus;
    private float mTranslation = 0f;
    private boolean mIsFirstCreation = true;

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
        this.mTranslation = Math.abs(t - b);
        if(this.mIsFirstCreation){
            this.mIsFirstCreation = false;
            this.close(false);
        }
    }

    public void close(){
        this.close(true);
    }

    private void close(boolean animate){
        if(animate){
            ViewCompat.animate(this)
                    .translationY(this.mTranslation)
                    .setDuration(300)
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {

                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            mCurrentStatus = Status.CLOSED;
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    })
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, this.mTranslation);
            this.mCurrentStatus = Status.CLOSED;
        }
    }

    public void open(){
        this.open(true);
    }

    private void open(boolean animate){
        if(animate){
            ViewCompat.animate(this)
                    .translationY(5f)
                    .setDuration(300)
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {

                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            mCurrentStatus = Status.OPEN;
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    })
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, 5f);
            mCurrentStatus = Status.OPEN;
        }
    }

    public void setFromStation(Station from){
        this.mFromStation = from;
        this.setupDirections();
        this.open();
    }

    public void setToStation(Station to) {
        this.mToStation = to;
        this.setupDirections();
        this.open();
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
