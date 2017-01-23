package com.fanhl.materialcalendarviewdemo.widget;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Created by fanhl on 2017/1/23.
 */
public class MileageDayViewDecorator implements DayViewDecorator {

    private final Context context;

    public MileageDayViewDecorator(Context context) {
        this.context = context;
    }

    @Override public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override public void decorate(DayViewFacade view) {
//        view.addSpan();
    }
}
