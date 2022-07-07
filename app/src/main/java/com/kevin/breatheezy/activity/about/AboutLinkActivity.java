package com.kevin.breatheezy.activity.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.breatheezy.R;
import com.kevin.breatheezy.adapter.AboutAdapter;
import com.kevin.breatheezy.adapter.AboutLinkAdapter;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.ArrayList;
import java.util.List;

public class AboutLinkActivity extends AppCompatActivity implements AboutLinkAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private AboutLinkAdapter mAdapter;
    private List<String> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_link);

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

        populateAboutLinks();

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new AboutLinkAdapter(mArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position, String title) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(title));
        startActivity(browserIntent);
    }


    private void populateAboutLinks(){
        mArrayList = new ArrayList<>();
        mArrayList.add("https://www.ginasthma.org");
        mArrayList.add("https://www.asthma.org.uk");
        mArrayList.add("https://www.cdc.gov/asthma/default.htm");
        mArrayList.add("https://www.nationalasthma.org.au/");
        mArrayList.add("https://www.asthma.org.uk/advice/inhalers-medicines-treatments/using-inhalers/#Videos");
        mArrayList.add("https://www.nhs.uk/conditions/asthma/treatment/");
    }

}
