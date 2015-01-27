package com.mbsurfer.ui.widget;

import com.mbsurfer.R;
import com.mbsurfer.model.Line;
import com.mbsurfer.model.Station;
import com.mbsurfer.ui.adapter.SearchResultsAdapter;
import com.mbsurfer.util.MBSUtils;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by GrzegorzFeathers on 1/23/15.
 */
public class SlidingLinearLayout extends CardView implements View.OnClickListener {

    private static final String TAG = SlidingLinearLayout.class.getSimpleName();

    public enum Status {
        OPEN_FULL, FIRST_LEVEL, CLOSED;
    }

    private enum EditableField {
        FROM, TO;
    }

    private static final Status defaultStatus = Status.CLOSED;

    private RecyclerView mRecyclerSearchResults;
    private RecyclerView.Adapter mRecyclerSearchResultsAdapter;
    private RecyclerView.LayoutManager mRecyclerSearchResultsManager;

    private Station mFromStation = null;
    private TextView mLblFrom;
    private ImageView mImageFrom;
    private EditText mEditFrom;

    private Station mToStation = null;
    private TextView mLblTo;
    private ImageView mImageTo;
    private EditText mEditTo;

    private Status mCurrentStatus = defaultStatus;
    private float mFirstLevelTranslation = 0f;
    private float mFullTranslation = 0f;
    private boolean mIsFirstCreation = true;

    private EditableField mCurrentSearch = EditableField.TO;

    public SlidingLinearLayout(Context context) {
        this(context, null);
    }

