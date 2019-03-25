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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.ContactDetailActivity;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPhoneList extends RecyclerView.Adapter<AdapterPhoneList.ViewHolder> {

    private static final String TAG = "AdapterPhoneList";
    private ArrayList<PoliceMasterData> mPoliceInfo;
    private Context mContext;
    private String fullName = "";
    private String IMAGE_URL = "http://ztidev.com:8081/phonebook/download?file=";


    public AdapterPhoneList(Context mContext, ArrayList<PoliceMasterData> mPoliceInfo) {
        this.mPoliceInfo = mPoliceInfo;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_phone_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .load(IMAGE_URL+mPoliceInfo.get(i).getImageProfile())
                .into(holder.imgProfile);
        fullName = mPoliceInfo.get(i).getFirstName() + "  " + mPoliceInfo.get(i).getLastName();
        holder.tvName.setText(fullName);
        holder.tvPosition.setText(mPoliceInfo.get(i).getPositionName());
        holder.tvDeparture.setText(mPoliceInfo.get(i).getDepartmentName());

        String rankName = mPoliceInfo.get(i).getRankName();
//        Set view_tab color from rank.
        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
            //Gold
            holder.viewTab.setBackgroundResource(R.color.colorYellow);
        } else if (rankName.equals("พล.ต.ต.")) {
            //Blue sky
            holder.viewTab.setBackgroundResource(R.color.colorGreen);
        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
            //Blue
            holder.viewTab.setBackgroundResource(R.color.colorBlue);
        } else {
            //Red
            holder.viewTab.setBackgroundResource(R.color.colorGreen);
        }

//        Set label orange or red.
        for (int x = 0; x < mPoliceInfo.get(i).getTag().size(); x++) {
//            Log.d("tag", i + " : " + x + " : " + polis.getTag().size());
//            Log.d("tag", polis.getFirstName() + ":" + polis.getTag().get(x));
            if (x == 0) {
                if (mPoliceInfo.get(i).getTag().get(x).equals("ตม.")) {
                    holder.tvPosition2.setVisibility(View.VISIBLE);
                    holder.tvPosition2.setBackgroundResource(R.drawable.tv_red_tag);
                    holder.tvPosition2.setText(mPoliceInfo.get(i).getTag().get(x));
                }
                if (mPoliceInfo.get(i).getTag().get(x).equals("ผบก.")) {
                    holder.tvPosition2.setVisibility(View.VISIBLE);
                    holder.tvPosition2.setBackgroundResource(R.drawable.tv_orange_tag);
                    holder.tvPosition2.setText(mPoliceInfo.get(i).getTag().get(x));
                }
            }

            if (x == 1) {
                if (mPoliceInfo.get(i).getTag().get(x).equals("ผบก.")) {
                    holder.tvPosition1.setVisibility(View.VISIBLE);
                    holder.tvPosition1.setBackgroundResource(R.drawable.tv_orange_tag);
                    holder.tvPosition1.setText(mPoliceInfo.get(i).getTag().get(x));
                }
                if (mPoliceInfo.get(i).getTag().get(x).equals("ตม.")) {
                    holder.tvPosition1.setVisibility(View.VISIBLE);
                    holder.tvPosition1.setBackgroundResource(R.drawable.tv_red_tag);
                    holder.tvPosition1.setText(mPoliceInfo.get(i).getTag().get(x));
                }
            }

        }// end for loop.

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "onClick: clicked on: " + mPoliceInfo.get(i).getFirstName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ContactDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact_detail", mPoliceInfo);
                intent.putExtra("position", i);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPoliceInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgProfile;
        TextView tvName, tvPosition, tvDeparture, tvPosition1, tvPosition2, viewTab;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.layout_item);
            imgProfile = itemView.findViewById(R.id.imgInfo);
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvDeparture = itemView.findViewById(R.id.tvDepartment);
            tvPosition1 = itemView.findViewById(R.id.tv_position_1);
            tvPosition2 = itemView.findViewById(R.id.tv_position_2);
            viewTab = itemView.findViewById(R.id.view_tab);
        }
    }

}
