package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.MockStation;

import java.util.List;

public class AdapterStationSubList extends RecyclerView.Adapter<AdapterStationSubList.ViewHolder> {

    private Context mContext;
    private List<MockStation> mStationList;
    private OnSubStationListener mOnSubStationListener;

    public AdapterStationSubList(Context mContext, List<MockStation> dataSet, OnSubStationListener onSubStationListener) {
        this.mOnSubStationListener = onSubStationListener;
        this.mContext = mContext;
        this.mStationList = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvStationSubList;
        OnSubStationListener onSubStationListener;

        public ViewHolder(@NonNull View itemView, OnSubStationListener onSubStationListener) {
            super(itemView);
            tvStationSubList = itemView.findViewById(R.id.tvStationSubList);
            this.onSubStationListener = onSubStationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSubStationListener.onSubStationClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public AdapterStationSubList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_station_sub_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view, mOnSubStationListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStationSubList.ViewHolder viewHolder, int i) {
        MockStation station = mStationList.get(i);

        viewHolder.tvStationSubList.setText(station.getSubStation());
    }

    @Override
    public int getItemCount() {
        return mStationList.size();
    }

    public interface OnSubStationListener {
        void onSubStationClick(int position);
    }
}
