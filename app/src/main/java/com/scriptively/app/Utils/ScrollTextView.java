package com.scriptively.app.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.scriptively.app.R;


@SuppressLint("AppCompatCustomView")
public class ScrollTextView extends TextView {

    private static final float DEFAULT_SPEED = 9.0f;
    public Scroller scroller;
    public float speed = DEFAULT_SPEED;
    public boolean continuousScrolling = true;
    public ScrollTextView(Context context) {
        super(context);
        init(null, 0);
        scrollerInstance(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        scrollerInstance(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
        scrollerInstance(context);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView,
                defStyleAttr, 0);
//        initAttributes(attrArray);
    }

//    protected void initAttributes(TypedArray attrArray) {
//        String textStyle = attrArray.getString(R.styleable.MyTextView_myTextStyle);
//        if (textStyle == null || textStyle.equals(null) || textStyle.equals("")) {
//
//        } else {
//            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), textStyle);
//            setTypeface(tf);
//        }

//    }

    public void scrollerInstance(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        setScroller(scroller);
    }

    @Override    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (scroller.isFinished()) {
            scroll();
        }
    }

    public void scroll() {
        int viewHeight = getHeight();
        int visibleHeight = viewHeight - getPaddingBottom() - getPaddingTop();
        int lineHeight = getLineHeight();
        int offset = -1 * visibleHeight;
        int distance = visibleHeight + getLineCount() * lineHeight;
        int duration = (int) (distance * speed);
        scroller.startScroll(0, offset, 0, distance, duration);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == scroller)
            return;
        if (scroller.isFinished() && continuousScrolling) {
            scroll();
        }
    }

    @Override    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == scroller)
            return;
        if (scroller.isFinished() && continuousScrolling) {
            scroll();
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }

    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }

    public void requestDisallowInterceptTouchEvent(boolean b) {
    }
}