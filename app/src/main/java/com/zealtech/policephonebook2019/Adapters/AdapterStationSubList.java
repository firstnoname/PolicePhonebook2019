package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Activities.StationDetailActivity;
import com.zealtech.policephonebook2019.Activities.StationDetailTabviewActivity;
import com.zealtech.policephonebook2019.Activities.StationSubListActivity;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Department;

import java.util.ArrayList;

public class AdapterStationSubList extends RecyclerView.Adapter<AdapterStationSubList.ViewHolder> {

    private static final String TAG = "AdapterStationSubList";

    private Context mContext;
    private ArrayList<Department> mStationList;
    int resId = R.mipmap.policestation_ic;
    String parentId = "";
    int level = 2;
    private String image_url;

    public AdapterStationSubList(Context mContext, ArrayList<Department> mStationList) {
        this.mContext = mContext;
        this.mStationList = mStationList;
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

        if (i == 0) {
            holder.layout_adapter_station_sub_list.setBackgroundResource(R.color.list_background);
        } else {
            holder.layout_adapter_station_sub_list.setBackgroundResource(R.color.fontWhite);
        }

        if (mStationList.get(i).getIcon() != null) {
            image_url = ApplicationConfig.getImageUrl() + mStationList.get(i).getIcon();
            Glide.with(mContext).load(image_url).into(holder.imgInfo);
        } else {
            holder.imgInfo.setImageResource(resId);
        }

        holder.tvStationName.setText(mStationList.get(i).getDepartmentName());
        if (mStationList.get(i).getFlagTail().equals(false)) {
            holder.imgNext.setVisibility(View.INVISIBLE);
        }

        String tagArea = "";
        if (mStationList.get(i).getTag() != null) {
            for (int x = 0; x < mStationList.get(i).getTag().size(); x++) {
                if (mStationList.get(i).getTag().size() - 1 == x) {
                    tagArea += mStationList.get(i).getTag().get(x);
                } else {
                    tagArea += mStationList.get(i).getTag().get(x) + ", ";
                }
            }

            holder.tvArea.setText(tagArea);
        } else {
            holder.tvArea.setVisibility(View.GONE);
            holder.tvStationName.setGravity(Gravity.CENTER_VERTICAL);
        }

        holder.layout_adapter_station_sub_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mStationList.get(i).getFlagTail().equals(true)) {
//                    Toast.makeText(mContext, mStationList.get(i).getFlagTail().toString(), Toast.LENGTH_SHORT).show();
                    String object = new Gson().toJson(mStationList.get(i));
                    level = level + 1;
                    parentId = String.valueOf(mStationList.get(i).getDepartmentId());
                    Intent intent = new Intent(mContext, StationSubListActivity.class);
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("level", level);
                    intent.putExtra("subTitle", mStationList.get(i).getDepartmentName());
                    intent.putExtra("parentDepartment", object);
                    mContext.startActivity(intent);
                } else {
//                    Toast.makeText(mContext, mStationList.get(i).getFlagTail().toString(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(mContext, StationDetailActivity.class);
                    Intent intent = new Intent(mContext, StationDetailTabviewActivity.class);
                    intent.putExtra("departmentId", mStationList.get(i).getDepartmentId());
                    intent.putExtra("departmentName", mStationList.get(i).getDepartmentName());
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
        TextView tvStationName, tvArea;
        ConstraintLayout layout_adapter_station_sub_list;
        ImageButton imgNext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgInfo = itemView.findViewById(R.id.imgStation);
            tvStationName = itemView.findViewById(R.id.tvStationPosition);
            tvArea = itemView.findViewById(R.id.tvArea);
            layout_adapter_station_sub_list = itemView.findViewById(R.id.layout_adapter_map_list);
            imgNext = itemView.findViewById(R.id.imgNext);

        }
    }

    public void updateSubStation(ArrayList<Department> newList) {
        mStationList = new ArrayList<>();
        mStationList.addAll(newList);

        notifyDataSetChanged();
    }

}
