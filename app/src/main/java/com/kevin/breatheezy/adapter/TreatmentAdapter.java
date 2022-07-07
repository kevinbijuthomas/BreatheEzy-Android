package com.kevin.breatheezy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.breatheezy.R;
import com.kevin.breatheezy.database.TreatmentEntity;

import java.util.List;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {

    public interface ButtonCallbacks{
        void removePosition(int position);
        void editPosition(int position);
    }

    private final ButtonCallbacks callbacks;
    private List<TreatmentEntity> dataList;

    public TreatmentAdapter(ButtonCallbacks callbacks){
        this.callbacks = callbacks;
    }

    public void setTreatmentDataList(List<TreatmentEntity> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<TreatmentEntity> getTreatmentDataList(){
        return dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_my_treatment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(dataList.get(position).getName());
        holder.tvPuffs.setText("Number of puffs: " + dataList.get(position).getPuffs());
        holder.tvTimes.setText("Number of times: " + dataList.get(position).getTimes());
    }

    @Override
    public int getItemCount() {
        if(dataList == null){
            return 0;
        }
        return dataList.size();
    }

    public class ViewHolder extends BaseSwipeOpenViewHolder {
        public TextView tvName;
        public TextView tvPuffs;
        public TextView tvTimes;

        RelativeLayout editView;
        RelativeLayout deleteView;
        LinearLayout contentView;
        public ViewHolder(View itemView){
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvPuffs = itemView.findViewById(R.id.tvPuffs);
            this.tvTimes = itemView.findViewById(R.id.tvTimes);

            this.editView = itemView.findViewById(R.id.editView);
            this.deleteView = itemView.findViewById(R.id.deleteView);
            this.contentView = itemView.findViewById(R.id.contentView);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.removePosition(getAdapterPosition());
                }
            });

            editView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbacks.editPosition(getAdapterPosition());
                }
            });
        }

        @NonNull
        @Override
        public View getSwipeView() {
            return contentView;
        }

        @Override
        public float getEndHiddenViewSize() {
            return deleteView.getMeasuredWidth();
        }

        @Override
        public float getStartHiddenViewSize() {
            return editView.getMeasuredWidth();
        }
    }
}
