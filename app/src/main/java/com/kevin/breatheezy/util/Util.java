package com.kevin.breatheezy.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static boolean compareToDay(Date date1, Date date2){
        if(date1 ==null || date2 ==null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Log.i("Kevin", sdf.format(date1) + " "+ sdf.format(date2));
        return sdf.format(date1).equals(sdf.format(date2));
    }
}

