package com.kevin.breatheezy.activity.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.preferences.GlobalPreferenceManager;
import com.kevin.breatheezy.util.Constant;
import com.kevin.breatheezy.view.CenteredToolbar;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cvDoctor, cvFamily, cvFriend, cvAmbulance;
    private FloatingActionButton fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("Contacts");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cvDoctor = findViewById(R.id.cvDoctor);
        cvFamily = findViewById(R.id.cvFamily);
        cvFriend = findViewById(R.id.cvFriend);
        cvAmbulance = findViewById(R.id.cvAmbulance);
        fabEdit = findViewById(R.id.fabEdit);

        cvDoctor.setOnClickListener(this);
        cvFamily.setOnClickListener(this);
        cvFriend.setOnClickListener(this);
        cvAmbulance.setOnClickListener(this);

        fabEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == cvDoctor){
            if(GlobalPreferenceManager.getDoctorPhNo().equals("")){
                Toast.makeText(getApplicationContext(), "Doctors phone no is not available.", Toast.LENGTH_SHORT).show();
            }else{
                callPhNo(GlobalPreferenceManager.getDoctorPhNo());
            }
        }else if(v == cvFriend){
            if(GlobalPreferenceManager.getFriendPhNo().equals("")){
                Toast.makeText(getApplicationContext(), "Friend's phone no is not available.", Toast.LENGTH_SHORT).show();
            }else{
                callPhNo(GlobalPreferenceManager.getFriendPhNo());
            }
        }else if(v == cvFamily){
            if(GlobalPreferenceManager.getFamilyPhNo().equals("")){
                Toast.makeText(getApplicationContext(), "Family phone no is not available.", Toast.LENGTH_SHORT).show();
            }else{
                callPhNo(GlobalPreferenceManager.getFamilyPhNo());
            }
        }else if(v == cvAmbulance){
            if(GlobalPreferenceManager.getAmbulancePhNo().equals("")){
                Toast.makeText(getApplicationContext(), "Ambulance phone no is not available.", Toast.LENGTH_SHORT).show();
            }else{
                callPhNo(GlobalPreferenceManager.getAmbulancePhNo());
            }
        }else if(v == fabEdit){
            startActivity(new Intent(ContactsActivity.this, EditContactsActivity.class));
        }
    }

    private void callPhNo(String phNo){
        if(checkPermission(Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phNo));
            startActivity(intent);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constant.REQUEST_PHONE_CALL);
        }
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
            return;
        }
    }
}
