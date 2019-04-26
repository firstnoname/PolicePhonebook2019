package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterDepartmentSearchFilter;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDepartmentActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Api api = AppUtils.getApiService();

    private int level = 1;
    private String provinceId = "";
    private String departmentId = "";

    private ArrayList<Department> mDepartmentList = new ArrayList<>();
    AdapterDepartmentSearchFilter mAdapterDepartment;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_department);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        //get data
        level = getIntent().getIntExtra("level", 1);
        departmentId = getIntent().getStringExtra("parentId");
        if (departmentId == null) {
            departmentId = "0";
        }
        provinceId = getIntent().getStringExtra("provinceId");
        if (provinceId == "0") {
            provinceId = "";
        }

        Call<ResponseDepartment> call = api.getDepartment(level, departmentId, provinceId);
        call.enqueue(new Callback<ResponseDepartment>() {
            @Override
            public void onResponse(Call<ResponseDepartment> call, Response<ResponseDepartment> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
//                            mDepartmentList.add(0, (Department) new Department().createTotalItem());
                            Department defaultDepartment = new Department();
                            defaultDepartment.setDepartmentId(Integer.parseInt(departmentId));
                            defaultDepartment.setDepartmentName("ทั้งหมด");
                            mDepartmentList.add(0, defaultDepartment);
                            mDepartmentList.addAll(response.body().getData().getContent());
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

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDepartment> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    private void setAdapter(ArrayList<Department> mDepartmentList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_filter);
        mAdapterDepartment = new AdapterDepartmentSearchFilter(this, mDepartmentList);
        recyclerView.setAdapter(mAdapterDepartment);
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
            if (mDepartmentList.get(i).getName().contains(userInput)) {
                newList.add(mDepartmentList.get(i));
            }
        }

        mAdapterDepartment.updateList(newList);

        return true;
    }
}
