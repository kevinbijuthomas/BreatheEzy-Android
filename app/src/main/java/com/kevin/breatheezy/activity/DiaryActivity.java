package com.kevin.breatheezy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.database.AppDatabase;
import com.kevin.breatheezy.database.AppExecutors;
import com.kevin.breatheezy.database.DiaryEntity;
import com.kevin.breatheezy.util.Constant;
import com.kevin.breatheezy.util.Util;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryActivity extends AppCompatActivity{
    private CalendarView calendarView;
    private FloatingActionButton fabAdd;

    List<EventDay> events = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        calendarView = findViewById(R.id.calendarView);
        fabAdd = findViewById(R.id.fabAdd);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("My Asthma Control");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiaryActivity.this, DiaryCreateActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_YEAR, -30);
        Calendar endCal = Calendar.getInstance();

        calendarView.setMinimumDate(startCal);
        calendarView.setMaximumDate(endCal);

        loadDiaryData();
    }

    private void loadDiaryData(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Calendar startCal = Calendar.getInstance();
                startCal.add(Calendar.DAY_OF_YEAR, -29);

                Calendar endCal = Calendar.getInstance();
                final List<DiaryEntity> diaryEntityList = AppDatabase.getInstance(getApplicationContext())
                        .diaryDAO().loadAllDiaryByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        events.clear();
                        boolean matched = false;
                        for(Date date=startCal.getTime(); startCal.before(endCal); startCal.add(Calendar.DATE, 1), date = startCal.getTime()){
                            for(int i =0; i< diaryEntityList.size(); i++){
                                if(Util.compareToDay(diaryEntityList.get(i).getSavedDate(), date)){
                                    matched = true;
                                    break;
                                }
                            }
                            if(!matched){
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                events.add(new EventDay(calendar, R.drawable.ico_circle_white));
                            }
                            matched = false;
                        }

                        for(int i =0; i< diaryEntityList.size(); i++){
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(diaryEntityList.get(i).getSavedDate());
                            if(diaryEntityList.get(i).getColor() == Constant.DIARY_COLOUR_GREEN){
                                events.add(new EventDay(calendar, R.drawable.ico_circle_green));
                            }else if(diaryEntityList.get(i).getColor() == Constant.DIARY_COLOUR_RED){
                                events.add(new EventDay(calendar, R.drawable.ico_circle_red));
                            }
                        }

                        if(events.size()!=0) {
                            calendarView.setEvents(events);
                        }
                    }
                });
            }
        });
    }
}
