package com.kevin.breatheezy.activity.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.breatheezy.R;
import com.kevin.breatheezy.adapter.AboutAdapter;
import com.kevin.breatheezy.model.AboutExpandableModel;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity implements AboutAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private AboutAdapter mAdapter;
    private List<String> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("About Asthma");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        populateAboutTopic();

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new AboutAdapter(mArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }



    private void populateAboutTopic(){
        mArrayList = new ArrayList<>();
        mArrayList.add("Asthma Facts");
        mArrayList.add("Asthma Triggers");
        mArrayList.add("Asthma Treatment");
        mArrayList.add("Asthma Attacks");
        mArrayList.add("Useful Links");
    }

    @Override
    public void onItemClick(int position, String title) {
        if(position == 0 || position == 2 || position == 3){
            Intent intent = new Intent(AboutActivity.this, AboutExpandableActivity.class);
            intent.putExtra("TITLE", title);
            intent.putExtra("POSITION", position);
            startActivity(intent);
        }else if(position == 1){
            Intent intent = new Intent(AboutActivity.this, AboutTriggersActivity.class);
            intent.putExtra("TITLE", title);
            startActivity(intent);
        }else if(position == 4){
            Intent intent = new Intent(AboutActivity.this, AboutLinkActivity.class);
            intent.putExtra("TITLE", title);
            startActivity(intent);
        }
    }
}

