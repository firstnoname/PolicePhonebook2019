package com.zealtech.policephonebook2019.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zealtech.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterStationStaff;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponsePosition;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationStaffFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StationStaffFragment";

    private TextView tvListSize;
    private Button btnSequence, btnFirstName, btnCreateDate, btnDepartment;

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
    private int sort = 2;

    private String keyWord;
    private int page = 0;
    private int sizeContents = 30;

    public static final String KEY_MESSAGE = "message";
    private String departmentId = "";
    private ArrayList<Police> mPoliceList = new ArrayList<>();
    private ArrayList<Rank> ranks = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();

    private Api api = AppUtils.getApiService();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public StationStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_station_staff, container, false);

        tvListSize = view.findViewById(R.id.tv_list_size);
        btnSequence = view.findViewById(R.id.btn_sequence);
        btnFirstName = view.findViewById(R.id.btn_alphabet);
        btnCreateDate = view.findViewById(R.id.btn_date);
        btnDepartment = view.findViewById(R.id.btn_department);

        btnSequence.setOnClickListener(this);
        btnDepartment.setOnClickListener(this);
        btnFirstName.setOnClickListener(this);
        btnCreateDate.setOnClickListener(this);

        departmentId = getArguments().getString(KEY_MESSAGE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshList(sort);

    }

    private void refreshList(int sort) {
        Call<ResponsePoliceList> call = api.getPoliceListFilter(departmentId,"",
                keyWord, isDepartmentChecked, isNameChecked, isLastnameChecked, isPhoneNumberChecked,
                isPositionChecked, isRankChecked, page, 2, sizeContents, sort);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            tvListSize.setText(response.body().getData().getTotalElements() + " รายการ");
                            mPoliceList = new ArrayList<>();
                            mPoliceList.addAll(response.body().getData().getContent());
                            checkColor();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
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
                            ranks.addAll(response.body().getData());
                            for (int x = 0; x < mPoliceList.size(); x++) {
                                for (int i = 0; i < ranks.size(); i++) {
                                    if (mPoliceList.get(x).getRankId() == ranks.get(i).getRankId()) {
                                        mPoliceList.get(x).setColor(ranks.get(i).getColor());
                                    }
                                }
                            }
                            checkPosition();
//                                    AdapterPhoneList.this.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRank> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    private void checkPosition() {
        Call<ResponsePosition> call = api.getPositionMasterData("");
        call.enqueue(new Callback<ResponsePosition>() {
            @Override
            public void onResponse(Call<ResponsePosition> call, Response<ResponsePosition> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            positions.addAll(response.body().getData());
                            for (int x = 0; x < mPoliceList.size(); x++) {
                                for (int i = 0; i < positions.size(); i++) {
                                    if (mPoliceList.get(x).getPositionId() == positions.get(i).getPositionId()) {
                                        mPoliceList.get(x).setTag(positions.get(i).getTag());
                                    }
                                }
                            }
                            setDataToLayout(mPoliceList);
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePosition> call, Throwable t) {

            }
        });
    }

    private void setDataToLayout(ArrayList<Police> mPoliceList) {
        this.mPoliceList = mPoliceList;
        Log.d(TAG, String.valueOf(mPoliceList.size()));
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_station_staff);
        adapter = new AdapterStationStaff(getActivity(), mPoliceList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sequence:
                refreshList(positionSequence);
                btnSequence.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                btnDepartment.setTextColor(getResources().getColor(R.color.fontGrey));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontGrey));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontGrey));
                break;
            case R.id.btn_alphabet:
                refreshList(firstName);
                btnSequence.setTextColor(getResources().getColor(R.color.fontGrey));
                btnDepartment.setTextColor(getResources().getColor(R.color.fontGrey));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontGrey));
                break;
            case R.id.btn_date:
                refreshList(createDate);
                btnSequence.setTextColor(getResources().getColor(R.color.fontGrey));
                btnDepartment.setTextColor(getResources().getColor(R.color.fontGrey));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontGrey));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                break;
            case R.id.btn_department:
                refreshList(department);
                btnSequence.setTextColor(getResources().getColor(R.color.fontGrey));
                btnDepartment.setTextColor(getResources().getColor(R.color.fontDeepBlue));
                btnFirstName.setTextColor(getResources().getColor(R.color.fontGrey));
                btnCreateDate.setTextColor(getResources().getColor(R.color.fontGrey));
                break;
        }
    }
}
