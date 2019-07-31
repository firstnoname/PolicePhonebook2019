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
import com.zealtech.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.ContactDetailActivity;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;
import com.zealtech.policephonebook2019.Model.base.BaseItem;
import com.zealtech.policephonebook2019.Model.base.BaseItemType;
import com.zealtech.policephonebook2019.Model.base.HeaderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPhoneList extends RecyclerView.Adapter {

    private static final String TAG = "AdapterPhoneList";

    private ArrayList<? extends BaseItem> mPoliceInfo;
    private Context mContext;
    private String fullName = "";
    private String IMAGE_URL = ApplicationConfig.getImageUrl();

    public AdapterPhoneList(Context mContext, ArrayList<PoliceMasterData> mPoliceInfo) {
        this(mContext, mPoliceInfo, false);
    }

    public AdapterPhoneList(Context mContext, ArrayList<PoliceMasterData> mPoliceInfo, Boolean isShowLabel) {
        this.mContext = mContext;
        if (isShowLabel) {
            this.mPoliceInfo = createLabel(mPoliceInfo);
        } else {
            this.mPoliceInfo = mPoliceInfo;
        }
    }


    private ArrayList<? extends BaseItem> createLabel(ArrayList<PoliceMasterData> mPoliceInfo) {
        ArrayList<BaseItem> mPolicInfoWithLabel = new ArrayList<>();

        Collections.sort(mPoliceInfo, new Comparator<PoliceMasterData>() {
            @Override
            public int compare(PoliceMasterData o1, PoliceMasterData o2) {
                return o1.getFirstNameAlphabetOnly().compareTo(o2.getFirstNameAlphabetOnly());
            }
        });

        char charFlag = Character.MIN_VALUE;
        for (PoliceMasterData data : mPoliceInfo) {
            if (charFlag != data.getFirstNameAlphabetOnly().charAt(0)) {
                //Add Header
                charFlag = data.getFirstNameAlphabetOnly().charAt(0);
                mPolicInfoWithLabel.add(new HeaderItem(String.valueOf(charFlag)));

            }
            mPolicInfoWithLabel.add(data);
        }

        return mPolicInfoWithLabel;
    }

    @Override
    public int getItemViewType(int position) {
        if (mPoliceInfo.get(position) instanceof HeaderItem) {
            return BaseItemType.HEADER.ordinal();
        } else {
            return BaseItemType.ITEM.ordinal();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == BaseItemType.HEADER.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
            return new LabelViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_phone_list, viewGroup, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        //        Log.d(TAG, "onBindViewHolder: called");

        if (viewHolder instanceof LabelViewHolder) {
            final LabelViewHolder holder = (LabelViewHolder) viewHolder;

            holder.tvItemRowLetter.setText(mPoliceInfo.get(i).getName());

        } else {
            final ItemViewHolder holder = (ItemViewHolder) viewHolder;

            BaseItem item = mPoliceInfo.get(holder.getAdapterPosition());

            if (item instanceof PoliceMasterData) {
                PoliceMasterData data = (PoliceMasterData) item;

                if (data.getImageProfile() != null) {
                    Glide.with(mContext)
                            .load(IMAGE_URL + data.getImageProfile())
                            .into(holder.imgProfile);
                }

                if (data.getColor() != null) {
                    holder.viewTab.setBackgroundColor(Color.parseColor(data.getColor()));
                }

                fullName = data.getRankName() + " " + data.getFirstName() + "  " + data.getLastName();
                holder.tvName.setText(fullName);
                holder.tvPosition.setText(data.getPositionName());
                holder.tvDeparture.setText(data.getDepartmentName());

//                if (data.getRankName() != "") {
//                    holder.tvPosition2.setVisibility(View.VISIBLE);
//                    holder.tvPosition2.setText(data.getRankName());
//                } else {
//                    holder.tvPosition2.setVisibility(View.GONE);
//                }

                if (data.getTag() != null) {
                    for (int x = 0; x < data.getTag().size(); x++) {
                        holder.tvPosition2.setVisibility(View.VISIBLE);
                        holder.tvPosition2.setText(data.getTag().get(x));
                    }
                }

                holder.parent_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, ContactDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contact_detail", mPoliceInfo);
                        intent.putExtra("position", holder.getAdapterPosition());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);

                    }
                });


            }
        }
    }

    @Override
    public int getItemCount() {
        return mPoliceInfo.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgProfile;
        TextView tvName, tvPosition, tvDeparture, tvPosition1, tvPosition2, viewTab;
        ConstraintLayout parent_layout;

        public ItemViewHolder(@NonNull View itemView) {
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

    public class LabelViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemRowLetter;

        public LabelViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemRowLetter = itemView.findViewById(R.id.item_row_letter);

        }
    }

}
