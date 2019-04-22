package com.zealtech.policephonebook2019.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.FilterDepartmentActivity;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Util.BusProvider;

import java.util.ArrayList;

public class AdapterDepartmentSearchFilter extends RecyclerView.Adapter<AdapterDepartmentSearchFilter.ViewHolder> {
    private static final String TAG = "AdapterDepartmentSearch";
    Department mDepartmentSelected = new Department();
    int resId = R.mipmap.policestation_ic;
    int level = 1;
    private Activity mActivity;
    private ArrayList<Department> mDepartment;
    private String image_url = ApplicationConfig.getImageUrl();

    private int departmentId;
    private String departmentName;


    public AdapterDepartmentSearchFilter(Activity activity, ArrayList<Department> mDepartment) {
        this.mActivity = activity;
        this.mDepartment = mDepartment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_map_list, viewGroup, false);
        AdapterDepartmentSearchFilter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

        if (mDepartment.get(i).getIcon() != null) {
            image_url = ApplicationConfig.getImageUrl() + mDepartment.get(i).getIcon();
            Glide.with(mActivity).load(image_url).into(holder.imgInfo);
        } else {
            holder.imgInfo.setImageResource(resId);
        }
        holder.tvStation.setText(mDepartment.get(i).getDepartmentName());

        String tagArea = "";
        if (mDepartment.get(i).getTag() != null) {
            for (int x = 0; x < mDepartment.get(i).getTag().size(); x++) {
                tagArea += mDepartment.get(i).getTag().get(x) + ", ";
            }
            holder.tvArea.setText(tagArea);
        } else {
            holder.tvArea.setVisibility(View.GONE);
        }

        if (mDepartment.get(i).getFlagTail().equals(false)) {
            holder.imgNext.setVisibility(View.INVISIBLE);
            holder.tvStation.setGravity(Gravity.CENTER_VERTICAL);
        }

        holder.layout_adapter_map_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDepartment.get(i).getFlagTail().equals(true)) {
                    level += 1;
                    String parentId = String.valueOf(mDepartment.get(i).getDepartmentId());
                    Intent intent = new Intent(mActivity, FilterDepartmentActivity.class);
                    intent.putExtra("level", level);
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("subTitle", mDepartment.get(i).getDepartmentName());

                    mActivity.startActivity(intent);
                    mActivity.finish();
                } else {
                    departmentId = mDepartment.get(i).getDepartmentId();
                    departmentName = mDepartment.get(i).getDepartmentName();

                    Intent iEditProfile = new Intent();
                    mDepartmentSelected.setDepartmentId(departmentId);
                    mDepartmentSelected.setDepartmentName(departmentName);
                    iEditProfile.putExtra("departmentSelected", mDepartmentSelected);

//                    BusProvider.getInstance().post("Hello");

                    mActivity.setResult(Activity.RESULT_OK, iEditProfile);
                    mActivity.finish();
                }

//                int departmentId = mDepartment.get(i).getDepartmentId();
//                String departmentName = mDepartment.get(i).getDepartmentName();
//
//                Intent iEditProfile = new Intent();
//                mDepartmentSelected.setDepartmentId(departmentId);
//                mDepartmentSelected.setDepartmentName(departmentName);
//                iEditProfile.putExtra("departmentSelected", mDepartmentSelected);
//                mActivity.setResult(Activity.RESULT_OK, iEditProfile);
//                mActivity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDepartment.size();
    }

    public void updateList(ArrayList<Department> newList) {
        mDepartment = new ArrayList<>();
        mDepartment.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgInfo, imgNext;
        TextView tvStation, tvArea;
        ConstraintLayout layout_adapter_map_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgInfo = itemView.findViewById(R.id.imgStation);
            tvStation = itemView.findViewById(R.id.tvStationPosition);
            tvArea = itemView.findViewById(R.id.tvArea);
            layout_adapter_map_list = itemView.findViewById(R.id.layout_adapter_map_list);
            imgNext = itemView.findViewById(R.id.imgNext);
        }
    }

}
