package com.kevin.breatheezy.activity.contacts;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.breatheezy.R;
import com.kevin.breatheezy.preferences.GlobalPreferenceManager;
import com.kevin.breatheezy.view.CenteredToolbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class EditContactsActivity extends AppCompatActivity {
    private MaterialEditText etDoctor, etFamily, etFriend, etAmbulance;
    private FancyButton btnSave;

    private boolean isError = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_edit);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("Edit Contacts");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etDoctor = findViewById(R.id.etDoctor);
        etFamily = findViewById(R.id.etFamily);
        etFriend = findViewById(R.id.etFriend);
        etAmbulance = findViewById(R.id.etAmbulance);

        etDoctor.setText(GlobalPreferenceManager.getDoctorPhNo());
        etFamily.setText(GlobalPreferenceManager.getFamilyPhNo());
        etFriend.setText(GlobalPreferenceManager.getFriendPhNo());
        etAmbulance.setText(GlobalPreferenceManager.getAmbulancePhNo());

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(etDoctor.getText().toString())){
                    GlobalPreferenceManager.setDoctorPhNo(etDoctor.getText().toString());
                }else{
                    etDoctor.setError("Wrong phone number");
                    isError = true;
                }

                if(validate(etFamily.getText().toString())){
                    GlobalPreferenceManager.setFamilyPhNo(etFamily.getText().toString());
                }else{
                    etFamily.setError("Wrong phone number");
                    isError = true;
                }

                if(validate(etFriend.getText().toString())){
                    GlobalPreferenceManager.setFriendPhNo(etFriend.getText().toString());
                }else{
                    etFriend.setError("Wrong phone number");
                    isError = true;
                }

                if(validate(etAmbulance.getText().toString())){
                    GlobalPreferenceManager.setAmbulancePhNo(etAmbulance.getText().toString());
                }else{
                    etAmbulance.setError("Wrong phone number");
                    isError = true;
                }

                if(!isError){
                    finish();
                }
            }
        });

    }

    private boolean validate(String phNo){
        if(phNo.equals("")) {
            return true;
        }else if(Patterns.PHONE.matcher(phNo).matches()){
            if(phNo.length()>=8)
                return true;
            else
                return false;
        }else
            return false;
    }
}
