package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.policephonebook2019.R;
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

public class StationSubListActivity extends AppCompatActivity {

    private static final String TAG = "StationSubListActivity";

    Api api = AppUtils.getApiService();
    ArrayList<Department> mDepartmentList = new ArrayList<>();
    String parentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_sub_list);

        parentId = getIntent().getExtras().getString("parentId");
        callApiGetDepartment();

    }

    private void callApiGetDepartment() {
        Call<ResponseDepartment> call = api.getDepartmentTail(parentId);
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
        AdapterStationSubList adapter = new AdapterStationSubList(this, mDepartmentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
