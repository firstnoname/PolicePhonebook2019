package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.StationDetailActivity;
import com.zealtech.policephonebook2019.Activities.StationSubListActivity;
import com.zealtech.policephonebook2019.Model.Department;

import java.util.ArrayList;

public class AdapterMapList extends RecyclerView.Adapter<AdapterMapList.ViewHolder> {

    private static final String TAG = "AdapterMapList";

    Context mContext;
    ArrayList<Department> mDepartment;
    int resId = R.drawable.policestation_ic;
    String tagArea = "นครบาล, ทั่วไป";
    int level = 1;

    public AdapterMapList(Context mContext, ArrayList<Department> mDepartment) {
        this.mContext = mContext;
        this.mDepartment = mDepartment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_map_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
//        Log.d(TAG, "onBindViewHolder: called");

        holder.imgInfo.setImageResource(resId);
        holder.tvStation.setText(mDepartment.get(i).getDepartmentName() + " " + mDepartment.get(i).getDepartmentId());
        holder.tvArea.setText(tagArea);

        holder.layout_adapter_map_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDepartment.get(i).getFlagTail().equals(true)) {
//                    Toast.makeText(mContext, mDepartment.get(i).getFlagTail() + " True", Toast.LENGTH_SHORT).show();
                    String parentId = String.valueOf(mDepartment.get(i).getDepartmentId());
                    Intent intent = new Intent(mContext, StationSubListActivity.class);
                    intent.putExtra("level", level);
                    intent.putExtra("parentId", parentId);

                    mContext.startActivity(intent);
                } else {
//                    Toast.makeText(mContext, mDepartment.get(i).getFlagTail() + " False", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, StationDetailActivity.class);
                    intent.putExtra("departmentId", mDepartment.get(i).getDepartmentId());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDepartment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgInfo;
        TextView tvStation, tvArea;
        ConstraintLayout layout_adapter_map_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInfo = itemView.findViewById(R.id.imgStation);
            tvStation = itemView.findViewById(R.id.tvStationPosition);
            tvArea = itemView.findViewById(R.id.tvArea);
            layout_adapter_map_list = itemView.findViewById(R.id.layout_adapter_map_list);
        }
    }

    public void updateStationList(ArrayList<Department> newList) {
        this.mDepartment = new ArrayList<>();
        mDepartment.addAll(newList);
        notifyDataSetChanged();
    }
}
