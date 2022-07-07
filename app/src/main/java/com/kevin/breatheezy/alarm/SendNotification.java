package com.kevin.breatheezy.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.arindam.alarmmanager.ArinAlarmController;
import com.arindam.alarmmanager.listener.AlarmHandleListener;
import com.arindam.alarmmanager.notification.AlarmNotification;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.kevin.breatheezy.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SendNotification extends AlarmHandleListener {

    @Override
    public void onHandle(Context context, Intent intent) {
        if(intent == null){
            return;
        }

        long alarmId = intent.getLongExtra(ArinAlarmController.EXTRA_ALARM_ID, -1);
        String title = intent.getStringExtra(ArinAlarmController.EXTRA_TITLE);
        String text = intent.getStringExtra(ArinAlarmController.EXTRA_MESSAGE);

        AlarmNotification alarmNotification = new AlarmNotification(context);
        alarmNotification.createNotification((int)alarmId, title, text, new Intent(context, MainActivity.class));
    }
}
