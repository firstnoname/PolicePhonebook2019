package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.StationDetailActivity;
import com.zealtech.policephonebook2019.Activities.StationSubListActivity;
import com.zealtech.policephonebook2019.Model.Department;

import java.util.ArrayList;

public class AdapterStationSubList extends RecyclerView.Adapter<AdapterStationSubList.ViewHolder> {

    private Context mContext;
    private ArrayList<Department> mStationList;
    int resId = R.drawable.policestation_ic;
    String parentId = "";

    public AdapterStationSubList(Context mContext, ArrayList<Department> mStationList) {
        this.mContext = mContext;
        this.mStationList = mStationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_station_sub_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.imgInfo.setImageResource(resId);
        holder.tvStationName.setText(mStationList.get(i).getDepartmentName());

        holder.layout_adapter_station_sub_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStationList.get(i).getFlagTail().equals(true)) {
//                    Toast.makeText(mContext, mDepartment.get(i).getFlagTail() + " True", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, StationDetailActivity.class);
                    mContext.startActivity(intent);
                } else {
//                    Toast.makeText(mContext, mDepartment.get(i).getFlagTail() + " False", Toast.LENGTH_SHORT).show();
                    parentId = String.valueOf(mStationList.get(i).getParentId());
                    Intent intent = new Intent(mContext, StationSubListActivity.class);
                    intent.putExtra("parentId", parentId);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgInfo;
        TextView tvStationName;
        ConstraintLayout layout_adapter_station_sub_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgInfo = itemView.findViewById(R.id.imgStationSubList);
            tvStationName = itemView.findViewById(R.id.tvStationSubList);
            layout_adapter_station_sub_list = itemView.findViewById(R.id.layout_adapter_station_sub_list);
        }
    }

}
