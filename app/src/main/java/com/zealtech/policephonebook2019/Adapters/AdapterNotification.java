package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        viewHolder.imgNoti.setImageResource(R.mipmap.noti_bl);
        viewHolder.tvNotiTitle.setText(mNoti.get(i).getTitle());
        viewHolder.tvNotiDate.setText(mNoti.get(i).getCreateDate());

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
