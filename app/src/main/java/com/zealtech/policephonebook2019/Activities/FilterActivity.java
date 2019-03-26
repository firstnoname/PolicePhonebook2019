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
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.response.ResponseProvince;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = "FilterActivity";

    Api api = AppUtils.getApiService();

    private String tagFilter = "";

    SearchView searchView;
    AdapterFilterSearch adapter;

    //vars
    private ArrayList<Province> apiProvince = new ArrayList<>();

    private ArrayList<String> mRank = new ArrayList<>();
    private ArrayList<String> mPosition = new ArrayList<>();
    private ArrayList<String> mProvince = new ArrayList<>();

    private ArrayList<String> mTagSearch = new ArrayList<>();

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
            }
        }

    }

    private void initProvince() {

        Call<ResponseProvince> call = api.getProvince();
        call.enqueue(new Callback<ResponseProvince>() {
            @Override
            public void onResponse(Call<ResponseProvince> call, Response<ResponseProvince> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiProvince.addAll(response.body().getData());

                            for (int i = 0; i < apiProvince.size(); i++) {
                                mProvince.add(apiProvince.get(i).getProvinceName());
                                mTagSearch.addAll(mProvince);
                            }

                            initRecyclerView(mProvince);
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
        mPosition.add("Position 1");
        mPosition.add("Position 2");
        mPosition.add("Position 3");
        mPosition.add("Position 4");
        mTagSearch.addAll(mPosition);
        initRecyclerView(mPosition);
    }

    private void initRank() {
        mRank.add("Rank 1");
        mRank.add("Rank 2");
        mRank.add("Rank 3");
        mRank.add("Rank 4");
        mTagSearch.addAll(mRank);
        initRecyclerView(mRank);
    }

    private void initRecyclerView(ArrayList<String> dataSet) {
        ArrayList<String> data = dataSet;
        RecyclerView recyclerView = findViewById(R.id.recycler_filter);
        adapter = new AdapterFilterSearch(this, data, tagFilter);
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
        ArrayList<String> newList = new ArrayList<>();

//        Log.d(TAG, String.valueOf(mTagSearch.size()));

        for (String tag : mTagSearch) {
            if (tag.toLowerCase().contains(userInput)) {
                newList.addAll(newList);
            }
        }

        adapter.updateList(newList);

        return true;
    }
}
