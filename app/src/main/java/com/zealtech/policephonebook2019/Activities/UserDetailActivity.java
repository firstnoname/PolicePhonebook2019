package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.ProfileH;

public class UserDetailActivity extends AppCompatActivity {

    private Button btnEditProfile, btnLogout;
    private ImageView imgProfile, btnClose;
    private TextView tvName, tvRank, tvPosition, tvTel, tvPhone, tvUpdateDate;
    private RelativeLayout relative_background;

//    vars
    private String image_url;
    private String rankName;
    private ProfileH mProfile = new ProfileH();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

//        Bind widget.
        relative_background = findViewById(R.id.background_user_detail);
        imgProfile = findViewById(R.id.img_user_profile);
        tvName = findViewById(R.id.tv_name_user_profile);
        tvRank = findViewById(R.id.tv_rank_name);
        tvPosition = findViewById(R.id.tv_position_name);
        tvTel = findViewById(R.id.tv_tel_profile);
        tvPhone = findViewById(R.id.tv_phone_profile);
        tvUpdateDate = findViewById(R.id.tv_update_profile);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnClose = findViewById(R.id.img_close);
        btnLogout = findViewById(R.id.btn_logout);

//        Get data from LoginActivity.
        mProfile = (ProfileH) getIntent().getSerializableExtra("user_profile");

//        Set value into view.
        image_url = ApplicationConfig.getImageUrl() + mProfile.getImageProfile();
        rankName = mProfile.getRankName();
        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
            //Gold
            relative_background.setBackgroundResource(R.mipmap.bg01);
        } else if (rankName.equals("พล.ต.ต.")) {
            //Blue sky
            relative_background.setBackgroundResource(R.mipmap.bg02);
        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
            //Blue
            relative_background.setBackgroundResource(R.mipmap.bg03);
        } else {
            //Red
            relative_background.setBackgroundResource(R.mipmap.bg04);
        }

        Glide.with(this).load(image_url).into(imgProfile);
        tvName.setText(mProfile.getFirstName() + "  " + mProfile.getLastName());
        tvRank.setText(mProfile.getRankName());
        tvPosition.setText(mProfile.getPositionName());
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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(getApplicationContext(), LoginActivity.class);
                FirebaseMessaging.getInstance().unsubscribeFromTopic("2");
                FirebaseMessaging.getInstance().subscribeToTopic("1");

                SharedPreferences mPref = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPref.edit();
                prefsEditor.putString("ProfileObject", "");
                prefsEditor.putInt("Subscription", 1);
                prefsEditor.commit();

                startActivity(iLogin);

                finish();

            }
        });

    }
}