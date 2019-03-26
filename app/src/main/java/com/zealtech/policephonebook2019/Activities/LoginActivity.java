package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Manager.UserManager;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.response.ResponseProfile;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    Button btnLogin;
    EditText edtUsername, edtPassword;
    private String username, password;
    private UserManager mManager;

    ProfileH profileH = new ProfileH();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_edit_profile);
        edtUsername = findViewById(R.id.username_input);
        edtPassword = findViewById(R.id.input_password);

        mManager = new UserManager(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {

        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        //Check empty field.
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
        } else {
            Api api = AppUtils.getApiService();
            Call<ResponseProfile> call = api.login(username, password);
            call.enqueue(new Callback<ResponseProfile>() {
                @Override
                public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                    if (response.body() != null) {
                        if (response.body().getCode().equalsIgnoreCase("OK")) {
                            if (response.body().getCode().equals("OK")) {
                                profileH.setEditBy(response.body().getData().getEditBy());
                                profileH.setImageProfile(response.body().getData().getImageProfile());
                                profileH.setUserName(response.body().getData().getUserName());
                                profileH.setFirstName(response.body().getData().getFirstName());
                                profileH.setLastName(response.body().getData().getLastName());
                                profileH.setSuperAdmin(response.body().getData().getSuperAdmin());
                                profileH.setGender(response.body().getData().getGender());
                                profileH.setDepartmentId(response.body().getData().getDepartmentId());
                                profileH.setPositionId(response.body().getData().getPositionId());
                                profileH.setRankId(response.body().getData().getRankId());
                                profileH.setPhoneNumber(response.body().getData().getPhoneNumber());
                                profileH.setTag(response.body().getData().getTag());
                                profileH.setDepartmentName(response.body().getData().getDepartmentName());
                                profileH.setPositionName(response.body().getData().getPositionName());
                                profileH.setRankName(response.body().getData().getRankName());
                                profileH.setViews(response.body().getData().getViews());
                                profileH.setFavorites(response.body().getData().getFavorites());
                                profileH.setToken(response.body().getData().getToken());
                                profileH.setDepartmentPhoneNumber(response.body().getData().getDepartmentPhoneNumber());
                                profileH.setSuperAdmin(response.body().getData().getSuperAdmin());
                                profileH.setId(response.body().getData().getId());
                                profileH.setCreateDate(response.body().getData().getCreateDate());
                                profileH.setUpdateDate(response.body().getData().getUpdateDate());
                                profileH.setEnable(response.body().getData().getEnable());


//                                Save shared preferences.


                                Intent iUserDetail = new Intent(getApplicationContext(), UserDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user_profile", profileH);
                                iUserDetail.putExtras(bundle);
                                startActivity(iUserDetail);

                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                                Log.d(TAG, response.errorBody().string());
                            } else {
                                Log.d(TAG, response.errorBody().string());
                            }
                        } catch (JSONException e) {
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseProfile> call, Throwable t) {
                    Log.d(TAG, String.valueOf(call));
                    Log.d(TAG, String.valueOf(t));
                }
            });
        }
    }

}