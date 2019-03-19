package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.policephonebook2019.R;

public class UserDetailActivity extends AppCompatActivity {

    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        btnEditProfile = findViewById(R.id.btn_edit_profile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEditProfile = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(iEditProfile);
            }
        });
    }
}
