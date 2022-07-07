package com.kevin.breatheezy.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arindam.alarmmanager.ArinAlarmController;
import com.arindam.alarmmanager.model.Alarm;
import com.arindam.bestdialog.BestChoiceDialog;
import com.arindam.bestdialog.BestDialogConstant;
import com.arindam.bestdialog.BestTextInputDialog;
import com.arindam.bestdialog.ViewConfigurator;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.alarm.SendNotification;
import com.kevin.breatheezy.database.AppDatabase;
import com.kevin.breatheezy.database.AppExecutors;
import com.kevin.breatheezy.database.DiaryEntity;
import com.kevin.breatheezy.database.TreatmentEntity;
import com.kevin.breatheezy.util.Constant;
import com.kevin.breatheezy.view.CenteredToolbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddTreatmentActivity extends AppCompatActivity {

    MaterialEditText etName, etPuffs, etTimes;
    FancyButton btnSave;

    int treatmentID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_add);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);

        String name = getIntent().getStringExtra("NAME");
        int puff = getIntent().getIntExtra("PUFF", -1);
        int times = getIntent().getIntExtra("TIMES", -1);
        treatmentID = getIntent().getIntExtra("ID", -1);

        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        if (treatmentID==-1) {
            toolbar.setTitle("Add Treatment");
        } else {
            toolbar.setTitle("Edit Treatment");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etName = findViewById(R.id.etName);
        etPuffs = findViewById(R.id.etPuffs);
        etTimes = findViewById(R.id.etTimes);
        btnSave = findViewById(R.id.btnSave);

        etPuffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String puffList[] = {"1", "2", "3", "4"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTreatmentActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, puffList);
                new BestChoiceDialog(AddTreatmentActivity.this,0,  BestDialogConstant.BOTTON_FOOTER_STYLE_ADVANCE)
                        .setTopColorRes(R.color.colorPrimary)
                        .setTopTitle("Choose number of puffs")
                        .setTopTitleColor(R.color.colorWhite)
                        .setTitleGravity(Gravity.CENTER)
                        .setItems(adapter, new BestChoiceDialog.OnItemSelectedListener<String>() {
                            @Override
                            public void onItemSelected(int position, String item) {
                                etPuffs.setText(item);
                            }
                        })
                        .show();
            }
        });

        etTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeList[] = {"1", "2", "3", "4"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTreatmentActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, timeList);
                new BestChoiceDialog(AddTreatmentActivity.this,0,  BestDialogConstant.BOTTON_FOOTER_STYLE_ADVANCE)
                        .setTopColorRes(R.color.colorPrimary)
                        .setTopTitle("How many times?")
                        .setTopTitleColor(R.color.colorWhite)
                        .setTitleGravity(Gravity.CENTER)
                        .setItems(adapter, new BestChoiceDialog.OnItemSelectedListener<String>() {
                            @Override
                            public void onItemSelected(int position, String item) {
                                etTimes.setText(item);
                            }
                        })
                        .show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTreatment();
            }
        });

        if(treatmentID != -1){
            etName.setText(name);
            etPuffs.setText(""+puff);
            etTimes.setText(""+times);
        }
    }

    public void saveTreatment() {
        if (etName.getText().toString().equals("") || etPuffs.getText().toString().equals("") || etTimes.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill all the boxes", Toast.LENGTH_SHORT).show();
        } else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    TreatmentEntity treatmentEntity = new TreatmentEntity(etName.getText().toString(), Integer.valueOf(etPuffs.getText().toString()), Integer.valueOf(etTimes.getText().toString()));
                    if(treatmentID != -1){
                        treatmentEntity.setId(treatmentID);
                        AppDatabase.getInstance(getApplicationContext()).treatmentDAO().updateTreatment(treatmentEntity);
                    }else {
                        AppDatabase.getInstance(getApplicationContext()).treatmentDAO().insertTreatment(treatmentEntity);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAllReminder();
                        }
                    });
                }
            });
        }
    }

    private void setAllReminder(){
        new ArinAlarmController().deleteAllAlarm(AddTreatmentActivity.this);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<TreatmentEntity> treatmentEntityList = AppDatabase.getInstance(AddTreatmentActivity.this).treatmentDAO().loadAllTreatment();
                for(int i=0; i<treatmentEntityList.size(); i++){
                    if(treatmentEntityList.get(i).getTimes() == 1){
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(), 6, 0);
                    }else if(treatmentEntityList.get(i).getTimes() == 2){
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),6, 0);
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),18, 0);
                    }else if(treatmentEntityList.get(i).getTimes() == 3){
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),6, 0);
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),12, 0);
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),18, 0);
                    }else if(treatmentEntityList.get(i).getTimes() == 4){
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),6, 0);
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),12, 0);
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),18, 0);
                        setReminder(treatmentEntityList.get(i).getName(), treatmentEntityList.get(i).getPuffs(),22, 0);
                    }
                }

                finish();
            }
        });
    }


    private void setReminder(String message, int puff, int hour, int min){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-1, hour, min, 0);

        Alarm alarm = new Alarm();
        alarm.setPeriodic(true);
        alarm.setPeriodicType(Alarm.TIMEUNIT_HOUR);
        alarm.setPeriodicValue(24);
        alarm.setPeriodicStartTime(calendar.getTimeInMillis());
        alarm.setTitle(getString(R.string.app_name));
        alarm.setMessage("Reminder for "+ message +"\nNo of puffs: "+puff);
        alarm.setAlarmHandleListener(SendNotification.class.getCanonicalName());
        alarm.setEnabled(true);

        new ArinAlarmController().setAlarm(AddTreatmentActivity.this, alarm);
    }
}

