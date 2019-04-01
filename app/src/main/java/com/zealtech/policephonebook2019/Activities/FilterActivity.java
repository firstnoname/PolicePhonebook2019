package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterFilterSearch;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Model.response.ResponsePosition;
import com.zealtech.policephonebook2019.Model.response.ResponseProvince;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = "FilterActivity";

    Api api = AppUtils.getApiService();

    private String tagFilter = "";
    private String provinceId = "";
    private String rankId = "";
    private String positionId = "";

    SearchView searchView;
    AdapterFilterSearch adapter;

    //vars
    private ArrayList<BaseFilterItem> apiItemFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        searchView = findViewById(R.id.search_filter);
        searchView.setOnQueryTextListener(this);

        tagFilter = getIntent().getStringExtra("tag");

        if (tagFilter != null) {
            if (tagFilter.equals("rank")) {
                initRank();
            } else if (tagFilter.equals("position")) {
                initPosition();
            } else if (tagFilter.equals("province")) {
                initProvince();
            } else if (tagFilter.equals("department")) {
                initDepartment();
            }
        }
    }


    private void initDepartment() {
        Call<ResponseDepartment> call = api.getDepartment(1, "", getIntent().getStringExtra(provinceId));
        call.enqueue(new Callback<ResponseDepartment>() {
            @Override
            public void onResponse(Call<ResponseDepartment> call, Response<ResponseDepartment> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiItemFilter.addAll(response.body().getData().getContent());
                            initRecyclerView(apiItemFilter);
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

    private void initProvince() {
        Call<ResponseProvince> call = api.getProvince();
        call.enqueue(new Callback<ResponseProvince>() {
            @Override
            public void onResponse(Call<ResponseProvince> call, Response<ResponseProvince> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiItemFilter.addAll(response.body().getData());
                            initRecyclerView(apiItemFilter);
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
            public void onFailure(Call<ResponseProvince> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });

    }

    private void initPosition() {
        Call<ResponsePosition> call = api.getPositionMasterData(positionId);
        call.enqueue(new Callback<ResponsePosition>() {
            @Override
            public void onResponse(Call<ResponsePosition> call, Response<ResponsePosition> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiItemFilter.addAll(response.body().getData());
                            initRecyclerView(apiItemFilter);
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
            public void onFailure(Call<ResponsePosition> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    private void initRank() {
        Call<ResponseRank> call = api.getRankMasterData(rankId);
        call.enqueue(new Callback<ResponseRank>() {
            @Override
            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiItemFilter.addAll(response.body().getData());
                            initRecyclerView(apiItemFilter);
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
            public void onFailure(Call<ResponseRank> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    private  <T extends BaseFilterItem> void initRecyclerView(ArrayList<T> dataSet) {
        RecyclerView recyclerView = findViewById(R.id.recycler_filter);

        ArrayList<BaseFilterItem> baseFilterItems = new ArrayList<>();
        baseFilterItems.addAll(dataSet);

        adapter = new AdapterFilterSearch(this, baseFilterItems, tagFilter);

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
        ArrayList<BaseFilterItem> newList = new ArrayList<>();

        for (int i = 0; i < apiItemFilter.size(); i++) {
            if (apiItemFilter.get(i).getName().contains(userInput)) {
                newList.add(apiItemFilter.get(i));
            }
        }

        adapter.updateList(newList);

        return true;
    }
}
