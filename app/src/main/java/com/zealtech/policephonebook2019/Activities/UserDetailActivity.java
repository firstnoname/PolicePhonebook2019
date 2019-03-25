package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.ProfileH;

public class UserDetailActivity extends AppCompatActivity {

    private Button btnEditProfile;
    private ImageView imgProfile, btnClose;
    private TextView tvName, tvRank, tvDepartment, tvTel, tvPhone, tvUpdateDate;

//    vars
    private String IMAGE_URL = "http://ztidev.com:8081/phonebook/download?file=";
    private ProfileH mProfile = new ProfileH();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        imgProfile = findViewById(R.id.img_user_profile);
        tvName = findViewById(R.id.tv_name_user_profile);
        tvRank = findViewById(R.id.tv_rank_profile);
        tvDepartment = findViewById(R.id.tv_department_profile);
        tvTel = findViewById(R.id.tv_tel_profile);
        tvPhone = findViewById(R.id.tv_phone_profile);
        tvUpdateDate = findViewById(R.id.tv_update_profile);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnClose = findViewById(R.id.img_close);

        mProfile = (ProfileH) getIntent().getSerializableExtra("user_profile");

        Glide.with(this).load(IMAGE_URL + mProfile.getImageProfile()).into(imgProfile);
        tvName.setText(mProfile.getFirstName() + "  " + mProfile.getLastName());
        tvRank.setText(mProfile.getRankName());
        tvDepartment.setText(mProfile.getDepartmentName());
        tvTel.setText(mProfile.getPhoneNumber());
        tvPhone.setText(mProfile.getPhoneNumber());
        tvUpdateDate.setText(mProfile.getUpdateDate());

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEditProfile = new Intent(getApplicationContext(), EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_profile", mProfile);
                iEditProfile.putExtras(bundle);
                startActivity(iEditProfile);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}