package com.zealtech.policephonebook2019.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.PagerAdapter;
import com.zealtech.policephonebook2019.Fragments.StationDetailFragment;
import com.zealtech.policephonebook2019.Fragments.StationStaffFragment;


public class StationDetailTabviewActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TextView tvAbTitle;
    private ImageView imgBack;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private String departmentId = "";
    private String stationName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail_tabview);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabStationDetail);
        viewPager = findViewById(R.id.pager);
        imgBack = findViewById(R.id.img_back);
        tvAbTitle = findViewById(R.id.tv_actionbar_back);

        ///Get departmentId and departmentName from subdepartment
        departmentId = String.valueOf(getIntent().getIntExtra("departmentId", 0));
        stationName = getIntent().getStringExtra("departmentName");

        adapter = new PagerAdapter(getSupportFragmentManager(), departmentId);
        adapter.AddFragment(new StationDetailFragment(), "ข้อมูลสถานี");
        adapter.AddFragment(new StationStaffFragment(), "เจ้าหน้าที่ในหน่วยงาน");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tvAbTitle.setText(stationName);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
