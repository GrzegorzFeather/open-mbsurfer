package com.mbsurfer.ui.widget;

import com.mbsurfer.R;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by GrzegorzFeathers on 1/5/15.
 */
public class MBSToolbar extends RelativeLayout {

    private Toolbar mToolbar;
    private TextView mLblTitle;
    private TextView mLblSubtitle;

    public MBSToolbar(Context context) {
        this(context, null);
    }

    public MBSToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MBSToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true);

        this.mToolbar = (Toolbar) this.findViewById(R.id.toolbar_comp);
        this.mLblTitle = (TextView) this.findViewById(R.id.lbl_title_comp);
        this.mLblSubtitle = (TextView) this.findViewById(R.id.lbl_subtitle_comp);
    }

    public void setTitle(int resId) {
        this.mLblTitle.setText(resId);
    }

    public void setTitle(CharSequence subtitle) {
        this.mLblTitle.setText(subtitle);
    }

    public void setTitleColor(int color){
        this.mLblTitle.setTextColor(color);
    }

    public void setSubtitle(int resId) {
        this.mLblSubtitle.setText(resId);
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mLblSubtitle.setText(subtitle);
    }

    public void setSubtitleColor(int color){
        this.mLblSubtitle.setTextColor(color);
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener){
        this.mToolbar.setOnMenuItemClickListener(listener);
    }

    public Toolbar getToolbarComp(){
        return this.mToolbar;
    }

}
