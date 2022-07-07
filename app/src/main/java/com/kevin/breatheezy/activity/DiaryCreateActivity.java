package com.kevin.breatheezy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.database.AppDatabase;
import com.kevin.breatheezy.database.AppExecutors;
import com.kevin.breatheezy.database.DiaryEntity;
import com.kevin.breatheezy.database.LungFunctionEntity;
import com.kevin.breatheezy.util.Constant;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class DiaryCreateActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    RadioButton radioYes1, radioYes2, radioYes3, radioYes4, radioYes5;
    RadioButton radioNo1, radioNo2, radioNo3, radioNo4, radioNo5;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    FancyButton btnSave;
    boolean answer1, answer2, answer3, answer4, answer5;
    int savedDiaryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_create);

        radioYes1 = findViewById(R.id.radioYes1);
        radioYes2 = findViewById(R.id.radioYes2);
        radioYes3 = findViewById(R.id.radioYes3);
        radioYes4 = findViewById(R.id.radioYes4);
        radioYes5 = findViewById(R.id.radioYes5);
        radioNo1 = findViewById(R.id.radioNo1);
        radioNo2 = findViewById(R.id.radioNo2);
        radioNo3 = findViewById(R.id.radioNo3);
        radioNo4 = findViewById(R.id.radioNo4);
        radioNo5 = findViewById(R.id.radioNo5);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup5 = findViewById(R.id.radioGroup5);
        btnSave = findViewById(R.id.btnSave);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("Asthma Symptom Diary");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radioGroup1.setOnCheckedChangeListener(this);
        radioGroup2.setOnCheckedChangeListener(this);
        radioGroup3.setOnCheckedChangeListener(this);
        radioGroup4.setOnCheckedChangeListener(this);
        radioGroup5.setOnCheckedChangeListener(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int result = calculateResult();
                if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Please answer all questions", Toast.LENGTH_SHORT).show();
                } else {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            DiaryEntity diaryEntity = new DiaryEntity(result, new Date());
                            if(savedDiaryID == -1) {
                                AppDatabase.getInstance(getApplicationContext()).diaryDAO().insertDiary(diaryEntity);
                            } else {
                                diaryEntity.setId(savedDiaryID);
                                AppDatabase.getInstance(getApplicationContext()).diaryDAO().updateDiary(diaryEntity);
                            }
                            finish();
                        }
                    });
                }
            }
        });
    }

    public int calculateResult () {
        if (radioGroup1.getCheckedRadioButtonId() == -1 || radioGroup2.getCheckedRadioButtonId() == -1 || radioGroup3.getCheckedRadioButtonId() == -1 || radioGroup4.getCheckedRadioButtonId() == -1 || radioGroup5.getCheckedRadioButtonId() == -1) {
            return -1;
        } else if (!answer1 && !answer2 && !answer3 && !answer4 && !answer5) {
            return Constant.DIARY_COLOUR_GREEN;
        } else if (answer1 || answer2 || answer3 || answer4 || answer5) {
            return Constant.DIARY_COLOUR_RED;
        } else {
            return -1;
        }
    }

    public void checkTodayDiaryData () {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH), 0, 0);
                endCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH), 23, 59);
                final List<DiaryEntity> diaryEntityList = AppDatabase.getInstance(getApplicationContext()).diaryDAO().loadAllDiaryByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                if(diaryEntityList.size() != 0) {
                    savedDiaryID = diaryEntityList.get(0).getId();
                } else {
                    savedDiaryID = -1;
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == radioGroup1) {
            if(checkedId == radioYes1.getId()) {
                answer1 = true;
            } else {
                answer1 = false;
            }
        }
        if (group == radioGroup2) {
            if(checkedId == radioYes2.getId()) {
                answer2 = true;
            } else {
                answer2 = false;
            }
        }
        if (group == radioGroup3) {
            if(checkedId == radioYes3.getId()) {
                answer3 = true;
            } else {
                answer3 = false;
            }
        }
        if (group == radioGroup4) {
            if(checkedId == radioYes4.getId()) {
                answer4 = true;
            } else {
                answer4 = false;
            }
        }
        if (group == radioGroup5) {
            if(checkedId == radioYes5.getId()) {
                answer5 = true;
            } else {
                answer5 = false;
            }
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        checkTodayDiaryData();
    }
}
