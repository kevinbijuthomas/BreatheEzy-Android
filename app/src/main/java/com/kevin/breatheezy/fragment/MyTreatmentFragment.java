package com.kevin.breatheezy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevin.breatheezy.R;
import com.kevin.breatheezy.activity.AddTreatmentActivity;
import com.kevin.breatheezy.adapter.TreatmentAdapter;
import com.kevin.breatheezy.database.AppDatabase;
import com.kevin.breatheezy.database.AppExecutors;
import com.kevin.breatheezy.database.TreatmentEntity;
import com.kevin.breatheezy.view.CenteredToolbar;

import java.util.List;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;

public class MyTreatmentFragment extends Fragment implements TreatmentAdapter.ButtonCallbacks{

    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;
    private TreatmentAdapter mAdapter;
    private SwipeOpenItemTouchHelper helper;
    private TextView tvNoItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_treatment, container, false);

        fabAdd = view.findViewById(R.id.fabAdd);
        tvNoItem = view.findViewById(R.id.tvNoItem);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddTreatmentActivity.class));
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TreatmentAdapter(this);
        recyclerView.setAdapter(mAdapter);

        helper.attachToRecyclerView(recyclerView);
        helper.setCloseOnAction(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadTreatmentData();
    }

    private void loadTreatmentData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<TreatmentEntity> treatmentEntityList = AppDatabase.getInstance(getActivity()).treatmentDAO().loadAllTreatment();
                Log.i("Kevin", "" + treatmentEntityList.size());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTreatmentDataList(treatmentEntityList);
                        if(treatmentEntityList.size()<=0){
                            tvNoItem.setVisibility(View.VISIBLE);
                        }else{
                            tvNoItem.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void removePosition(int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               List<TreatmentEntity> treatmentEntityList = mAdapter.getTreatmentDataList();
                AppDatabase.getInstance(getActivity()).treatmentDAO().deleteRecord(treatmentEntityList.get(position));
                loadTreatmentData();
            }
        });
    }

    @Override
    public void editPosition(int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<TreatmentEntity> treatmentEntityList = mAdapter.getTreatmentDataList();
                TreatmentEntity treatmentEntity = treatmentEntityList.get(position);

                Intent intent = new Intent(getActivity(), AddTreatmentActivity.class);
                intent.putExtra("NAME", treatmentEntity.getName());
                intent.putExtra("PUFF", treatmentEntity.getPuffs());
                intent.putExtra("TIMES", treatmentEntity.getTimes());
                intent.putExtra("ID", treatmentEntity.getId());

                startActivity(intent);
            }
        });
    }
}
