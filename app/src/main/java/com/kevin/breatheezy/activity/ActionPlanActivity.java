package com.kevin.breatheezy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.util.Constant;
import com.kevin.breatheezy.view.CenteredToolbar;

public class ActionPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cvGreenZone, cvYellowZone, cvOrangeZone, cvRedZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_plan);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("My Action Plan");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cvGreenZone = findViewById(R.id.cvGreenZone);
        cvYellowZone = findViewById(R.id.cvYellowZone);
        cvOrangeZone = findViewById(R.id.cvOrangeZone);
        cvRedZone = findViewById(R.id.cvRedZone);

        cvGreenZone.setOnClickListener(this);
        cvYellowZone.setOnClickListener(this);
        cvOrangeZone.setOnClickListener(this);
        cvRedZone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == cvGreenZone){
            finish();
        }else if(v == cvYellowZone){
            Intent intent = new Intent(this, ZoneActivity.class);
            intent.putExtra("ZONE_TYPE", Constant.ZONE_YELLOW);
            intent.putExtra("TITLE", "Yellow Zone");
            startActivity(intent);
        }else if(v == cvOrangeZone){
            Intent intent = new Intent(this, ZoneActivity.class);
            intent.putExtra("ZONE_TYPE", Constant.ZONE_ORANGE);
            intent.putExtra("TITLE", "Orange Zone");
            startActivity(intent);
        }else if(v == cvRedZone){
            Intent intent = new Intent(this, ZoneActivity.class);
            intent.putExtra("ZONE_TYPE", Constant.ZONE_RED);
            intent.putExtra("TITLE", "Red Zone");
            startActivity(intent);
        }
    }
}
