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

public class AdapterMapList extends RecyclerView.Adapter<AdapterMapList.ViewHolder> {

    private static final String TAG = "AdapterMapList";

    Context mContext;
    ArrayList<Department> mDepartment;
    int resId = R.mipmap.policestation_ic;
    int level = 1;
    private String image_url;

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

        if (mDepartment.get(i).getIcon() != null) {
            image_url = ApplicationConfig.getImageUrl() + mDepartment.get(i).getIcon();
            Glide.with(mContext).load(image_url).into(holder.imgInfo);
        } else {
            holder.imgInfo.setImageResource(resId);
        }

        holder.tvStation.setText(mDepartment.get(i).getDepartmentName());

        String tagArea = "";
        if (mDepartment.get(i).getTag() != null) {
            for (int x = 0; x < mDepartment.get(i).getTag().size(); x++) {
                if (mDepartment.get(i).getTag().size() - 1 == x) {
                    tagArea += mDepartment.get(i).getTag().get(x);
                } else {
                    tagArea += mDepartment.get(i).getTag().get(x) + ", ";
                }
            }

            holder.tvArea.setText(tagArea);
        } else {
            holder.tvArea.setVisibility(View.GONE);
            holder.tvStation.setGravity(Gravity.CENTER_VERTICAL);
        }

        if (mDepartment.get(i).getFlagTail().equals(false)) {
            holder.imgNext.setVisibility(View.INVISIBLE);
        }

        holder.layout_adapter_map_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDepartment.get(i).getFlagTail().equals(true)) {
                    String object = new Gson().toJson(mDepartment.get(i));
                    String parentId = String.valueOf(mDepartment.get(i).getDepartmentId());
                    Intent intent = new Intent(mContext, StationSubListActivity.class);
                    intent.putExtra("level", level);
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("subTitle", mDepartment.get(i).getDepartmentName());
                    intent.putExtra("parentDepartment", object);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, StationDetailTabviewActivity.class);
                    intent.putExtra("departmentId", mDepartment.get(i).getDepartmentId());
                    intent.putExtra("departmentName", mDepartment.get(i).getDepartmentName());
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
        ImageButton imgNext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInfo = itemView.findViewById(R.id.imgStation);
            tvStation = itemView.findViewById(R.id.tvStationPosition);
            tvArea = itemView.findViewById(R.id.tvArea);
            layout_adapter_map_list = itemView.findViewById(R.id.layout_adapter_map_list);
            imgNext = itemView.findViewById(R.id.imgNext);
        }
    }

    public void updateStationList(ArrayList<Department> newList) {
        this.mDepartment = new ArrayList<>();
        mDepartment.addAll(newList);
        notifyDataSetChanged();
    }
}
