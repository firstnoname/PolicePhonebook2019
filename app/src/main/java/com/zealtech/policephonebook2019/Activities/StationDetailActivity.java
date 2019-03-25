package com.zealtech.policephonebook2019.Activities;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.DepartmentRoot;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartmentRoot;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationDetailActivity extends AppCompatActivity{

    private static final String TAG = "StationDetailActivity";

    TextView tvStationPhone1, tvStationPhone2, tvStationAddress, btnOpenMap;

    private String departmentId = "";
    private ArrayList<DepartmentRoot> mDepartmentRoot = new ArrayList<>();
    private Double latitude = 18.7988609;
    private Double longitude = 99.0238646;

    Api api = AppUtils.getApiService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        tvStationPhone1 = findViewById(R.id.tv_statoin_phone1);
        tvStationPhone2 = findViewById(R.id.tv_station_phone2);
        tvStationAddress = findViewById(R.id.tv_station_address);
        btnOpenMap = findViewById(R.id.btn_station_open_map);

        departmentId = String.valueOf(getIntent().getIntExtra("departmentId", 0));

        Call<ResponseDepartmentRoot> call = api.getDepartmentRoot(departmentId);
        call.enqueue(new Callback<ResponseDepartmentRoot>() {
            @Override
            public void onResponse(Call<ResponseDepartmentRoot> call, Response<ResponseDepartmentRoot> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mDepartmentRoot.addAll(response.body().getData());
//                            Log.d(TAG, String.valueOf(mDepartmentRoot.get(0).getDepartmentName()));
                            setDataToLayout(mDepartmentRoot);
                        } else {
                            Toast.makeText(StationDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(StationDetailActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, "ไม่พบผู้ใช้งาน");
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, "ไม่พบผู้ใช้งาน");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, e.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDepartmentRoot> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();


            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void setDataToLayout(ArrayList<DepartmentRoot> dataDepartmentRoot) {
        this.mDepartmentRoot = dataDepartmentRoot;

        String fullAddress = mDepartmentRoot.get(0).getAddress() + " ถ." + mDepartmentRoot.get(0).getRoad() + " แขวง " + mDepartmentRoot.get(0).getPostcode();

        tvStationPhone1.setText(mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTel() + " ต่อ " + mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTelTo());
        tvStationPhone2.setText(mDepartmentRoot.get(0).getFaxes().get(0).getFaxNo());
        tvStationAddress.setText(fullAddress);
    }

}
