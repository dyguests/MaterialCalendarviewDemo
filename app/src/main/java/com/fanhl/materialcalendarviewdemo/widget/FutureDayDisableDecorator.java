package com.fanhl.materialcalendarviewdemo.widget;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

/**
 * Created by fanhl on 2017/1/24.
 */

public class FutureDayDisableDecorator implements DayViewDecorator {
    @Override public boolean shouldDecorate(CalendarDay day) {
        Calendar calendar = Calendar.getInstance();
        boolean b1 = day.getYear() > calendar.get(Calendar.YEAR);
        boolean b2 = day.getYear() == calendar.get(Calendar.YEAR) && day.getMonth() > calendar.get(Calendar.MONTH);
        boolean b3 = day.getYear() == calendar.get(Calendar.YEAR) && day.getMonth() == calendar.get(Calendar.MONTH) && day.getDay() > calendar.get(Calendar.DATE);
        return b1 || b2 || b3;
    }

    @Override public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }
}
