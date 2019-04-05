package com.zealtech.policephonebook2019.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDetailFilterActivity extends AppCompatActivity {

    private static final String TAG = "ContactDetailActivity";


    private TextView tvName, tvPosition, tvDepartment, tvTel1, tvTel2, tvBack, tvUpdatedate;
    private ImageView imgFavorite, imgClose, imgProfile;
    private RelativeLayout relativeLayoutBackground;
    private int position;
    private String image_url;
    private String fullName, strPosition, department, tel1, tel2, rankName;

    ArrayList<Police> policeMasterData = new ArrayList<>();

    Api api = AppUtils.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        relativeLayoutBackground = findViewById(R.id.background_rank_color);
        imgProfile = findViewById(R.id.img_contact_profile);
        tvName = findViewById(R.id.tv_contact_name);
        tvPosition = findViewById(R.id.tv_contact_position);
        tvDepartment = findViewById(R.id.tv_contact_department);
        tvTel1 = findViewById(R.id.tv_contact_tel);
        tvTel2 = findViewById(R.id.tv_contact_phone);
        imgFavorite = findViewById(R.id.imgFavorite);
        imgClose = findViewById(R.id.imgBack);
        tvBack = findViewById(R.id.tvBack);
        tvUpdatedate = findViewById(R.id.tv_contact_update_date);

        policeMasterData = (ArrayList<Police>) getIntent().getSerializableExtra("contact_detail");
        position = getIntent().getIntExtra("position", 0);

        image_url = ApplicationConfig.getImageUrl() + policeMasterData.get(position).getImageProfile();
        fullName = policeMasterData.get(position).getFirstName() + "  " + policeMasterData.get(position).getLastName();
        strPosition = policeMasterData.get(position).getPositionName();
        department = policeMasterData.get(position).getDepartmentName();
        rankName = policeMasterData.get(position).getRankName();
        callRankApi();
//        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
//            //Gold
//            //relativeLayoutBackground.setBackgroundResource(R.mipmap.bg01);
//            relativeLayoutBackground.setBackgroundColor(0xFF00FF00);
//        } else if (rankName.equals("พล.ต.ต.")) {
//            //Blue sky
////            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg02);
//            relativeLayoutBackground.setBackgroundColor(0xFF00FF00);
//        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
//            //Blue
////            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg03);
//            relativeLayoutBackground.setBackgroundColor(0xFF00FF00);
//        } else {
//            //Red
////            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg04);
//            relativeLayoutBackground.setBackgroundColor(0xFF00FF00);
//        }
        Glide.with(this).load(image_url).into(imgProfile);
        tvName.setText(fullName);
        tvPosition.setText(strPosition);
        tvDepartment.setText(department);
//        Todo: Don't have update date in PoliceMasterData.

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFavorite.setImageResource(R.mipmap.star_ac);
            }
        });
    }

    private void callRankApi() {
        Call<ResponseRank> call = api.getRankMasterData("");
        call.enqueue(new Callback<ResponseRank>() {
            @Override
            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            checkRank(response.body().getData());
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRank> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    private void checkRank(ArrayList<Rank> data) {
        for (int i = 0; i < data.size(); i++) {
            if (rankName.equals(data.get(i).getShortName())) {
                relativeLayoutBackground.setBackgroundColor(Color.parseColor(data.get(i).getColor()));
            }
        }
    }

}
