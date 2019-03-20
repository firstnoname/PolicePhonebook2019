package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterStationSubList;
import com.zealtech.policephonebook2019.Model.MockStation;

import java.util.ArrayList;
import java.util.List;

public class StationSubListActivity extends AppCompatActivity implements AdapterStationSubList.OnSubStationListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MockStation> mData = new ArrayList<>();
    List<MockStation> dataStation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_sub_list);

        recyclerView = findViewById(R.id.recycler_sub_station);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterStationSubList(this, initStation(), this);
        recyclerView.setAdapter(mAdapter);

    }

    private List<MockStation> initStation() {
        MockStation ms = new MockStation("ตำรวจภูธรภาค ๑-๘ (ภ.๑-๘)", "ส.ร.ฟ.ธนบุรี",
                "02-411-3107 ต่อ 123", "02-411-3107", "264/20 ถ.รถไฟ แขวงซิริราช เขตบางกอกน้อย กรุงเทพ 10700","123" ,"123");

        MockStation ms2 = new MockStation("ตำรวจภูธรภาค ๑-๘ (ภ.๑-๘)", "ส.ร.ฟ.ธนบุรี",
                "02-411-3107 ต่อ 123", "02-411-3107", "264/20 ถ.รถไฟ แขวงซิริราช เขตบางกอกน้อย กรุงเทพ 10700","123" ,"123");



        dataStation.add(ms);

        for (int i = 0; i < 6; i++) {
            dataStation.add(ms);
            dataStation.add(ms2);
        }

        return dataStation;
    }

    @Override
    public void onSubStationClick(int position) {
        Log.d("recycler", "onClick " + position);
        Intent intent = new Intent(this, StationDetailActivity.class);
        intent.putExtra("some_object", "something else");
        startActivity(intent);
    }
}
