package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Profile;
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

    ArrayList<Profile> mProfile = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_edit_profile);
        edtUsername = findViewById(R.id.username_input);
        edtPassword = findViewById(R.id.input_password);

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

        //Check empty field
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
//                                mProfile.addAll(response.body().getData().);
//                                mProfile.addAll(response.body().getData().getContent());
                                Log.d(TAG, String.valueOf(response.body().getData().getContent().size()));
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
