package com.mbsurfer.ui.widget;

import com.mbsurfer.R;
import com.mbsurfer.util.MBSUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by GrzegorzFeathers on 1/13/15.
 */
public class FloatLabelEditText extends LinearLayout {

    private static final long ANIMATION_DURATION = 150;

    private static final float DEFAULT_LABEL_PADDING_LEFT = 3f;
    private static final float DEFAULT_LABEL_PADDING_TOP = 4f;
    private static final float DEFAULT_LABEL_PADDING_RIGHT = 3f;
    private static final float DEFAULT_LABEL_PADDING_BOTTOM = 4f;

    private CharSequence mHintText;
    private Interpolator mInterpolator;

    private TextView mLabel;
    private EditText mEditText;

    public FloatLabelEditText(Context context) {
        this(context, null);
    }

    public FloatLabelEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLabelEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_float_label_edit_text, this, true);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatLabelEditText);

        int leftPadding = a.getDimensionPixelSize(
                R.styleable.FloatLabelEditText_floatEditTextPaddingLeft,
                MBSUtils.dipsToPix(context, DEFAULT_LABEL_PADDING_LEFT));
        int rightPadding = a.getDimensionPixelSize(
                R.styleable.FloatLabelEditText_floatEditTextPaddingRight,
                MBSUtils.dipsToPix(context, DEFAULT_LABEL_PADDING_RIGHT));
        int topPadding = a.getDimensionPixelSize(
                R.styleable.FloatLabelEditText_floatEditTextPaddingTop,
                MBSUtils.dipsToPix(context, DEFAULT_LABEL_PADDING_TOP));
        int bottomPadding = a.getDimensionPixelSize(
                R.styleable.FloatLabelEditText_floatEditTextPaddingBottom,
                MBSUtils.dipsToPix(context, DEFAULT_LABEL_PADDING_BOTTOM));

        this.mHintText = a.getText(R.styleable.FloatLabelEditText_floatEditTextHint);
        this.mLabel = (TextView) this.findViewById(R.id.lbl_float_hint);
        this.mEditText = (EditText) this.findViewById(R.id.edit_content);
        this.setupEditText();

        this.mLabel.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        this.mLabel.setTextAppearance(context, a.getResourceId(
                R.styleable.FloatLabelEditText_floatEditTextAppearance,
                R.style.TextAppearance_MBS_FloatLabel));
        this.mLabel.setVisibility(INVISIBLE);
        this.mLabel.setText(this.mHintText);

        ViewCompat.setPivotX(this.mLabel, 0f);
        ViewCompat.setPivotY(this.mLabel, 0f);

        a.recycle();

        this.mInterpolator = AnimationUtils.loadInterpolator(
                context, Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                        ? android.R.interpolator.fast_out_slow_in
                        : android.R.anim.decelerate_interpolator);
    }

    private void setupEditText(){
        this.updateLabelVisibility(false);

        this.mEditText.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateLabelVisibility(true);
            }
        });

        this.mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                updateLabelVisibility(true);
            }
        });
    }

    private void updateLabelVisibility(boolean animate){
        boolean hasText = !TextUtils.isEmpty(this.mEditText.getText());
        boolean isFocused = this.mEditText.isFocused();

        this.mLabel.setActivated(isFocused);

        if(hasText || isFocused){
            if(this.mLabel.getVisibility() != VISIBLE){
                this.showLabel(animate);
            }
        } else {
            if(this.mLabel.getVisibility() == VISIBLE){
                this.hideLabel(animate);
            }
        }
    }

    private void showLabel(boolean animate){
        if(animate){
            this.mLabel.setVisibility(VISIBLE);
            ViewCompat.setTranslationY(this.mLabel, this.mLabel.getHeight());

            float scale = this.mEditText.getTextSize() / this.mLabel.getTextSize();
            ViewCompat.setScaleX(this.mLabel, scale);
            ViewCompat.setScaleY(this.mLabel, scale);

            ViewCompat.animate(this.mLabel)
                    .translationY(0f)
                    .scaleY(1f)
                    .scaleX(1f)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(null)
                    .setInterpolator(this.mInterpolator).start();
        } else {
            this.mLabel.setVisibility(VISIBLE);
        }

        this.mEditText.setHint(null);
    }

    private void hideLabel(boolean animate){
        if(animate) {
            float scale = this.mEditText.getTextSize() / this.mLabel.getTextSize();
            ViewCompat.setScaleX(this.mLabel, 1f);
            ViewCompat.setScaleY(this.mLabel, 1f);
            ViewCompat.setTranslationY(this.mLabel, 0f);

            ViewCompat.animate(this.mLabel)
                    .translationY(this.mLabel.getHeight())
                    .setDuration(ANIMATION_DURATION)
                    .scaleX(scale)
                    .scaleY(scale)
                    .setInterpolator(this.mInterpolator)
                    .setListener(new ViewPropertyAnimatorListenerAdapter(){
                        @Override
                        public void onAnimationEnd(View view) {
                            mLabel.setVisibility(INVISIBLE);
                            mEditText.setHint(mHintText);
                        }
                    }).start();
        } else {
            this.mLabel.setVisibility(INVISIBLE);
            this.mEditText.setHint(this.mHintText);
        }
    }

}
