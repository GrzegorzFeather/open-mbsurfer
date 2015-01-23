package com.mbsurfer.ui.widget;

import com.mbsurfer.util.MBSUtils;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;


/**
 * Created by GrzegorzFeathers on 1/23/15.
 */
public class SlidingLinearLayout extends LinearLayout {

    private enum Status {
        OPEN, SLIDING, CLOSED;
    }

    private static final Status defaultStatus = Status.CLOSED;

    private Status mCurrentStatus = defaultStatus;

    public SlidingLinearLayout(Context context) {
        this(context, null);
    }

    public SlidingLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.close(false);
    }

    public void close(){
        this.close(true);
    }

    private void close(boolean animate){
        if(animate){
            ViewCompat.animate(this)
                    .translationY(MBSUtils.getActionBarSize(getContext()))
                    .setDuration(300)
                    .setListener(null)
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, MBSUtils.getActionBarSize(getContext()));
        }
    }

    public void open(){
        this.open(true);
    }

    private void open(boolean animate){
        if(animate){
            ViewCompat.animate(this)
                    .translationY(0f)
                    .setDuration(300)
                    .setListener(null)
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, 0f);
        }
    }

}
