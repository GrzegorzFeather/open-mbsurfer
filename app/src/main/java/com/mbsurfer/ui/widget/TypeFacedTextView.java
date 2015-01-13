package com.mbsurfer.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mbsurfer.R;

/**
 * Created by GrzegorzFeathers on 12/17/14.
 */
public class TypeFacedTextView extends TextView {

    public TypeFacedTextView(Context context) {
        this(context, null);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(this.isInEditMode() || attrs == null) {
            return;
        }

        this.customizeTypography(context, attrs);
    }

    private void customizeTypography(Context context, AttributeSet attrs){
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypeFacedView);
        String fontName = styledAttrs.getString(R.styleable.TypeFacedView_typeface);
        styledAttrs.recycle();

        if(fontName != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }
    }

}
