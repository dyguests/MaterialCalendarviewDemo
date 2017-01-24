package com.fanhl.materialcalendarviewdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayView;

/**
 * 带里程的 DayView
 * <p>
 * Created by fanhl on 2017/1/24.
 */
@SuppressLint("ViewConstructor")
public class MileageDayView extends DayView {
    public MileageDayView(Context context, CalendarDay day, AttributeSet attrs) {
        super(context, day, attrs);
    }
}
