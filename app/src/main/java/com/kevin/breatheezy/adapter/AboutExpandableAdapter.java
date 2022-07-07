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
import com.kevin.breatheezy.model.AboutExpandableModel;
import com.kevin.breatheezy.util.ExpandableRecyclerViewAnimation;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.List;

public class AboutExpandableAdapter extends RecyclerView.Adapter<AboutExpandableAdapter.ViewHolder> {

    private List<AboutExpandableModel> dataList;

    public AboutExpandableAdapter(List<AboutExpandableModel> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_about_expandable, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvTitle.setText(dataList.get(position).getTitle());
        holder.tvDesc.setText(dataList.get(position).getDescription());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(holder.iconView, !dataList.get(position).isExpanded(), holder.layoutExpand);
                dataList.get(position).setExpanded(show);
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
        public MaterialIconView iconView;
        public LinearLayout layoutExpand;
        public TextView tvDesc;
        public ViewHolder(View itemView){
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.iconView = itemView.findViewById(R.id.iconView);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayout);
            this.layoutExpand = itemView.findViewById(R.id.layoutExpand);
            this.tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }

    private boolean toggleLayout(View v, boolean isExpanded, View layoutExpand){
        ExpandableRecyclerViewAnimation.toggleArrow(v, isExpanded);
        if(isExpanded){
            ExpandableRecyclerViewAnimation.expand(layoutExpand);
        }else{
            ExpandableRecyclerViewAnimation.collapse(layoutExpand);
        }
        return isExpanded;
    }
}

