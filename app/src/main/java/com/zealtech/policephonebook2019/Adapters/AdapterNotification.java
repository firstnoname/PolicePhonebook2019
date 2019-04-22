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
import com.zealtech.policephonebook2019.Activities.NotificationDetail;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Notification;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {

    private static final String TAG = "AdapterNotificationList";
    private ArrayList<Notification> mNoti;
    private Context mContext;
    private String IMAGE_URL = ApplicationConfig.getImageUrl();

    public AdapterNotification(Context mContext, ArrayList<Notification> mNoti) {
        this.mNoti = mNoti;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_notifications, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (mNoti.get(i).getPicturesPath().get(0) != null) {
            Glide.with(mContext).load(IMAGE_URL + mNoti.get(i).getPicturesPath().get(0)).fitCenter().into(viewHolder.imgNoti);
            Log.d(TAG, IMAGE_URL + mNoti.get(i).getPicturesPath().get(0));
        } else {
            viewHolder.imgNoti.setImageResource(R.mipmap.noti_bl);
        }
        viewHolder.tvNotiTitle.setText(mNoti.get(i).getTitle());

        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNotiDetail = new Intent(mContext, NotificationDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("noti_detail", mNoti.get(i));
                iNotiDetail.putExtras(bundle);
                mContext.startActivity(iNotiDetail);
            }
        });

        String dateFormat = mNoti.get(0).getUpdateDate().substring(0,10);
        String date = dateFormat.substring(8);
        String month = dateFormat.substring(5);
        month = month.substring(0, 2);
        if (month.equals("01")) {
            month = "มกราคม";
        }
        if (month.equals("02")) {
            month = "กุมภาพันธ์";
        }
        if (month.equals("03")) {
            month = "มีนาคม";
        }
        if (month.equals("04")) {
            month = "เมษายน";
        }
        if (month.equals("05")) {
            month = "พฤษภาคม";
        }
        if (month.equals("06")) {
            month = "มิถุนายน";
        }
        if (month.equals("07")) {
            month = "กรกฎาคม";
        }
        if (month.equals("08")) {
            month = "สิงหาคม";
        }
        if (month.equals("09")) {
            month = "กันยายน";
        }
        if (month.equals("10")) {
            month = "ตุลาคม";
        }
        if (month.equals("11")) {
            month = "พฤษจิกายน";
        }
        if (month.equals("12")) {
            month = "ธันวาคม";
        }
        String year = dateFormat.substring(0, 4);

        viewHolder.tvNotiDate.setText("วันที่อัพเดทข้อมูล " + date + " " + month + " " + year);
    }

    @Override
    public int getItemCount() {

        return mNoti.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgNoti;
        TextView tvNotiTitle, tvNotiDate;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.layout_item);
            imgNoti = itemView.findViewById(R.id.img_noti);
            tvNotiTitle = itemView.findViewById(R.id.tv_noti_title);
            tvNotiDate = itemView.findViewById(R.id.tv_noti_date);
        }
    }
}
