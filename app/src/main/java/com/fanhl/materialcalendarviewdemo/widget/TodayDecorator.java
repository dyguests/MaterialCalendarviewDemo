package com.fanhl.materialcalendarviewdemo.widget;

import android.content.Context;

import com.fanhl.materialcalendarviewdemo.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

/**
 * Created by zmy on 2015/12/14 0014.
 * 为今日提供装饰器
 */
public class TodayDecorator implements DayViewDecorator
{
    private Context context;

    public TodayDecorator(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day)
    {
        Calendar calendar = Calendar.getInstance();
        return day.getYear() == calendar.get(Calendar.YEAR) &&
                day.getMonth() == calendar.get(Calendar.MONTH) &&
                day.getDay() == calendar.get(Calendar.DATE);
    }

    @Override
    public void decorate(DayViewFacade view)
    {
        //view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228BC34A")));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_light_blue));
    }
}
