package com.zealtech.policephonebook2019.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.ContactDetailFilterActivity;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPhoneListFilter extends RecyclerView.Adapter<AdapterPhoneListFilter.ViewHolder> {

    private static final String TAG = "AdapterPhoneList";
    Api api = AppUtils.getApiService();
    private ArrayList<Police> mPoliceInfo;
    private Context mContext;
    private String fullName = "";
    private String IMAGE_URL = ApplicationConfig.getImageUrl();
    private ArrayList<Rank> ranks = new ArrayList<>();

    public AdapterPhoneListFilter(Context mContext, ArrayList<Police> mPoliceInfo) {
        this.mPoliceInfo = mPoliceInfo;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterPhoneListFilter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_phone_list, viewGroup, false);
        AdapterPhoneListFilter.ViewHolder holder = new AdapterPhoneListFilter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPhoneListFilter.ViewHolder holder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");

        if (mPoliceInfo.get(i).getImageProfile() != null) {
            Glide.with(mContext)
                    .load(IMAGE_URL + mPoliceInfo.get(i).getImageProfile())
                    .into(holder.imgProfile);
        }

        fullName = mPoliceInfo.get(i).getRankName() + " " + mPoliceInfo.get(i).getFirstName() + "  " + mPoliceInfo.get(i).getLastName();
        holder.tvName.setText(fullName);
        holder.tvPosition.setText(mPoliceInfo.get(i).getPositionName());
//        holder.tvDeparture.setText(mPoliceInfo.get(i).getDepartmentName());

        //Check department root.
        if (mPoliceInfo.get(i).getDepartmentRoot() != null) {
            if (mPoliceInfo.get(i).getDepartmentRoot().size() != 0) {
                String depRoot = "";
//            for (int y = 0; y < mPoliceInfo.get(i).getDepartmentRoot().size(); y++) {
//                if (y == mPoliceInfo.get(i).getDepartmentRoot().size()-1) {
//                    SpannableString content = new SpannableString(mPoliceInfo.get(i).getDepartmentRoot().get(y).getDepartmentName());
//                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//                    holder.tvDeparture.setText(content);
//                }
//                if (y == 1) {
//                    depRoot = mPoliceInfo.get(i).getDepartmentRoot().get(y).getDepartmentName();
//                }
//                if (y == 2) {
//                    depRoot += " / " + mPoliceInfo.get(i).getDepartmentRoot().get(y).getDepartmentName();
//                }
//            }

                for (int y = mPoliceInfo.get(i).getDepartmentRoot().size() - 1; y >= 0; y--) {
                    if (y == mPoliceInfo.get(i).getDepartmentRoot().size() - 1) {
                        SpannableString content = new SpannableString(mPoliceInfo.get(i).getDepartmentRoot().get(y).getDepartmentName());
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        holder.tvDeparture.setText(content);
                    } else {
                        depRoot += mPoliceInfo.get(i).getDepartmentRoot().get(y).getDepartmentName();
                        depRoot += " / ";
                    }

                }
                holder.tvDepartureRoot.setText(depRoot);
            }
        }

        if (mPoliceInfo.get(i).getColor() != null) {
            holder.viewTab.setBackgroundColor(Color.parseColor(mPoliceInfo.get(i).getColor()));
        }

//        String rankName = mPoliceInfo.get(i).getRankName();
//        Set view_tab color from rank.
//        Call<ResponseRank> call = api.getRankMasterData("");
//        call.enqueue(new Callback<ResponseRank>() {
//            @Override
//            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
//                if (response.body() != null) {
//                    if (response.body().getCode().equalsIgnoreCase("OK")) {
//                        if (response.body().getCode().equals("OK")) {
//                            ranks = response.body().getData();
//                        } else {
//                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(mContext, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//
//                    } catch (Exception e) {
//                        Toast.makeText(mContext, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseRank> call, Throwable t) {
//                Log.d("response", String.valueOf(t));
//            }
//        });
//
//        for (int x = 0; x < ranks.size(); x++) {
//            if (rankName.equals(ranks.get(x).getShortName())) {
//                holder.viewTab.setBackgroundColor(Color.parseColor(ranks.get(x).getColor()));
//            }
//        }
//        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
//            //Gold
////            holder.imgProfile.setBorderColor(Color.YELLOW);
//            holder.viewTab.setBackgroundResource(R.color.colorYellow);
//        } else if (rankName.equals("พล.ต.ต.")) {
//            //Blue sky
////            holder.imgProfile.setBorderColor(Color.GREEN);
//            holder.viewTab.setBackgroundResource(R.color.colorGreen);
//        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
//            //Blue
////            holder.imgProfile.setBorderColor(Color.BLUE);
//            holder.viewTab.setBackgroundResource(R.color.colorBlue);
//        } else {
//            //Red
////            holder.imgProfile.setBorderColor(Color.RED);
//            holder.viewTab.setBackgroundResource(R.color.colorRed);
//        }

        if (mPoliceInfo.get(i).getTag() != null) {
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
        }

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "onClick: clicked on: " + mPoliceInfo.get(i).getFirstName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ContactDetailFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact_detail", mPoliceInfo);
                intent.putExtra("position", i);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPoliceInfo.size();
    }

    public void updateList(ArrayList<Police> newList) {
        mPoliceInfo = new ArrayList<>();
        mPoliceInfo.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgProfile;
        TextView tvName, tvPosition, tvDeparture, tvDepartureRoot, tvPosition1, tvPosition2, viewTab;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.layout_item);
            imgProfile = itemView.findViewById(R.id.img_noti);
            tvName = itemView.findViewById(R.id.tv_noti_title);
            tvPosition = itemView.findViewById(R.id.tv_noti_date);
            tvDeparture = itemView.findViewById(R.id.tvDepartment);
            tvDepartureRoot = itemView.findViewById(R.id.tvDepRoot);
            tvPosition1 = itemView.findViewById(R.id.tv_position_1);
            tvPosition2 = itemView.findViewById(R.id.tv_position_2);
            viewTab = itemView.findViewById(R.id.view_tab);

        }
    }

}
