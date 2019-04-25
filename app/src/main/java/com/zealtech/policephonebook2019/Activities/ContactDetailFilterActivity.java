package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Favorite;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponseFavorite;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDetailFilterActivity extends AppCompatActivity {

    private static final String TAG = "ContactDetailActivity";


    private TextView tvName, tvPosition, tvDepartment, tvTel1, tvTel2, tvBack, tvUpdatedate;
    private ImageView imgFavorite, imgClose, imgProfile, imgTelWork, imgTel;
    private RelativeLayout relativeLayoutBackground;
    private int position;
    private String image_url;
    private String fullName, strPosition, department, tel1, tel2, rankName;
    private Boolean favTag = false;
    private String token = "";
    private String id = "";
    private String updateDate = "";

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
        imgTelWork = findViewById(R.id.img_tel_work);
        imgTel = findViewById(R.id.img_tel);

        policeMasterData = (ArrayList<Police>) getIntent().getSerializableExtra("contact_detail");
        position = getIntent().getIntExtra("position", 0);

        image_url = ApplicationConfig.getImageUrl() + policeMasterData.get(position).getImageProfile();
        fullName = policeMasterData.get(position).getRankName() + " " + policeMasterData.get(position).getFirstName() + "  " + policeMasterData.get(position).getLastName();
        strPosition = policeMasterData.get(position).getPositionName();
        department = policeMasterData.get(position).getDepartmentName();
        rankName = policeMasterData.get(position).getRankName();
        id = policeMasterData.get(position).getId();

        tel1 = policeMasterData.get(position).getWorkPhoneNumber();
        if (tel1 != "") {
            tvTel1.setText(tel1);
            imgTelWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:" + tel1));
                    startActivity(i);
                }
            });
        } else {
            tvTel1.setText("ไม่มีข้อมูล");
            imgTelWork.setClickable(false);
        }
        tel2 = policeMasterData.get(position).getPhoneNumber();
        if (tel2 != "") {
            tvTel2.setText(tel2);
            imgTel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:" + tel2));
                    startActivity(i);
                }
            });
        } else {
            //imgTel.setClickable(false);
        }

//        callRankApi();
        if (policeMasterData.get(position).getColor() != null) {
            relativeLayoutBackground.setBackgroundColor(Color.parseColor(policeMasterData.get(position).getColor()));
        }

        if (policeMasterData.get(position).getImageProfile() != null) {
            Glide.with(this).load(image_url).into(imgProfile);
        }
        tvName.setText(fullName);
        tvPosition.setText(strPosition);
        tvDepartment.setText(department);

        if (policeMasterData.get(position).getUpdateDate() != null) {
            String dateFormat = policeMasterData.get(position).getUpdateDate().substring(0,10);
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

            tvUpdatedate.setText("วันที่อัพเดทข้อมูล " + date + " " + month + " " + year);
        }



        isFavorite();

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
                if (favTag.equals(true)) {
                    imgFavorite.setImageResource(R.mipmap.star);
                    favTag = false;
                    addFavorite();
                } else {
                    imgFavorite.setImageResource(R.mipmap.star_ac);
                    favTag = true;
                    addFavorite();
                }
            }
        });

    }

    private void isFavorite() {
        
        checkLogin();
        
        Call<ResponseFavorite> call = api.getFavorite(token);
        call.enqueue(new Callback<ResponseFavorite>() {
            @Override
            public void onResponse(Call<ResponseFavorite> call, Response<ResponseFavorite> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                String apiId = response.body().getData().get(i).getId();
                                String selectedId = policeMasterData.get(position).getId();
                                if (selectedId.equals(apiId)) {
//                                    Toast.makeText(ContactDetailFilterActivity.this, "True", Toast.LENGTH_SHORT).show();
                                    imgFavorite.setImageResource(R.mipmap.star_ac);
                                } else {
//                                    Toast.makeText(ContactDetailFilterActivity.this, selectedId + " : " + apiId, Toast.LENGTH_SHORT).show();
                                    imgFavorite.setImageResource(R.mipmap.star);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("code").equals("no_user_found")) {
                            Log.d(TAG, String.valueOf(jObjError.get("code")));
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, String.valueOf(jObjError.get("code")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFavorite> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });
    }

//    private void callRankApi() {
//        Call<ResponseRank> call = api.getRankMasterData("");
//        call.enqueue(new Callback<ResponseRank>() {
//            @Override
//            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
//                if (response.body() != null) {
//                    if (response.body().getCode().equalsIgnoreCase("OK")) {
//                        if (response.body().getCode().equals("OK")) {
//                            checkRank(response.body().getData());
//                        } else {
//                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseRank> call, Throwable t) {
//                Log.d("response", String.valueOf(t));
//            }
//        });
//    }

    private void checkRank(ArrayList<Rank> data) {
        for (int i = 0; i < data.size(); i++) {
            if (rankName.equals(data.get(i).getShortName())) {
                relativeLayoutBackground.setBackgroundColor(Color.parseColor(data.get(i).getColor()));
            }
        }
    }

    private void checkLogin() {
        SharedPreferences mPref = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString("ProfileObject", "");
        ProfileH mProfile = gson.fromJson(json, ProfileH.class);

        if (mProfile != null) {
            token = mProfile.getToken();
        } else {
            token = "";
        }

    }

    private void addFavorite() {
        //Call api add fav.
//        Toast.makeText(this, policeMasterData.get(position).getId(), Toast.LENGTH_SHORT).show();
        if (favTag.equals(true)) {
            Call<ResponseFavorite> call = api.addFavorite(id, token);
            call.enqueue(new Callback<ResponseFavorite>() {
                @Override
                public void onResponse(Call<ResponseFavorite> call, Response<ResponseFavorite> response) {
                    if (response.body() != null) {
                        if (response.body().getCode().equalsIgnoreCase("OK")) {
                            if (response.body().getCode().equals("OK")) {
                                Toast.makeText(ContactDetailFilterActivity.this, "Save favorite successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            if (jObjError.has("code") && jObjError.get("code").equals("no_user_found")) {
                                Log.d(TAG, String.valueOf(jObjError.get("code")));
                            } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                                Log.d(TAG, String.valueOf(jObjError.get("code")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, String.valueOf(e));
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, String.valueOf(e));

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseFavorite> call, Throwable t) {

                }
            });
        } else {
            Call<ResponseFavorite> call = api.removeFavorite(id, token);
            call.enqueue(new Callback<ResponseFavorite>() {
                @Override
                public void onResponse(Call<ResponseFavorite> call, Response<ResponseFavorite> response) {
                    if (response.body() != null) {
                        if (response.body().getCode().equalsIgnoreCase("OK")) {
                            if (response.body().getCode().equals("OK")) {
//                                Toast.makeText(ContactDetailFilterActivity.this, "Remove favorite successful", Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            if (jObjError.has("code") && jObjError.get("code").equals("no_user_found")) {
                                Log.d(TAG, String.valueOf(jObjError.get("code")));
                            } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                                Log.d(TAG, String.valueOf(jObjError.get("code")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, String.valueOf(e));
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, String.valueOf(e));

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseFavorite> call, Throwable t) {

                }
            });
        }
    }

}
