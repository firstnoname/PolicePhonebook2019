package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zealtech.policephonebook2019.R;
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Adapters.AdapterStationSubList;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationSubListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "StationSubListActivity";

    Api api = AppUtils.getApiService();
    ArrayList<Department> mDepartmentList = new ArrayList<>();
    Department parentDepartment = new Department();
    String departmentId = "";
    int level = 2;
    int checkLvl = 0;

    android.support.v7.widget.SearchView searchView;
    AdapterStationSubList adapter;

    private TextView tvAbTitle;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_sub_list);

        tvAbTitle = findViewById(R.id.tv_actionbar_back);
        imgBack = findViewById(R.id.img_back);
        searchView = findViewById(R.id.search_sub_station);
        searchView.setOnQueryTextListener(this);

        String object = getIntent().getExtras().getString("parentDepartment");
        parentDepartment = new Gson().fromJson(object, Department.class);
        parentDepartment.setFlagTail(false);
        mDepartmentList.add(parentDepartment);

        //Check level and departmentId to query.
        tvAbTitle.setText(getIntent().getExtras().getString("subTitle"));
        departmentId = getIntent().getExtras().getString("parentId");
        checkLvl = checkLvl + getIntent().getIntExtra("level", level);
        if (checkLvl == 3) {
            //level = level + getIntent().getExtras().getInt("level");
//            Log.d(TAG, "True : " + checkLvl + " : " + departmentId);
            level = checkLvl;
        } else if (checkLvl == 4) {
            level = checkLvl;
        }

        callApiGetDepartment();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void callApiGetDepartment() {
        Call<ResponseDepartment> call = api.getDepartment(level, departmentId, 300);
        call.enqueue(new Callback<ResponseDepartment>() {
            @Override
            public void onResponse(Call<ResponseDepartment> call, Response<ResponseDepartment> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mDepartmentList.addAll(response.body().getData().getContent());
//                            Log.d(TAG, String.valueOf(mDepartmentList.get(0).getFlagTail()));
                            setAdapter(mDepartmentList);
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, response.errorBody().string());
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")){
                            Log.d(TAG, response.errorBody().string());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDepartment> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });
    }

    private void setAdapter(ArrayList<Department> Data) {

        this.mDepartmentList = Data;
        RecyclerView recyclerView = findViewById(R.id.recycler_sub_station);
        adapter = new AdapterStationSubList(this, mDepartmentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Department> newList = new ArrayList<>();

        for (int i = 0; i < mDepartmentList.size(); i++) {
            if (mDepartmentList.get(i).getDepartmentName().contains(userInput)) {
                newList.add(mDepartmentList.get(i));
            }
            if (mDepartmentList.get(i).getTag() != null) {
                for (int tagSize = 0; tagSize < mDepartmentList.get(i).getTag().size(); tagSize++) {
                    if (mDepartmentList.get(i).getTag().get(tagSize).contains(userInput)) {
                        newList.add(mDepartmentList.get(i));
                    }
                }
            }
        }

        adapter.updateSubStation(newList);

        return true;
    }
}
