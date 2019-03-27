package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.policephonebook2019.R;

public class NotificationDetail extends AppCompatActivity {

    private TextView tvAbTitle;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        tvAbTitle = findViewById(R.id.tv_actionbar_back);
        imgBack = findViewById(R.id.img_back);

//        Customize action bar.
        tvAbTitle.setText("รายละเอียด");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
