package com.zealtech.policephonebook2019.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zealtech.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SearchResultActivity";

    private Api api = AppUtils.getApiService();

    private TextView tvListSize, titleBar;
    private ImageView btnBack;
    private Button btnSequence, btnFirstName, btnCreateDate, btnPosition;

    private Boolean isNameChecked = false;
    private Boolean isLastnameChecked = false;
    private Boolean isRankChecked = false;
    private Boolean isPositionChecked = false;
    private Boolean isDepartmentChecked = false;
    private Boolean isPhoneNumberChecked = false;

    private int positionSequence = 3;
    private int firstName = 4;
    private int createDate = 5;
    private int department = 1;
    private int rankSequence = 2;

    private String keyWord;
    private int page = 0;
    private int sizeContents = 100;

    private boolean isLoading = false;

    //Adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Police> mPolice;
    private ArrayList<Rank> ranks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        tvListSize = findViewById(R.id.tv_list_size);
        titleBar = findViewById(R.id.tv_actionbar_back);
        btnBack = findViewById(R.id.img_back);
        btnSequence = findViewById(R.id.btn_sequence);
        btnFirstName = findViewById(R.id.btn_alphabet);
        btnCreateDate = findViewById(R.id.btn_date);
        btnPosition = findViewById(R.id.btn_position);

        btnSequence.setOnClickListener(this);
        btnPosition.setOnClickListener(this);
        btnFirstName.setOnClickListener(this);
        btnCreateDate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        titleBar.setText("ผลการค้นหา");

        recyclerView = findViewById(R.id.recycler_phone_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        keyWord = getIntent().getStringExtra("keyWord");
        isNameChecked = getIntent().getExtras().getBoolean("isNameChecked");
        isLastnameChecked = getIntent().getExtras().getBoolean("isLastnameChecked");
        isRankChecked = getIntent().getExtras().getBoolean("isRankChecked");
        isPositionChecked = getIntent().getExtras().getBoolean("isPositionChecked");
        isDepartmentChecked = getIntent().getExtras().getBoolean("isDepartmentChecked");
        isPhoneNumberChecked = getIntent().getExtras().getBoolean("isPhoneNumberChecked");

        refreshList(rankSequence);

//        initScrollListener();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sequence:
                refreshList(rankSequence);
                btnSequence.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                btnPosition.setTextColor(getResources().getColor(R.color.fontGrey));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontGrey));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontGrey));
                break;
            case R.id.btn_alphabet:
                refreshList(firstName);
                btnSequence.setTextColor(getResources().getColor(R.color.fontGrey));
                btnPosition.setTextColor(getResources().getColor(R.color.fontGrey));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontGrey));
                break;
            case R.id.btn_date:
                refreshList(createDate);
                btnSequence.setTextColor(getResources().getColor(R.color.fontGrey));
                btnPosition.setTextColor(getResources().getColor(R.color.fontGrey));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontGrey));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                break;
            case R.id.btn_position:
                refreshList(positionSequence);
                btnSequence.setTextColor(getResources().getColor(R.color.fontGrey));
                btnPosition.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontGrey));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontGrey));
                break;
            case R.id.tv_actionbar_back:
                finish();
                break;
        }
    }

    private void refreshList(int sort) {
        Call<ResponsePoliceList> call = api.getPoliceListFilter("","", true,
                keyWord, isDepartmentChecked, isNameChecked, isLastnameChecked, isPhoneNumberChecked,
                isPositionChecked, isRankChecked, page,2, sizeContents, sort);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            String size = response.body().getData().getTotalElements();
                            double amount = Double.parseDouble(size);
                            DecimalFormat format = new DecimalFormat("#,###");
                            tvListSize.setText(format.format(amount) + " รายการ");
                            mPolice = new ArrayList<>();
                            mPolice.addAll(response.body().getData().getContent());
                            checkColor();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePoliceList> call, Throwable t) {
                Log.d(TAG, String.valueOf(t));
            }
        });
    }

    private void checkColor() {

        Call<ResponseRank> call = api.getRankMasterData("");
        call.enqueue(new Callback<ResponseRank>() {
            @Override
            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            ranks = new ArrayList<>();
                            ranks.addAll(response.body().getData());
                            for (int x = 0; x < mPolice.size(); x++) {
                                for (int i = 0; i < ranks.size(); i++) {
                                    if (mPolice.get(x).getRankId() == ranks.get(i).getRankId()) {
                                        mPolice.get(x).setColor(ranks.get(i).getColor());
                                    }
                                }
                            }
                            if (sizeContents == 100) {
                                setAdapter(mPolice);
                            } else {
                                updateAdapter(mPolice);
                            }

//                                    AdapterPhoneList.this.notifyDataSetChanged();
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

    private void updateAdapter(ArrayList<Police> mPolice) {
        mAdapter.notifyDataSetChanged();
    }

    private void setAdapter(ArrayList<Police> mPolice) {
        mAdapter = new AdapterPhoneListFilter(getApplicationContext(), mPolice);
        recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mPolice.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        sizeContents += sizeContents;

        Call<ResponsePoliceList> call = api.getPoliceListFilter("","", true,
                keyWord, isDepartmentChecked, isNameChecked, isLastnameChecked, isPhoneNumberChecked,
                isPositionChecked, isRankChecked, page, 2, sizeContents, 4);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            String size = response.body().getData().getTotalElements();
                            double amount = Double.parseDouble(size);
                            DecimalFormat format = new DecimalFormat("#,###");
                            tvListSize.setText(format.format(amount) + " รายการ");
                            mPolice = new ArrayList<>();
                            mPolice.addAll(response.body().getData().getContent());
                            checkColor();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePoliceList> call, Throwable t) {
                Log.d(TAG, String.valueOf(t));
            }
        });

//        mAdapter.notifyDataSetChanged();
        isLoading = false;
    }

}
