package com.kevin.breatheezy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.breatheezy.R;
import com.kevin.breatheezy.preferences.GlobalPreferenceManager;
import com.kevin.breatheezy.view.CenteredToolbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class SettingsActivity extends AppCompatActivity {
    private MaterialEditText etName, etHeight, etWeight;
    private FancyButton btnSave;
    private RadioGroup radioGenderGroup;
    private RadioButton radioMale, radioFemale;

    boolean isMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("My Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etName = findViewById(R.id.etName);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        radioGenderGroup = findViewById(R.id.radioGenderGroup);
        radioFemale = findViewById(R.id.radioFemale);
        radioMale = findViewById(R.id.radioMale);

        etName.setText(GlobalPreferenceManager.getUserName());
        etHeight.setText(GlobalPreferenceManager.getHeight());
        etWeight.setText(GlobalPreferenceManager.getWeight());

        if(GlobalPreferenceManager.isGenderMale()){
            radioMale.setChecked(true);
        }else{
            radioFemale.setChecked(true);
        }

        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == radioMale.getId()){
                    isMale = true;
                }else{
                    isMale = false;
                }
            }
        });

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalPreferenceManager.setUserName(etName.getText().toString());
                GlobalPreferenceManager.setHeight(etHeight.getText().toString());
                GlobalPreferenceManager.setWeight(etWeight.getText().toString());
                if(isMale){
                    GlobalPreferenceManager.setGenderAsMale(true);
                }else{
                    GlobalPreferenceManager.setGenderAsMale(false);
                }

                finish();
            }
        });

    }
}
