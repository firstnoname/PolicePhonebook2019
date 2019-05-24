package com.zealtech.policephonebook2019.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Realm.PoliceHistory;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponseFavorite;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDetailActivity extends AppCompatActivity {

    private static final String TAG = "ContactDetailActivity";


    private TextView tvName, tvPosition, tvDepartment, tvTel1, tvTel2, tvBack, tvUpdatedate;
    private ImageView imgFavorite, imgClose, imgProfile, imgTelWork, imgTelPhone;
    private RelativeLayout relativeLayoutBackground;
    private LinearLayout linearPositionSection;
    private int position;
    private String image_url;
    private String fullName, strPosition, department, tel1, tel2, rankName;
    private Boolean favTag = false;
    private String token = "";
    private String id = "";

    ArrayList<PoliceMasterData> policeMasterData = new ArrayList<>();
    ArrayList<Police> mPolice = new ArrayList<>();
    ArrayList<Rank> ranks = new ArrayList<>();

    Api api = AppUtils.getApiService();

    private Realm mRealm;

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
        linearPositionSection = findViewById(R.id.position_section);
        imgTelWork = findViewById(R.id.img_tel_work);
        imgTelPhone = findViewById(R.id.img_tel);

        policeMasterData = (ArrayList<PoliceMasterData>) getIntent().getSerializableExtra("contact_detail");
        position = getIntent().getIntExtra("position", 0);
        id = policeMasterData.get(position).getId();

//        Fetch contact detail.
        callUserDetail(id);

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

    private void callUserDetail(String id) {
        Call<ResponsePoliceList> call = api.getPoliceList("", "", "", "", "", "",4);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mPolice.addAll(response.body().getData().getContent());
                            checkColor();

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
            public void onFailure(Call<ResponsePoliceList> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });
    }

    private void checkColor() {
        Call<ResponseRank> call = api.getRankMasterData("");
        call.enqueue(new Callback<ResponseRank>() {
            @Override
            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            ranks.addAll(response.body().getData());
                            for (int x = 0; x < mPolice.size(); x++) {
                                for (int i = 0; i < ranks.size(); i++) {
                                    if (mPolice.get(x).getRankId() == ranks.get(i).getRankId()) {
                                        mPolice.get(x).setColor(ranks.get(i).getColor());
                                    }
                                }
                            }
                            setData(mPolice);
                            saveHistory(mPolice);
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

    private void saveHistory(ArrayList<Police> mPolice) {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().name("sample1.realm")
                .schemaVersion(1).deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);

        mRealm = Realm.getDefaultInstance();

        final RealmResults<PoliceHistory> policeHistories = mRealm.where(PoliceHistory.class)
                .contains("id", mPolice.get(0).getId()).findAll();

        mRealm.beginTransaction();
//        mRealm.deleteAll();

        if (policeHistories.size() == 0) {

            PoliceHistory mPoliceHistory = mRealm.createObject(PoliceHistory.class);
            mPoliceHistory.setImageProfile(mPolice.get(0).getImageProfile());
            mPoliceHistory.setFirstName(mPolice.get(0).getFirstName());
            mPoliceHistory.setLastName(mPolice.get(0).getLastName());
            mPoliceHistory.setDepartmentName(mPolice.get(0).getDepartmentName());
            mPoliceHistory.setPositionName(mPolice.get(0).getPositionName());
            mPoliceHistory.setRankName(mPolice.get(0).getRankName());
            mPoliceHistory.setRankId(mPolice.get(0).getRankId());
            mPoliceHistory.setId(mPolice.get(0).getId());
            mPoliceHistory.setColor(mPolice.get(0).getColor());
            mPoliceHistory.setUpdateDate(mPolice.get(0).getUpdateDate());

        }

        mRealm.commitTransaction();
    }

    private void setData(ArrayList<Police> mPolice) {
        if (mPolice.get(0).getImageProfile() != "") {
            image_url = ApplicationConfig.getImageUrl() + mPolice.get(0).getImageProfile();
        }

        fullName = mPolice.get(0).getRankName() + " " + mPolice.get(0).getFirstName() + "  " + mPolice.get(0).getLastName();
        strPosition = mPolice.get(0).getPositionName();
        department = mPolice.get(0).getDepartmentName();
        rankName = mPolice.get(0).getRankName();
        if (mPolice.get(0).getWorkPhoneNumber().size() != 0) {
            tel1 = mPolice.get(0).getWorkPhoneNumber().get(0).getTel();
        } else {
            tel1 = "ไม่มีข้อมูล";
        }

        if (mPolice.get(0).getPhoneNumber().size() != 0) {
            tel2 = mPolice.get(0).getPhoneNumber().get(0).getTel();
        } else {
            tel2 = "ไม่มีข้อมูล";
        }

        relativeLayoutBackground.setBackgroundColor(Color.parseColor(mPolice.get(0).getColor()));
        if (mPolice.get(0).getImageProfile() != null) {
            Glide.with(this).load(image_url).into(imgProfile);
        }

        tvName.setText(fullName);
        tvPosition.setText(strPosition);
        tvDepartment.setText(department);
        tvTel1.setText(tel1);
        if (tel1 != "") {
            tvTel1.setText(tel1);
        } else {
            tvTel1.setText("ไม่มีข้อมูล");
            imgTelWork.setClickable(false);
        }
        tvTel2.setText(tel2);
        if (tel2 != "") {
            tvTel2.setText(tel2);
        } else {
            tvTel2.setText("ไม่มีข้อมูล");
            imgTelPhone.setClickable(false);
        }

        String dateFormat = mPolice.get(0).getCreateDate().substring(0,10);
        String date = dateFormat.substring(8);
        String month = dateFormat.substring(5);
        String year = dateFormat.substring(0, 4);
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

        tvUpdatedate.setText("วันที่อัพเดทข้อมูล " + date + " " + month + " " + year);
    }


    private void isFavorite() {

        checkLogin();

        if (token.equals(null)) {
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
        } else {

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
        if (favTag.equals(true)) {
            Call<ResponseFavorite> call = api.addFavorite(id, token);
            call.enqueue(new Callback<ResponseFavorite>() {
                @Override
                public void onResponse(Call<ResponseFavorite> call, Response<ResponseFavorite> response) {
                    if (response.body() != null) {
                        if (response.body().getCode().equalsIgnoreCase("OK")) {
                            if (response.body().getCode().equals("OK")) {
                                Toast.makeText(ContactDetailActivity.this, "Save favorite successful", Toast.LENGTH_SHORT).show();
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
