package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zealtech.policephonebook2019.Activities.ContactDetailFilterActivity;
import com.zealtech.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Police;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterStationStaff extends RecyclerView.Adapter<AdapterStationStaff.ViewHolder> {

    private static final String TAG = "AdapterPhoneList";
    private ArrayList<Police> mPoliceList;
    private Context mContext;
    private String IMAGE_URL = ApplicationConfig.getImageUrl();

    private String fullName;

    public AdapterStationStaff(Context mContext, ArrayList<Police> mPoliceList) {
        this.mPoliceList = mPoliceList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_station_staff, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        if (mPoliceList.get(i).getImageProfile() != null) {
            Glide.with(mContext)
                    .load(IMAGE_URL + mPoliceList.get(i).getImageProfile())
                    .into(viewHolder.imgProfile);
        } else {
            viewHolder.imgProfile.setImageResource(R.mipmap.userfrofiledefualt);
        }
//        Log.d(TAG, IMAGE_URL + mPoliceList.get(i).getImageProfile())เรื่อ;
        fullName = mPoliceList.get(i).getRankName() + " " + mPoliceList.get(i).getFirstName() + "  " + mPoliceList.get(i).getLastName();
        viewHolder.tvName.setText(fullName);
        viewHolder.tvPosition.setText(mPoliceList.get(i).getPositionName());
        viewHolder.tvDeparture.setText(mPoliceList.get(i).getDepartmentName());

        if (mPoliceList.get(i).getColor() != null) {
            viewHolder.viewTab.setBackgroundColor(Color.parseColor(mPoliceList.get(i).getColor()));
        }

        if (mPoliceList.get(i).getTag() != null) {
            for (int x = 0; x < mPoliceList.get(i).getTag().size(); x++) {
                viewHolder.tvPosition2.setVisibility(View.VISIBLE);
                viewHolder.tvPosition2.setText(mPoliceList.get(i).getTag().get(x));
            }
        }

        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "onClick: clicked on: " + mPoliceInfo.get(i).getFirstName(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(mContext, StationStaffDetailActivity.class);
                Intent intent = new Intent(mContext, ContactDetailFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact_detail", mPoliceList);
                intent.putExtra("position", i);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPoliceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView tvName, tvPosition, tvDeparture, tvPosition1, tvPosition2, viewTab;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.layout_item);
            imgProfile = itemView.findViewById(R.id.img_noti);
            tvName = itemView.findViewById(R.id.tv_noti_title);
            tvPosition = itemView.findViewById(R.id.tv_noti_date);
            tvDeparture = itemView.findViewById(R.id.tvDepartment);
            tvPosition1 = itemView.findViewById(R.id.tv_position_1);
            tvPosition2 = itemView.findViewById(R.id.tv_position_2);
            viewTab = itemView.findViewById(R.id.view_tab);

        }
    }

}
