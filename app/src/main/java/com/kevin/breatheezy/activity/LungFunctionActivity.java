package com.kevin.breatheezy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.arindam.bestdialog.BestDialogConstant;
import com.arindam.bestdialog.BestTextInputDialog;
import com.arindam.bestdialog.ViewConfigurator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.database.AppDatabase;
import com.kevin.breatheezy.database.AppExecutors;
import com.kevin.breatheezy.database.LungFunctionEntity;
import com.kevin.breatheezy.util.DayAxisValueFormatter;
import com.kevin.breatheezy.util.Util;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LungFunctionActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private LineChart lineChart;
    private int savedLungFunctionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lung_function);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleColor(getResources().getColor(R.color.colorWhite));
        toolbar.setTitle("My Lung Function");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabAdd = findViewById(R.id.fabAdd);
        setupChart();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTodayLungFunction();
                showInputDialog();
            }
        });

    }

    private void setupChart(){
        lineChart = findViewById(R.id.lineChart);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(15);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new DayAxisValueFormatter());

        lineChart.getAxisRight().setEnabled(false);
    }

    private void showInputDialog(){
        new BestTextInputDialog(this, R.style.EditTextTheme, BestDialogConstant.BOTTON_FOOTER_STYLE_ADVANCE)
                .setTopColorRes(R.color.colorPrimary)
                .setTopTitle("ADD PEFR")
                .setTopTitleColor(R.color.colorWhite)
                .setTitleGravity(Gravity.CENTER)
                .setTitle("Add today's Peak Expiratory Flow Rate")
                .configureEditText(new ViewConfigurator<EditText>() {
                    @Override
                    public void configureView(EditText editText) {
                        editText.setLines(1);
                        editText.setHint("PEFR (L/min)");
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                })
                .setConfirmButton("Save", new BestTextInputDialog.OnTextInputConfirmListener(){
                    @Override
                    public void onTextInputConfirmed(String text) {
                        saveLungFunction(text);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveLungFunction(final String text) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                LungFunctionEntity lungFunctionEntity = new LungFunctionEntity(Integer.valueOf(text), new Date());
                if(savedLungFunctionID == -1) {
                    AppDatabase.getInstance(getApplicationContext()).lungFunctionDAO().insertLungFunction(lungFunctionEntity);
                } else {
                    lungFunctionEntity.setId(savedLungFunctionID);
                    AppDatabase.getInstance(getApplicationContext()).lungFunctionDAO().updateLungFunction(lungFunctionEntity);
                }
                loadLungFunction();
            }
        });
    }

    private void loadLungFunction() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                startCal.add(Calendar.DAY_OF_YEAR, -14);
                final List<LungFunctionEntity> lungFunctionEntityList = AppDatabase.getInstance(getApplicationContext())
                                    .lungFunctionDAO()
                                    .loadAllLungFunctionByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       populateDataToChart(lungFunctionEntityList);
                    }
                });
            }
        });
    }

    private void checkTodayLungFunction() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH), 0, 0);
                endCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH), 23, 59);
                final List<LungFunctionEntity> lungFunctionEntityList = AppDatabase.getInstance(getApplicationContext())
                                                                        .lungFunctionDAO().loadAllLungFunctionByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                if(lungFunctionEntityList.size() != 0) {
                    savedLungFunctionID = lungFunctionEntityList.get(0).getId();
                } else {
                    savedLungFunctionID = -1;
                }
            }
        });
    }

    private void populateDataToChart(List<LungFunctionEntity> lungFunctionEntityList) {
        ArrayList<Entry> entryArrayList = new ArrayList<>();

        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.DAY_OF_YEAR, 1);
        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_YEAR, -13);

        int count = 1;
        for(Date date = startCal.getTime(); startCal.before(endCal); startCal.add(Calendar.DATE, 1), date = startCal.getTime()){
            boolean isDateMatched = false;

            for(int i=0; i<lungFunctionEntityList.size(); i++){
                if(Util.compareToDay(date, lungFunctionEntityList.get(i).getSavedDate())){
                    entryArrayList.add(new Entry(count, lungFunctionEntityList.get(i).getPEFR()));
                    isDateMatched = true;
                    break;
                }
            }
            if(!isDateMatched){
                entryArrayList.add(new Entry(count, 0f));
            }
            count++;
        }


        LineDataSet set1 = new LineDataSet(entryArrayList, "");
        set1.enableDashedLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setValueTextSize(9f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
        set1.setDrawFilled(true);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return lineChart.getAxisLeft().getAxisMinimum();
            }
        });

        LineData data = new LineData(set1);
        lineChart.setData(data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadLungFunction();
    }
}
