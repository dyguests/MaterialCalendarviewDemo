package com.fanhl.materialcalendarviewdemo.widget;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fanhl.materialcalendarviewdemo.model.Report;
import com.fanhl.materialcalendarviewdemo.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 让 MaterialCalendarView 实现 显示里程的功能
 * <p>
 * Created by fanhl on 2017/1/23.
 */
public class MileageCalendarBehavior {
    public static final String TAG = MileageCalendarBehavior.class.getSimpleName();

    private final MaterialCalendarView calendarView;

    private Set<String> loadingMonths;
    private Set<String> loadedMonths;
    /**
     * yyyy-MM-dd ,
     */
    private HashMap<String, Report> reportMap;
    private DateFormatDayFormatter dayFormatter;

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
            @SuppressLint("DefaultLocale") @NonNull @Override
            public String format(@NonNull CalendarDay day) {
                String dayStr = super.format(day);

                Report report = reportMap.get(DateUtil.date2short(day.getDate()));
                String mileageStr = "test";
                if (report != null) {
                    mileageStr = String.format("%.1fkm", report.getMileage() / 1000);
                }

                return dayStr + "\n" + mileageStr;
            }
        });
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d(TAG, "onDateSelected date:" + date);
            }
        });

        loadingMonths = new HashSet<>();
        loadedMonths = new HashSet<>();
        reportMap = new HashMap<>();
        dayFormatter = new DateFormatDayFormatter() {
            @SuppressLint("DefaultLocale") @NonNull @Override
            public String format(@NonNull CalendarDay day) {
                String dayStr = super.format(day);

                Report report = reportMap.get(DateUtil.date2short(day.getDate()));
                String mileageStr = "test";
                if (report != null) {
                    mileageStr = String.format("%.1fkm", report.getMileage() / 1000);
                }

                return dayStr + "\n" + mileageStr;
            }
        };

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay calendarDay) {
                Date date = calendarDay.getDate();
                String dateStr = DateUtil.date2short(date);
                if (loadedMonths.contains(dateStr)) {
                    return;
                }
                if (loadingMonths.contains(dateStr)) {
                    return;
                }
                loadingMonths.add(dateStr);

                loadMonthData(date);
            }
        });
        loadMonthData(new Date());
    }

    private void loadMonthData(final Date date) {
        new AsyncTask<Void, Void, List<Report>>() {
            @Override protected List<Report> doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                loadingMonths.remove(DateUtil.date2short(date));

                Date beginDate = DateUtil.getFirstDayInMonth(date);
                int daysInMonth = DateUtil.getDaysInMonth(date);

                ArrayList<Report> reports = new ArrayList<>();
                for (int i = 0; i < daysInMonth; i++) {
                    reports.add(new Report(beginDate, new Random().nextFloat() * 100000));
                    beginDate = DateUtil.addDay(beginDate, 1);
                }

                return reports;
            }

            @Override protected void onPostExecute(List<Report> reports) {
                loadedMonths.add(DateUtil.date2short(date));
                for (Report report : reports) {
                    reportMap.put(DateUtil.date2short(report.getDate()), report);
                }
                onMonthReportLoaded(date);
            }
        }.execute();
    }

    private void onMonthReportLoaded(Date date) {
        calendarView.setDayFormatter(dayFormatter);
    }
}
