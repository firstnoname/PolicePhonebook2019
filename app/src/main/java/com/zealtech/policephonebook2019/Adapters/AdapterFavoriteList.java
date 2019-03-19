package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zealtech.policephonebook2019.Model.MockPolistInfo;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;

import java.util.ArrayList;
import java.util.List;

public class AdapterFavoriteList extends RecyclerView.Adapter<AdapterFavoriteList.ViewHolder> {
    private Context mContext;
    private ArrayList<PoliceMasterData> mPolisInfo;

    public AdapterFavoriteList(Context context, ArrayList<PoliceMasterData> dataSet) {
        mContext = context;
        mPolisInfo = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPosition, tvDeparture, tvPosition1, tvPosition2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvDeparture = itemView.findViewById(R.id.tvDepartment);
            tvPosition1 = itemView.findViewById(R.id.tv_position_1);
            tvPosition2 = itemView.findViewById(R.id.tv_position_2);
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


        }

    }

    @Override
    public int getItemCount() {
        return mPolisInfo.size();
    }
}
