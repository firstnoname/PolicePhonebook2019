package com.zealtech.policephonebook2019.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterFilterSearch extends RecyclerView.Adapter<AdapterFilterSearch.ViewHolder> {

    private static final String TAG = "AdapterFilterSearch";

    private List<BaseFilterItem> mTag;
    private Activity mActivity;
    private String tagFilter;
    private String valueFilter = "";
    private String IMAGE_URL = ApplicationConfig.getImageUrl();

    private String info, detail;

    public AdapterFilterSearch(Activity mActivity, List<BaseFilterItem> mTag, String tagFilter) {
        this.mTag = mTag;
        this.mActivity = mActivity;
        this.tagFilter = tagFilter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_filter_search,
                viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        String shortName = "";

        if (tagFilter.equals("province")) {

            Province provinces = (Province) mTag.get(i);

            if (provinces.getProvinceName() != null) {
                info = provinces.getProvinceName();
                viewHolder.tvDetail.setVisibility(View.GONE);
                viewHolder.txtInfo.setText(info);
                viewHolder.txtInfo.setGravity(Gravity.CENTER_VERTICAL);
            }

        }

        if (tagFilter.equals("position")) {

            Position positions = (Position) mTag.get(i);

            if (positions.getShortName() != null) {
                shortName = "(" + positions.getShortName() + ")";
            }

            info = positions.getPositionName() + " " + shortName;

            if (positions.getTag() != null) {
                for (int tagSize = 0; tagSize < positions.getTag().size(); tagSize++) {
                    viewHolder.tvDetail.setText(positions.getTag().get(tagSize));
                }
            } else {
                viewHolder.tvDetail.setVisibility(View.GONE);
            }

            viewHolder.txtInfo.setText(info);
            viewHolder.txtInfo.setGravity(Gravity.CENTER_VERTICAL);


            if (positions.getIcon() != null) {
                Glide.with(mActivity).load(IMAGE_URL + positions.getIcon()).fitCenter().into(viewHolder.imgInfo);
            } else {
                viewHolder.imgInfo.setImageResource(R.mipmap.all_ic);
            }
        }

        if (tagFilter.equals("rank")) {

            Rank ranks = (Rank) mTag.get(i);

            if (ranks.getShortName() != null) {
                shortName = "(" + ranks.getShortName() + ")";
            }

            info = ranks.getRankName() + " " + shortName;

            viewHolder.tvDetail.setVisibility(View.GONE);
            viewHolder.txtInfo.setText(info);
            viewHolder.txtInfo.setGravity(Gravity.CENTER_VERTICAL);


            if (ranks.getIcon() != null) {
                Glide.with(mActivity).load(IMAGE_URL + ranks.getIcon()).fitCenter().into(viewHolder.imgInfo);
            } else {
                viewHolder.imgInfo.setImageResource(R.mipmap.all_ic);
            }
        }

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iSearchFragment = new Intent();
                iSearchFragment.putExtra("tagFilter", tagFilter);
                iSearchFragment.putExtra("valueFilter", mTag.get(i));
                mActivity.setResult(Activity.RESULT_OK, iSearchFragment);
                mActivity.finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTag.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgInfo;
        TextView txtInfo, tvDetail;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.layout_adapter_filter_search);
            imgInfo = itemView.findViewById(R.id.img_info);
            txtInfo = itemView.findViewById(R.id.tv_info);
            tvDetail = itemView.findViewById(R.id.tv_detail);
        }
    }

    public void updateList(ArrayList<BaseFilterItem> newList) {
        mTag = new ArrayList<>();
        mTag.addAll(newList);
        notifyDataSetChanged();
    }
}
