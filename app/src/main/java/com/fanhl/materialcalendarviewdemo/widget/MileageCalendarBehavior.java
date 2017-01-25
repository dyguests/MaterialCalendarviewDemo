package com.fanhl.materialcalendarviewdemo.widget;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import com.fanhl.materialcalendarviewdemo.R;
import com.fanhl.materialcalendarviewdemo.model.Report;
import com.fanhl.materialcalendarviewdemo.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.DayViewProvider;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.MileageDayView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
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
        //设置日期最大值
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMaximumDate(calendar.getTime())
                .commit();

        dayFormatter = new DateFormatDayFormatter() {
            @SuppressLint("DefaultLocale") @NonNull @Override
            public String format(@NonNull CalendarDay day) {
                String dayStr = super.format(day);

                Report report = reportMap.get(DateUtil.date2short(day.getDate()));
                String mileageStr = " ";
                if (report != null) {
                    mileageStr = meterToMileageString(report.getMileage());
                }

                return dayStr + "\n" + mileageStr;
            }
        };

        calendarView.setTopbarVisible(false);
        calendarView.setWeekDayTextAppearance(R.style.weekday_style, R.style.weekend_style);
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(calendarView.getContext().getResources().getTextArray(R.array.custom_weekdays)));

        calendarView.setDayFormatter(dayFormatter);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d(TAG, "onDateSelected date:" + date);
            }
        });

        loadingMonths = new HashSet<>();
        loadedMonths = new HashSet<>();
        reportMap = new HashMap<>();
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
        loadMonthData(DateUtil.getFirstDayInMonth(new Date()));

        calendarView.removeDecorators();
        calendarView.addDecorator(new TodayDecorator(calendarView.getContext()));
        calendarView.addDecorator(new DayViewDecorator() {
            @Override public boolean shouldDecorate(CalendarDay day) {
                return true;
            }

            @Override public void decorate(DayViewFacade view) {
                view.addSpan(new RelativeSizeSpan(1.4f));
            }
        });
        calendarView.addDecorator(new FutureDayDisableDecorator());

        calendarView.setDayViewProvider(new DayViewProvider() {
            @Override public DayView getDayView(CalendarDay calendarDay) {
                return new MileageDayView(calendarView.getContext(), calendarDay, null);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String meterToMileageString(float meters) {
        float km = meters / 1000f;
        String mileageString;
        if (km < 100) {
            mileageString = String.format("%.1fkm", km);
        } else {
            mileageString = String.format("%dkm", (int) km);
        }

        return mileageString;
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
        //calendarView.setDayFormatter(dayFormatter);
        calendarView.refreshMonthView(date);
    }
}
