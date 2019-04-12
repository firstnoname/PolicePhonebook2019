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
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.StationStaffDetailActivity;
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
        }
//        Log.d(TAG, IMAGE_URL + mPoliceList.get(i).getImageProfile());
        fullName = mPoliceList.get(i).getRankName() + " " + mPoliceList.get(i).getFirstName() + "  " + mPoliceList.get(i).getLastName();
        viewHolder.tvName.setText(fullName);
        viewHolder.tvPosition.setText(mPoliceList.get(i).getPositionName());
        viewHolder.tvDeparture.setText(mPoliceList.get(i).getDepartmentName());

        if (mPoliceList.get(i).getColor() != null) {
            viewHolder.viewTab.setBackgroundColor(Color.parseColor(mPoliceList.get(i).getColor()));
        }

//        String rankName = mPoliceList.get(i).getRankName();
////        Set view_tab color from rank.
//        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
//            //Gold
//
//            viewHolder.imgProfile.setBorderColor(Color.YELLOW);
//            viewHolder.viewTab.setBackgroundResource(R.color.colorYellow);
//        } else if (rankName.equals("พล.ต.ต.")) {
//            //Blue sky
//            viewHolder.imgProfile.setBorderColor(Color.GREEN);
//            viewHolder.viewTab.setBackgroundResource(R.color.colorGreen);
//        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
//            //Blue
//            viewHolder.imgProfile.setBorderColor(Color.BLUE);
//            viewHolder.viewTab.setBackgroundResource(R.color.colorBlue);
//        } else {
//            //Red
//            viewHolder.imgProfile.setBorderColor(Color.RED);
//            viewHolder.viewTab.setBackgroundResource(R.color.colorRed);
//        }

//        Set label orange or red.
        if (mPoliceList.get(i).getTag() != null) {
            for (int x = 0; x < mPoliceList.get(i).getTag().size(); x++) {
//            Log.d("tag", i + " : " + x + " : " + polis.getTag().size());
//            Log.d("tag", polis.getFirstName() + ":" + polis.getTag().get(x));
                if (x == 0) {
                    if (mPoliceList.get(i).getTag().get(x).equals("ตม.")) {
                        viewHolder.tvPosition2.setVisibility(View.VISIBLE);
                        viewHolder.tvPosition2.setBackgroundResource(R.drawable.tv_red_tag);
                        viewHolder.tvPosition2.setText(mPoliceList.get(i).getTag().get(x));
                    }
                    if (mPoliceList.get(i).getTag().get(x).equals("ผบก.")) {
                        viewHolder.tvPosition2.setVisibility(View.VISIBLE);
                        viewHolder.tvPosition2.setBackgroundResource(R.drawable.tv_orange_tag);
                        viewHolder.tvPosition2.setText(mPoliceList.get(i).getTag().get(x));
                    }
                }

                if (x == 1) {
                    if (mPoliceList.get(i).getTag().get(x).equals("ผบก.")) {
                        viewHolder.tvPosition1.setVisibility(View.VISIBLE);
                        viewHolder.tvPosition1.setBackgroundResource(R.drawable.tv_orange_tag);
                        viewHolder.tvPosition1.setText(mPoliceList.get(i).getTag().get(x));
                    }
                    if (mPoliceList.get(i).getTag().get(x).equals("ตม.")) {
                        viewHolder.tvPosition1.setVisibility(View.VISIBLE);
                        viewHolder.tvPosition1.setBackgroundResource(R.drawable.tv_red_tag);
                        viewHolder.tvPosition1.setText(mPoliceList.get(i).getTag().get(x));
                    }
                }

            }// end for loop.
        }
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "onClick: clicked on: " + mPoliceInfo.get(i).getFirstName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, StationStaffDetailActivity.class);
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
