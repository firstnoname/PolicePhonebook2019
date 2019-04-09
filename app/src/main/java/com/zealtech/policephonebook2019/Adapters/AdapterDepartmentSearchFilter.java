package com.zealtech.policephonebook2019.Adapters;

import android.app.Activity;
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
import com.zealtech.policephonebook2019.Model.Department;

import java.util.ArrayList;

public class AdapterDepartmentSearchFilter extends RecyclerView.Adapter<AdapterDepartmentSearchFilter.ViewHolder> {
    private static final String TAG = "AdapterDepartmentSearch";
    Department mDepartmentSelected = new Department();
    int resId = R.mipmap.policestation_ic;
    int level = 1;
    private Activity mActivity;
    private ArrayList<Department> mDepartment;

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

        holder.imgInfo.setImageResource(resId);
        holder.tvStation.setText(mDepartment.get(i).getDepartmentName());

        String tagArea = "";
        if (mDepartment.get(i).getTag() != null) {
            for (int x = 0; x < mDepartment.get(i).getTag().size(); x++) {
                tagArea += mDepartment.get(i).getTag().get(x) + ", ";
            }
        }

        holder.tvArea.setText(tagArea);

        holder.layout_adapter_map_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo Cheating on select department. Search how to setResult from third activity to 1st activity.
//                if (mDepartment.get(i).getFlagTail().equals(true)) {
//                    level += 1;
//                    String parentId = String.valueOf(mDepartment.get(i).getDepartmentId());
//                    Intent intent = new Intent(mActivity, FilterDepartmentActivity.class);
//                    intent.putExtra("level", level);
//                    intent.putExtra("parentId", parentId);
//                    intent.putExtra("subTitle", mDepartment.get(i).getDepartmentName());
//                    mActivity.startActivity(intent);
//
//                    mActivity.finish();
//                } else {
//                    int departmentId = mDepartment.get(i).getDepartmentId();
//                    String departmentName = mDepartment.get(i).getDepartmentName();
//
//                    Intent iEditProfile = new Intent();
//                    iEditProfile.putExtra("departmentId", departmentId);
//                    iEditProfile.putExtra("departmentName", departmentName);
//                    mActivity.setResult(Activity.RESULT_OK, iEditProfile);
//                    mActivity.finish();
//                }

                int departmentId = mDepartment.get(i).getDepartmentId();
                String departmentName = mDepartment.get(i).getDepartmentName();

                Intent iEditProfile = new Intent();
                mDepartmentSelected.setDepartmentId(departmentId);
                mDepartmentSelected.setDepartmentName(departmentName);
                iEditProfile.putExtra("departmentSelected", mDepartmentSelected);
//                iEditProfile.putExtra("departmentId", departmentId);
//                iEditProfile.putExtra("departmentName", departmentName);
                mActivity.setResult(Activity.RESULT_OK, iEditProfile);
                mActivity.finish();
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

}
