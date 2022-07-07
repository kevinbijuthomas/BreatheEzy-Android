package com.kevin.breatheezy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.breatheezy.R;

import java.util.List;

public class AboutLinkAdapter extends RecyclerView.Adapter<AboutLinkAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position, String title);
    }

    private List<String> dataList;
    private OnItemClickListener listener;

    public AboutLinkAdapter(List<String> dataList, OnItemClickListener listener){
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_about_link, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvTitle.setText(dataList.get(position));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position, dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView){
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
