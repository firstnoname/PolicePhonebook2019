package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.policephonebook2019.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.titleGroup)
    public void gotoMainAct() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}