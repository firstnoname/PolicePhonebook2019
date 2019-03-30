package com.zealtech.policephonebook2019.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;

import java.util.ArrayList;

public class StationStaffDetailActivity extends AppCompatActivity {

    private static final String TAG = "StationStaffDetail";


    private TextView tvName, tvPosition, tvDepartment, tvTel1, tvTel2, tvBack, tvUpdatedate;
    private ImageView imgFavorite, imgClose, imgProfile;
    private RelativeLayout relativeLayoutBackground;
    private int position;
    private String image_url;
    private String fullName, strPosition, department, tel1, tel2, rankName;


    ArrayList<Police> policeData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        relativeLayoutBackground = findViewById(R.id.layout_contact_detail);
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

        policeData = (ArrayList<Police>) getIntent().getSerializableExtra("contact_detail");
        position = getIntent().getIntExtra("position", 0);

        image_url = ApplicationConfig.getImageUrl() + policeData.get(position).getImageProfile();
        fullName = policeData.get(position).getFirstName() + "  " + policeData.get(position).getLastName();
        strPosition = policeData.get(position).getPositionName();
        department = policeData.get(position).getDepartmentName();
        rankName = policeData.get(position).getRankName();
        if (policeData.get(position).getDepartmentPhoneNumber().size() == 0) {
            tel1 = " - ";
        } else {
            tel1 = policeData.get(position).getDepartmentPhoneNumber().get(0);
        }

        tel2 = policeData.get(position).getPhoneNumber();

        if (rankName.equals("พล.ต.อ.") || rankName.equals("พล.ต.ท.")) {
            //Gold
            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg01);
        } else if (rankName.equals("พล.ต.ต.")) {
            //Blue sky
            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg02);
        } else if (rankName.equals("พ.ต.อ.") || rankName.equals("พ.ต.ท.")) {
            //Blue
            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg03);
        } else {
            //Red
            relativeLayoutBackground.setBackgroundResource(R.mipmap.bg04);
        }
        Glide.with(this).load(image_url).into(imgProfile);
        tvName.setText(fullName);
        tvPosition.setText(strPosition);
        tvDepartment.setText(department);
        tvTel1.setText(tel1);
        tvTel2.setText(tel2);
//        Todo: Don't have update date in PoliceMasterData.

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
                imgFavorite.setImageResource(R.mipmap.star_ac);
            }
        });
    }

}
