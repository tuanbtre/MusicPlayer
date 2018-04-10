package com.example.ducph.musicplayer.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Duc Pham on 27/09/2016.
 */

public class MyViewPager extends ViewPager {
    private boolean enabled;
    public MyViewPager(Context context) {
        super(context);
        this.enabled = true;
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }
    private int mCurrentPagePosition = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        try {
//            View child = getChildAt(mCurrentPagePosition);
//            if (child != null) {
//                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                int h = child.getMeasuredHeight();
//                heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void reMeasureCurrentPage(int position) {
        mCurrentPagePosition = position;
        requestLayout();
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
