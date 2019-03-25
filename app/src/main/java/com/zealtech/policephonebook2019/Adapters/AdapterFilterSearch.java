package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.FilterActivity;

import java.util.ArrayList;

public class AdapterFilterSearch extends RecyclerView.Adapter<AdapterFilterSearch.ViewHolder> {

    private static final String TAG = "AdapterFilterSearch";

    private ArrayList<String> mRank = new ArrayList<>();
    private Context mContext;
    private String tagFilter;
    private String valueFilter = "";

    public AdapterFilterSearch(Context mContext, ArrayList<String> mRank, String tagFilter) {
        this.mRank = mRank;
        this.mContext = mContext;
        this.tagFilter = tagFilter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_filter_search, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.imgInfo.setImageResource(R.drawable.policestation_ic);
        viewHolder.txtInfo.setText(mRank.get(i));

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueFilter = mRank.get(i);
                Toast.makeText(mContext, mRank.get(i), Toast.LENGTH_SHORT).show();
                Intent iSearchFragment = new Intent(mContext, FilterActivity.class);
                iSearchFragment.putExtra("tagFilter", tagFilter);
                iSearchFragment.putExtra("valueFilter", valueFilter);
                mContext.startActivity(iSearchFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRank.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgInfo;
        TextView txtInfo;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.layout_adapter_filter_search);
            imgInfo = itemView.findViewById(R.id.img_info);
            txtInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
