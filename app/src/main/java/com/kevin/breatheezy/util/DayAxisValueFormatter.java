package com.kevin.breatheezy.util;

import android.annotation.SuppressLint;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayAxisValueFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        calendar.add(Calendar.DATE, (int)value);
        return simpleDateFormat.format(calendar.getTime());


    }
}
