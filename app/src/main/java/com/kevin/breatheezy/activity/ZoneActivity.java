package com.kevin.breatheezy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.database.AppDatabase;
import com.kevin.breatheezy.database.AppExecutors;
import com.kevin.breatheezy.database.DiaryEntity;
import com.kevin.breatheezy.preferences.GlobalPreferenceManager;
import com.kevin.breatheezy.util.Constant;
import com.kevin.breatheezy.util.Util;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class ZoneActivity extends AppCompatActivity{

    private CardView cvYellowAndOrange, cvRedZoneDescription;
    private TextView tvName, tvPuffs, tvTimes;
    private FancyButton btnAmbulance;
    int zoneType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);

        String title = getIntent().getStringExtra("TITLE");
        zoneType = getIntent().getIntExtra("ZONE_TYPE", -1);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cvYellowAndOrange = findViewById(R.id.cvYellowAndOrange);
        cvRedZoneDescription = findViewById(R.id.cvRedZoneDescription);
        tvName = findViewById(R.id.tvName);
        tvPuffs = findViewById(R.id.tvPuffs);
        tvTimes = findViewById(R.id.tvTimes);
        btnAmbulance = findViewById(R.id.btnAmbulance);

        if(zoneType == Constant.ZONE_YELLOW) {
            cvRedZoneDescription.setVisibility(View.GONE);
            cvYellowAndOrange.setCardBackgroundColor(Color.parseColor("#fae769"));
            tvName.setText("Salbutamol MDI");
            tvPuffs.setText("Number of puffs: 2");
            tvTimes.setText("Every 6 hours");
        } else if (zoneType == Constant.ZONE_ORANGE) {
            cvRedZoneDescription.setVisibility(View.GONE);
            cvYellowAndOrange.setCardBackgroundColor(Color.parseColor("#faa646"));
            tvName.setText("Salbutamol MDI");
            tvPuffs.setText("Number of puffs: 2");
            tvTimes.setText("Every 4 hours");
        } else if (zoneType == Constant.ZONE_RED) {
            cvYellowAndOrange.setVisibility(View.GONE);
            tvName.setText("Salbutamol MDI");
            tvPuffs.setText("Number of puffs: 10");
            tvTimes.setText("Every 15 minutes");
        }

        btnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission(Manifest.permission.CALL_PHONE)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + GlobalPreferenceManager.getAmbulancePhNo()));
                    startActivity(intent);
                }else{
                    ActivityCompat.requestPermissions(ZoneActivity.this, new String[]{Manifest.permission.CALL_PHONE}, Constant.REQUEST_PHONE_CALL);
                }
            }
        });
    }

    private boolean checkPermission(String permission){
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constant.REQUEST_PHONE_CALL:
                if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(this, "You have call permission now", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
