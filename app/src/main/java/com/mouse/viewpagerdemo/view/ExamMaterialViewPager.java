package com.mouse.viewpagerdemo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 考试题中材料展示的根布局
 * 主要用于拦截横向滑动的事件
 */
public class ExamMaterialViewPager extends ViewPager {
    private boolean canScroll = true;
    private boolean noScrollAnim = false;

    public ExamMaterialViewPager(@NonNull Context context) {
        super(context);
    }

    public ExamMaterialViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否能左右滑动
     *
     * @param canScroll true 能滑动
     */
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    /**
     * 设置没有滑动动画
     *
     * @param noAnim false 无动画
     */
    public void setScrollAnim(boolean noAnim) {
        this.noScrollAnim = noAnim;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (canScroll)
            return super.onTouchEvent(arg0);
        else
            return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (canScroll)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, noScrollAnim);
    }
}
