package com.mbsurfer.ui.widget;

import com.mbsurfer.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by GrzegorzFeathers on 1/6/15.
 */
public class TypeFacedEditText extends EditText {

    public TypeFacedEditText(Context context) {
        this(context, null);
    }

    public TypeFacedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeFacedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(this.isInEditMode() || attrs == null) {
            return;
        }

        this.customizeTypography(context, attrs);
    }

    private void customizeTypography(Context context, AttributeSet attrs){
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypeFacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypeFacedTextView_typeface);
        styledAttrs.recycle();

        if(fontName != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }
    }
}