    public SlidingLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_directions, this, true);

        this.mRecyclerSearchResults = (RecyclerView) this.findViewById(R.id.recycler_search_results);
        this.mRecyclerSearchResults.setHasFixedSize(true);
        this.mRecyclerSearchResultsAdapter = new SearchResultsAdapter(this);
        this.mRecyclerSearchResultsManager = new LinearLayoutManager(context);
        this.mRecyclerSearchResults.setAdapter(this.mRecyclerSearchResultsAdapter);
        this.mRecyclerSearchResults.setLayoutManager(this.mRecyclerSearchResultsManager);
        ((SearchResultsAdapter) this.mRecyclerSearchResultsAdapter).updateDataSet(Line.getFilteredStations(""));

        this.mLblFrom = (TextView) this.findViewById(R.id.lbl_from);
        this.mImageFrom = (ImageView) this.findViewById(R.id.img_from);
        this.mEditFrom = (EditText) this.findViewById(R.id.edit_from);
        this.mLblFrom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsEditable(mLblFrom, mEditFrom, EditableField.FROM);
                openFull();
            }
        });
        this.mEditFrom.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    setAsLabel(mLblFrom, mEditFrom);
                }
            }
        });
        this.mEditFrom.addTextChangedListener(new SearchTextWatcher());

        this.mLblTo = (TextView) this.findViewById(R.id.lbl_to);
        this.mImageTo = (ImageView) this.findViewById(R.id.img_to);
        this.mEditTo = (EditText) this.findViewById(R.id.edit_to);
        this.mLblTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsEditable(mLblTo, mEditTo, EditableField.TO);
                openFull();
            }
        });
        this.mEditTo.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    setAsLabel(mLblTo, mEditTo);
                }
            }
        });
        this.mEditTo.addTextChangedListener(new SearchTextWatcher());


        this.findViewById(R.id.btn_swap).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                swapStations();
            }
        });
        this.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        this.findViewById(R.id.btn_down).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openFirstLevel();
            }
        });
        this.findViewById(R.id.btn_travel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openFirstLevel();

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        MBSUtils.log(TAG, "onLayout: Current Status: " + this.mCurrentStatus);
        this.mFullTranslation = Math.abs(t - b);
        this.mFirstLevelTranslation = this.mFullTranslation - this.findViewById(R.id.layout_first_level).getHeight();
        if(this.mIsFirstCreation){
            this.mIsFirstCreation = false;
            switch (this.mCurrentStatus){
                case CLOSED:
                    MBSUtils.log(TAG, "onLayout - Will close");
                    this.close(false);
                    break;
                case FIRST_LEVEL:
                    MBSUtils.log(TAG, "onLayout - Will open first level");
                    this.openFirstLevel(false);
                    break;
                case OPEN_FULL:
                    MBSUtils.log(TAG, "onLayout - Will open full");
                    this.openFull(false);
                    break;
            }
        }
    }

    private void setAsEditable(TextView tv, final EditText et, EditableField field){
        tv.setVisibility(INVISIBLE);
        et.setVisibility(VISIBLE);
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                et.requestFocus();
                InputMethodManager imm = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);
        this.mCurrentSearch = field;
        ((SearchResultsAdapter) this.mRecyclerSearchResultsAdapter).updateDataSet(Line.getFilteredStations(""));
    }

    private void setAsLabel(TextView tv, EditText et){
        tv.setVisibility(VISIBLE);
        et.setVisibility(INVISIBLE);
        et.setText("");
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
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override public void onAnimationStart(View view) {}
                        @Override public void onAnimationCancel(View view) {}
                        @Override
                        public void onAnimationEnd(View view) {
                            setAsLabel(mLblFrom, mEditFrom);
                            setAsLabel(mLblTo, mEditTo);
                        }
                    })
                    .setInterpolator(AnimationUtils.loadInterpolator(
                            getContext(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                    ? android.R.interpolator.fast_out_slow_in
                                    : android.R.anim.decelerate_interpolator))
                    .start();
        } else {
            ViewCompat.setTranslationY(this, this.mFullTranslation);
            this.setAsLabel(this.mLblFrom, this.mEditFrom);
            this.setAsLabel(this.mLblTo, this.mEditTo);
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

        this.setAsLabel(this.mLblFrom, this.mEditFrom);
        this.setAsLabel(this.mLblTo, this.mEditTo);
        this.findViewById(R.id.btn_close).setVisibility(VISIBLE);
        this.findViewById(R.id.btn_down).setVisibility(GONE);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public void openFull(){
        this.openFull(true);
    }

    private void openFull(boolean animate){
        this.mCurrentStatus = Status.OPEN_FULL;
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

        this.findViewById(R.id.btn_close).setVisibility(GONE);
        this.findViewById(R.id.btn_down).setVisibility(VISIBLE);
    }

    public void setFromStation(Station from){
        this.setFromStation(from, true);
    }

    public void setFromStation(Station from, boolean animate) {
        if(from == null || from.equals(this.mToStation)){
            Toast.makeText(this.getContext(), "Already set as destination", Toast.LENGTH_SHORT).show();
            return;
        }
        this.mFromStation = from;
        this.setAsLabel(this.mLblFrom, this.mEditFrom);
        this.setupDirections();
        this.openFirstLevel(animate);
    }

    public void setToStation(Station to) {
        this.setToStation(to, true);
    }

    public void setToStation(Station to, boolean animate) {
        if(to == null || to.equals(this.mFromStation)){
            Toast.makeText(this.getContext(), "Already set as origin", Toast.LENGTH_SHORT).show();
            return;
        }
        this.mToStation = to;
        this.setAsLabel(this.mLblTo, this.mEditTo);
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

        if(this.mFromStation != null && this.mToStation != null){
            this.findViewById(R.id.btn_travel).setVisibility(VISIBLE);
        } else {
            this.findViewById(R.id.btn_travel).setVisibility(INVISIBLE);
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

    public boolean isOpen(){
        return this.mCurrentStatus.equals(Status.OPEN_FULL);
    }

    public boolean isClosed(){
        return this.mCurrentStatus.equals(Status.CLOSED);
    }

    public boolean isFirstLevel(){
        return this.mCurrentStatus.equals(Status.FIRST_LEVEL);
    }

    public Status getCurrentStatus(){
        return this.mCurrentStatus;
    }

    @Override
    public void onClick(View v) {
        int position = this.mRecyclerSearchResults.getChildPosition(v);

        switch (this.mCurrentSearch){
            case FROM:
                this.setFromStation(((SearchResultsAdapter) this.mRecyclerSearchResultsAdapter).getItem(position));
                break;
            case TO:
                this.setToStation(((SearchResultsAdapter)this.mRecyclerSearchResultsAdapter).getItem(position));
                break;
        }

        this.openFirstLevel();
    }

    private class SearchTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if(s.toString().trim().isEmpty()){
                return;
            }
            post(new Runnable() {
                @Override
                public void run() {
                    ((SearchResultsAdapter) mRecyclerSearchResultsAdapter).updateDataSet(
                            Line.getFilteredStations(s.toString()));
                }
            });
        }
    }

}
