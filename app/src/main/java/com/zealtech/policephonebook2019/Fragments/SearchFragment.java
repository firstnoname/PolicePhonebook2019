package com.zealtech.policephonebook2019.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.FilterActivity;
import com.zealtech.policephonebook2019.Activities.FilterDepartmentActivity;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private static final String TAG = "SearchFragment";

    String tagFilter = "";
    String tagValue = "";
    int level = 1;
    String departmentId = "";
    String provinceId = "";
    String positionId = "";
    String rankId = "";

    Province selectProvince = null;
    Department selectDepartment = null;
    Rank selectRank = null;
    Position selectPosition = null;

    CardView cvProvince, cvRank, cvPosition, cvDepartment;
    TextView tvProvince, tvDepartment, tvRank, tvPosition, tvListSize;
    SearchView searchView;

    Api api = AppUtils.getApiService();

    //Adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Police> mPolice = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();

        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvProvince = view.findViewById(R.id.tvProvince);
        tvDepartment = view.findViewById(R.id.tvDepartment);
        tvPosition = view.findViewById(R.id.tv_position);
        tvRank = view.findViewById(R.id.tvRank);
        tvListSize = view.findViewById(R.id.tv_list_size);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        searchView = view.findViewById(R.id.search_view);

        cvProvince = view.findViewById(R.id.cardViewProvince);
        cvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iProvince = new Intent(getActivity(), FilterActivity.class);
                iProvince.putExtra("tag", "province");
                getActivity().startActivityForResult(iProvince, 1);
            }
        });

        cvRank = view.findViewById(R.id.cardViewRank);
        cvRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRank = new Intent(getActivity(), FilterActivity.class);
                iRank.putExtra("tag", "rank");
                getActivity().startActivityForResult(iRank, 1);
            }
        });

        cvPosition = view.findViewById(R.id.cardViewPosition1);
        cvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPosition = new Intent(getActivity(), FilterActivity.class);
                iPosition.putExtra("tag", "position");
                getActivity().startActivityForResult(iPosition, 1);
            }
        });

        cvDepartment = view.findViewById(R.id.cardViewDepartment);
        cvDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDepartment = new Intent(getActivity(), FilterActivity.class);
                iDepartment.putExtra("tag", "department");
                iDepartment.putExtra("provinceId", provinceId);
                getActivity().startActivityForResult(iDepartment, 2);
            }
        });

        //If this activity has been call by adapter.
//        tagFilter = getActivity().getIntent().getStringExtra("tagFilter");
//        tagValue = getActivity().getIntent().getStringExtra("valueFilter");
//        if (tagFilter == "province") {
//            tvProvince.setText(tagValue);
//        } else {
////            tvPosition.setText(tagValue);
//        }

        onRefreshView();
    }

    private void setAdapter(ArrayList<Police> dataSet) {
        tvListSize.setText(dataSet.size() + " รายการ");
        mAdapter = new AdapterPhoneListFilter(getActivity(), dataSet);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    public void setDropDownProvince(Province item) {
        selectProvince = item;
        tvProvince.setText(selectProvince.getProvinceName());

        if (selectProvince.getProvinceId() == "0") {
            provinceId = "";
        } else {
            provinceId = selectProvince.getProvinceId();
        }
        //Set department dropdown.

    }

    public void setDropDownDepartment(Department item) {
        selectDepartment = item;
        tvDepartment.setText(selectDepartment.getDepartmentName());
        if (selectDepartment.getDepartmentId() == 0) {
            departmentId = "";
        } else {
            departmentId = String.valueOf(selectDepartment.getDepartmentId());
        }
        onRefreshView();
    }

    public void setDropDownRank(Rank item) {
        selectRank = item;
        tvRank.setText(selectRank.getRankName());
        if (selectRank.getRankId() == 0) {
            rankId = "";
        } else {
            rankId = String.valueOf(selectRank.getRankId());
        }

        onRefreshView();
    }

    public void setDropDownPosition(Position item) {
        selectPosition = item;
        tvPosition.setText(selectPosition.getPositionName());
        if (selectPosition.getPositionId() == 0) {
            positionId = "";
        } else {
            positionId = String.valueOf(selectPosition.getPositionId());
        }

        onRefreshView();
    }

    private void onRefreshView() {

        Call<ResponsePoliceList> call = api.getPoliceList(departmentId, positionId, rankId);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            tvListSize.setText(response.body().getData().getContent().size() + "รายการ");
                            mPolice.addAll(response.body().getData().getContent());
                            setAdapter(mPolice);
                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("code").equals("no_user_found")) {
                            Log.d(TAG, String.valueOf(jObjError.get("code")));
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, String.valueOf(jObjError.get("code")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePoliceList> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });
    }


}
