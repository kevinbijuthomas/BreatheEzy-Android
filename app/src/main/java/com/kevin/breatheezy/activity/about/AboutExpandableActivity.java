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
import com.kevin.breatheezy.adapter.AboutExpandableAdapter;
import com.kevin.breatheezy.model.AboutExpandableModel;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.ArrayList;
import java.util.List;

public class AboutExpandableActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private AboutExpandableAdapter mAdapter;
    private List<AboutExpandableModel> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_expandable);

        String title = getIntent().getStringExtra("TITLE");
        int position = getIntent().getIntExtra("POSITION", -1);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        populateAboutTopic(position);

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new AboutExpandableAdapter(mArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }



    private void populateAboutTopic(int position){
        mArrayList = new ArrayList<>();
        if(position == 0) {

            AboutExpandableModel aem1 = new AboutExpandableModel("What is asthma?", "Asthma is a common and potentially serious " +
                    "lung disease. Estimates suggest that " +
                    "worldwide 300 million people suffer from " +
                    "asthma. Asthma can be effectively controlled but not cured. Childhood asthma symptoms may remit over time in some children.");
            AboutExpandableModel aem2 = new AboutExpandableModel("What causes asthma?", "The exact cause(s) of asthma is not known. " +
                    "However, both genetic and environmental " +
                    "factors are involved in causing asthma.");
            AboutExpandableModel aem3 = new AboutExpandableModel("What are the symptoms of asthma?", "Symptoms of asthma include coughing, " +
                    "wheezing, chest tightness and shortness of " +
                    "breath. These symptoms typically vary over " +
                    "time in their occurrence and severity. ");
            AboutExpandableModel aem4 = new AboutExpandableModel("What causes asthma symptoms?", "The symptoms of asthma are due to " +
                    "inflammation and narrowing of the airways " +
                    "(breathing tubes) in the lungs, which cause " +
                    "obstruction to the flow of air in the airways.");
            AboutExpandableModel aem5 = new AboutExpandableModel("What are the triggers of asthma?", "Asthma symptoms may be triggered by viral " +
                    "infections (‘cold’), allergens, exposure to " +
                    "tobacco smoke, exercise or stress. " +
                    "Allergens that trigger asthma may differ " +
                    "from person to person.");
            AboutExpandableModel aem6 = new AboutExpandableModel("How is asthma diagnosed?", "Diagnosis of asthma remains clinical, based " +
                    "on history of characteristic symptom pattern. " +
                    "Lung function tests may help with diagnosis.");

            mArrayList.add(aem1);
            mArrayList.add(aem2);
            mArrayList.add(aem3);
            mArrayList.add(aem4);
            mArrayList.add(aem5);
            mArrayList.add(aem6);

        }else if(position == 2){
            AboutExpandableModel aem1 = new AboutExpandableModel("General Principles", "Appropriate treatment can help patients achieve " +
                    "good asthma control. " +
                    "There are two classes of medications for asthma: " +
                    "Controllers and Relievers. Controllers treat the " +
                    "underlying cause of asthma and help prevent " +
                    "asthma symptoms. Relievers are used to treat " +
                    "the symptoms of asthma to provide relief when " +
                    "symptoms occur. " +
                    "In general, both controllers and relievers are well " +
                    "tolerated and have minimal side effects. Relievers " +
                    "(e.g. Salbutamol) may cause transient rise in " +
                    "heart rate and/or tremors, especially if used in " +
                    "high doses. Controllers (e.g. inhaled steroids) " +
                    "may cause hoarseness of voice, throat irritation, " +
                    "dysphonia and oral thrush. The risk of these side " +
                    "effects may be reduced by using a spacer with the " +
                    "metered dose inhaler (MDI) and rinsing the mouth " +
                    "with water after inhalation. For further information " +
                    "regarding possible side effects, please consult " +
                    "your doctor.");
            AboutExpandableModel aem2 = new AboutExpandableModel("How to use asthma treatment?", "Follow the instructions of your doctor " +
                    "regarding the type and dose of medicines. " +
                    "Good inhaler/device technique and " +
                    "appropriate use of spacers are very " +
                    "important for the medicines to be effective. " +
                    "Ensure that inhalers are not empty and all " +
                    "medication are in date (check the expiry " +
                    "date). " +
                    "The controller medicines must be used " +
                    "regularly, even when you do not have asthma " +
                    "symptoms. " +
                    "The reliever medicines are for use when you " +
                    "experience asthma symptoms. Use them " +
                    "according to your asthma action plan. ");
            AboutExpandableModel aem3 = new AboutExpandableModel("How to assess asthma control?", "You can monitor how good your asthma " +
                    "control is. This can be done using tools such " +
                    "as asthma symptom diary, Global Initiative " +
                    "for Asthma (GINA) asthma control " +
                    "assessment, Asthma Control Test (ACT) and " +
                    "monitoring of Peak Expiratory Flow Rate " +
                    "(PEFR) using a peak flow meter. Go to My " +
                    "Asthma Control. " +
                    "Self-monitoring of your asthma control may " +
                    "help you and your doctor to modify the " +
                    "treatment according to your needs. " +
                    "In addition to self-monitoring, it is very " +
                    "important to have regular review with your " +
                    "doctor for a comprehensive assessment and " +
                    "appropriate personalised management plan.");
            AboutExpandableModel aem4 = new AboutExpandableModel("What is an Asthma Action Plan?", "Patients with asthma should have an up to " +
                    "date Written Asthma Action Plan (WAAP). " +
                    "The WAAP should be reviewed periodically " +
                    "with your doctor. " +
                    "The purpose of this asthma action plan is " +
                    "help you recognize worsening asthma " +
                    "symptoms AND guide you on appropriate " +
                    "actions to take depending on your symptoms. " +
                    "It will also advise you on when to seek " +
                    "medical help. " +
                    "Go to my asthma action plan.");

            mArrayList.add(aem1);
            mArrayList.add(aem2);
            mArrayList.add(aem3);
            mArrayList.add(aem4);

        }else if(position ==3){
            AboutExpandableModel aem1 = new AboutExpandableModel("What is an asthma attack?", "Asthma attack is characterized by an " +
                    "increase in asthma symptoms (increasing " +
                    "severity of cough, wheezing, chest tightness " +
                    "and/or shortness of breath) that typically " +
                    "happens over a short period of time. " +
                    "Asthma attacks occur typically in response to " +
                    "triggers (such as common cold). " +
                    "Whatever the trigger may be, the end result " +
                    "is an increase in inflammation of the air " +
                    "passages that lead to narrowing of air " +
                    "passages and excessive mucus (phlegm) " +
                    "production. " +
                    "This causes obstruction of the air passages " +
                    "and lead to symptoms of asthma attack. ");
            AboutExpandableModel aem2 = new AboutExpandableModel("What are the usual triggers?", "Viral infections (‘flu’ or ‘common cold’) " +
                    "Inhaled allergens (such as house dust mite, " +
                    "animal dander, pollen, indoor mould) " +
                    "Inhaled irritants (exposure to tobacco smoke, " +
                    "air pollution, haze etc.) " +
                    "Cold drinks " +
                    "Exercise " +
                    "Sometimes food substances such as nuts, if " +
                    "the patient is allergic to it.");
            AboutExpandableModel aem3 = new AboutExpandableModel("What should I do if I am having an asthma attack?", "If you are experiencing symptoms of asthma " +
                    "attack (cough, wheezing, chest tightness " +
                    "and/or shortness of breath), DO NOT PANIC. " +
                    "You should take your asthma reliever " +
                    "medications, as instructed in your asthma " +
                    "action plan. " +
                    "Seek emergency medical help if your " +
                    "symptoms persist or worsen despite the use " +
                    "of asthma reliever medication, as instructed " +
                    "in your asthma action plan. " +
                    "Even if your symptoms improve with use of " +
                    "asthma reliever medication, it is advisable to " +
                    "schedule a visit to your doctor to discuss your " +
                    "asthma management. ");

            mArrayList.add(aem1);
            mArrayList.add(aem2);
            mArrayList.add(aem3);
        }
    }
}
