package com.fanhl.materialcalendarviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fanhl.materialcalendarviewdemo.widget.MileageCalendarBehavior;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private MileageCalendarBehavior calendarBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assignViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void assignViews() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialCalendarView.StateBuilder builder = calendarView.state().edit();
                if (calendarView.state().calendarMode == CalendarMode.MONTHS) {
                    builder.setCalendarDisplayMode(CalendarMode.WEEKS);
                } else {
                    builder.setCalendarDisplayMode(CalendarMode.MONTHS);
                }
                builder.commit();
            }
        });

        initCalendarView();
    }

    private void initCalendarView() {
        calendarView = ((MaterialCalendarView) findViewById(R.id.calendarView));
        calendarBehavior = MileageCalendarBehavior.from(calendarView);
    }

}
