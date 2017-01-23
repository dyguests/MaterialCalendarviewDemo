package com.fanhl.materialcalendarviewdemo.model;

import java.util.Date;

/**
 * Created by fanhl on 2017/1/23.
 */
public class Report {
    private Date date;
    private float mileage;

    public Report() {
    }

    public Report(Date date, float mileage) {
        this.date = date;
        this.mileage = mileage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }
}
