package com.fanhl.materialcalendarviewdemo.widget;

import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;

import java.util.Calendar;

/**
 * 让 MaterialCalendarView 实现 显示里程的功能
 * <p>
 * Created by fanhl on 2017/1/23.
 */
public class MileageCalendarBehavior {

    private final MaterialCalendarView calendarView;

    public static MileageCalendarBehavior from(MaterialCalendarView calendarView) {
        return new MileageCalendarBehavior(calendarView);
    }

    private MileageCalendarBehavior(MaterialCalendarView calendarView) {
        this.calendarView = calendarView;
        init();
    }

    private void init() {
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .commit();
        calendarView.setDayFormatter(new DateFormatDayFormatter() {
            @NonNull @Override public String format(@NonNull CalendarDay day) {
                return super.format(day) + "\ntest";
            }
        });
    }
}
