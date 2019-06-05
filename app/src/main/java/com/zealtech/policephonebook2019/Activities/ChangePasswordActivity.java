package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.response.ResponseEditPassword;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private final String TAG = "ChangePasswordActivity";

    private TextView tvAbTitle;
    private ImageView imgBack;
    private EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    private Button btnSaveNewPass;
    private ProfileH mProfile = new ProfileH();
    private Api api = AppUtils.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tvAbTitle = findViewById(R.id.tv_actionbar_back);
        imgBack = findViewById(R.id.img_back);
        edtCurrentPassword = findViewById(R.id.edt_current_pass);
        edtNewPassword = findViewById(R.id.edt_new_pass);
        edtConfirmPassword = findViewById(R.id.edt_confirm_new_pass);
        btnSaveNewPass = findViewById(R.id.btn_change_password);

        mProfile = (ProfileH) getIntent().getSerializableExtra("user_profile");

        tvAbTitle.setText("เปลี่ยนรหัสผ่าน");

        btnSaveNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassword();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Check space in EditText.
        edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = edtNewPassword.getText().toString();
                Log.d(TAG, str);
                if (str.contains(" ")) {
                    Toast.makeText(getApplicationContext(), "ไม่อนุญาติให้มีช่องว่าง", Toast.LENGTH_LONG).show();
                    edtNewPassword.setText(edtNewPassword.getText().toString().replaceAll(" ", ""));
                    edtNewPassword.setSelection(edtNewPassword.getText().length());
                }
            }
        });

    }

    private void editPassword() {
        String currentPass, newPass, confirmNewPass, token;

        token = mProfile.getToken();
        currentPass = edtCurrentPassword.getText().toString().trim();
        newPass = edtNewPassword.getText().toString().trim();
        confirmNewPass = edtConfirmPassword.getText().toString().trim();

        if (currentPass == "" || newPass == "" || confirmNewPass == "") {
            Toast.makeText(this, "กรุณากรอกรหัสผ่านให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        } else if (currentPass.equals(newPass)) {
            Toast.makeText(this, "รหัสผ่านปัจจุบันซ้ำกับรหัสผ่านใหม่", Toast.LENGTH_SHORT).show();
        } else if (!newPass.equals(confirmNewPass)) {
            Toast.makeText(this, "กรุณากรอกยืนยันรหัสผ่านให้ถูกต้อง", Toast.LENGTH_SHORT).show();
        } else {
            //Call api.
            Log.d(TAG, mProfile.getToken());
            Call<ResponseEditPassword> call = api.editPassword(newPass, confirmNewPass, currentPass, token);
            call.enqueue(new Callback<ResponseEditPassword>() {
                @Override
                public void onResponse(Call<ResponseEditPassword> call, Response<ResponseEditPassword> response) {
                    if (response.body() != null) {
                        if (response.body().getCode().equalsIgnoreCase("OK")) {
                            if (response.body().getCode().equals("OK")) {
                                Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
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
                public void onFailure(Call<ResponseEditPassword> call, Throwable t) {

                }
            });
        } 
    }
}
