package com.example.policephonebook2019.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policephonebook2019.Model.MockPolistInfo;
import com.example.policephonebook2019.R;

import java.util.List;

public class AdapterFavoriteList extends RecyclerView.Adapter<AdapterFavoriteList.ViewHolder> {
    private Context mContext;
    private List<MockPolistInfo> mPolisInfo;

    public AdapterFavoriteList(Context context, List<MockPolistInfo> dataSet) {
        mContext = context;
        mPolisInfo = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPosition, tvDeparture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvDeparture = itemView.findViewById(R.id.tvDepartment);
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
        MockPolistInfo polis = mPolisInfo.get(i);

        viewHolder.tvName.setText(polis.getName());
        viewHolder.tvPosition.setText(polis.getPosition());
        viewHolder.tvDeparture.setText(polis.getDeparture());

    }

    @Override
    public int getItemCount() {
        return mPolisInfo.size();
    }
}
