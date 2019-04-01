package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;

import java.util.ArrayList;

public class AdapterFavoriteList extends RecyclerView.Adapter<AdapterFavoriteList.ViewHolder> {
    private Context mContext;
    private ArrayList<PoliceMasterData> mPolisInfo;

    public AdapterFavoriteList(Context context, ArrayList<PoliceMasterData> dataSet) {
        mContext = context;
        mPolisInfo = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPosition, tvDeparture, tvPosition1, tvPosition2;
        public TextView viewTab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_noti_title);
            tvPosition = itemView.findViewById(R.id.tv_noti_date);
            tvDeparture = itemView.findViewById(R.id.tvDepartment);
            tvPosition1 = itemView.findViewById(R.id.tv_position_1);
            tvPosition2 = itemView.findViewById(R.id.tv_position_2);
            viewTab = itemView.findViewById(R.id.view_tab);
        }
    }

    @NonNull
    @Override
    public AdapterFavoriteList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_phone_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavoriteList.ViewHolder viewHolder, int i) {
        PoliceMasterData polis = mPolisInfo.get(i);

        viewHolder.tvName.setText(polis.getFirstName());
        viewHolder.tvPosition.setText(polis.getPositionName());
        viewHolder.tvDeparture.setText(polis.getDepartmentName());

        String rankName = polis.getRankName();
//        Set view_tab color from rank.
        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
            //Gold
            viewHolder.viewTab.setBackgroundResource(R.color.colorYellow);
        } else if (rankName.equals("พล.ต.ต.")) {
            //Blue sky
            viewHolder.viewTab.setBackgroundResource(R.color.colorGreen);
        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
            //Blue
            viewHolder.viewTab.setBackgroundResource(R.color.colorBlue);
        } else {
            //Red
            viewHolder.viewTab.setBackgroundResource(R.color.colorGreen);
        }

//        Set label orange or red.
        for (int x = 0; x < polis.getTag().size(); x++) {
//            Log.d("tag", i + " : " + x + " : " + polis.getTag().size());
//            Log.d("tag", polis.getFirstName() + ":" + polis.getTag().get(x));
            if (x == 0) {
                if (polis.getTag().get(x).equals("ตม.")) {
                    viewHolder.tvPosition2.setVisibility(View.VISIBLE);
                    viewHolder.tvPosition2.setBackgroundResource(R.drawable.tv_red_tag);
                    viewHolder.tvPosition2.setText(polis.getTag().get(x));
                }
                if (polis.getTag().get(x).equals("ผบก.")) {
                    viewHolder.tvPosition2.setVisibility(View.VISIBLE);
                    viewHolder.tvPosition2.setBackgroundResource(R.drawable.tv_orange_tag);
                    viewHolder.tvPosition2.setText(polis.getTag().get(x));
                }
            }

            if (x == 1) {
                if (polis.getTag().get(x).equals("ผบก.")) {
                    viewHolder.tvPosition1.setVisibility(View.VISIBLE);
                    viewHolder.tvPosition1.setBackgroundResource(R.drawable.tv_orange_tag);
                    viewHolder.tvPosition1.setText(polis.getTag().get(x));
                }
                if (polis.getTag().get(x).equals("ตม.")) {
                    viewHolder.tvPosition1.setVisibility(View.VISIBLE);
                    viewHolder.tvPosition1.setBackgroundResource(R.drawable.tv_red_tag);
                    viewHolder.tvPosition1.setText(polis.getTag().get(x));
                }
            }

        }// end for loop.

    }

    @Override
    public int getItemCount() {
        return mPolisInfo.size();
    }
}
