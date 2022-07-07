package com.kevin.breatheezy.activity.about;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.breatheezy.R;
import com.kevin.breatheezy.preferences.GlobalPreferenceManager;
import com.kevin.breatheezy.view.CenteredToolbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class AboutTriggersActivity extends AppCompatActivity {

    private TextView tvDesc;
    private String desc = "Patients with asthma may be allergic to  " +
            "inhaled or ingested allergens. Common  " +
            "inhaled allergens include house dust  " +
            "mite, pollen, cat, dog, animal dander and  " +
            "cockroach. Some patients with asthma  " +
            "may also have allergy to certain types of  " +
            "food (e.g. nuts)  " +
            "Triggers of asthma may vary from person  " +
            "to person. In general, these include  " +
            "respiratory infections (e.g. flu), exposure  " +
            "to smoking or pollution, cold weather,  " +
            "cold drinks, exercise and exposure to  " +
            "allergens.  " +
            "Recognizing and avoiding personal  " +
            "triggers and allergens may help achieve  " +
            "better asthma control.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_triggers);

        String title = getIntent().getStringExtra("TITLE");

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvDesc = findViewById(R.id.tvDesc);
        tvDesc.setText(desc);
    }
}
