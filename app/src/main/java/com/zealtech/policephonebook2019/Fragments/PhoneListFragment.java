package com.zealtech.policephonebook2019.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneList;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceMasterData;
import com.zealtech.policephonebook2019.Model.response.ResponsePosition;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneListFragment extends Fragment implements SearchView.OnQueryTextListener{

    private static final String TAG = "PhoneListFragment";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Api api = AppUtils.getApiService();

    //Vars
    public ArrayList<PoliceMasterData> apiPoliceMasterData;
    public ArrayList<Rank> ranks = new ArrayList<>();
    public ArrayList<Position> positions = new ArrayList<>();

    SearchView actionSearch;

    public PhoneListFragment() {
        // Required empty public constructor
    }

    public static PhoneListFragment newInstance() {
        PhoneListFragment phoneListFragment = new PhoneListFragment();

        return phoneListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_phone_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_phone_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        actionSearch = view.findViewById(R.id.search_view);

        actionSearch.setOnQueryTextListener(this);

        callApi();

    }// end onCreate.

    private void callApi() {
        //Fetch data from api.
        Call<ResponsePoliceMasterData> call = api.getPoliceMasterData();
        call.enqueue(new Callback<ResponsePoliceMasterData>() {
            @Override
            public void onResponse(Call<ResponsePoliceMasterData> call, Response<ResponsePoliceMasterData> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiPoliceMasterData = new ArrayList<>();
                            apiPoliceMasterData.addAll(response.body().getData());
                            //Police master data come with no color string. so we have to check color before set data to adapter.
                            checkColor();
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
            public void onFailure(Call<ResponsePoliceMasterData> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        }); // end retrofit call.
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
                            for (int x = 0; x < apiPoliceMasterData.size(); x++) {
                                for (int i = 0; i < ranks.size(); i++) {
                                    if (apiPoliceMasterData.get(x).getRankId() == ranks.get(i).getRankId()) {
                                        apiPoliceMasterData.get(x).setColor(ranks.get(i).getColor());
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
                            for (int x = 0; x < apiPoliceMasterData.size(); x++) {
                                for (int i = 0; i < positions.size(); i++) {
                                    if (apiPoliceMasterData.get(x).getPositionId() == positions.get(i).getPositionId()) {
                                        apiPoliceMasterData.get(x).setTag(positions.get(i).getTag());
                                    }
                                }
                            }
                            setAdapter(apiPoliceMasterData);
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


    private void setAdapter(ArrayList<PoliceMasterData> dataSet) {
        this.apiPoliceMasterData = dataSet;
//        Log.d(TAG, String.valueOf(apiPoliceMasterData.size()));

        mAdapter = new AdapterPhoneList(getActivity(), apiPoliceMasterData, true);

        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        ArrayList<PoliceMasterData> newList = new ArrayList<>();

        for (int i = 0; i < apiPoliceMasterData.size(); i++) {
            if (apiPoliceMasterData.get(i).getFirstName().contains(userInput)) {
                newList.add(apiPoliceMasterData.get(i));
            }

            if (apiPoliceMasterData.get(i).getLastName().contains(userInput)) {
                newList.add(apiPoliceMasterData.get(i));
            }

            if (apiPoliceMasterData.get(i).getPositionName().contains(userInput)) {
                newList.add(apiPoliceMasterData.get(i));
            }

            if (apiPoliceMasterData.get(i).getDepartmentName().contains(userInput)) {
                newList.add(apiPoliceMasterData.get(i));
            }

            if (apiPoliceMasterData.get(i).getRankName().contains(userInput)) {
                newList.add(apiPoliceMasterData.get(i));
            }

            if (apiPoliceMasterData.get(i).getWorkPhoneNumber() != null) {
                if (apiPoliceMasterData.get(i).getWorkPhoneNumber().contains(userInput)) {
                    newList.add(apiPoliceMasterData.get(i));
                }
            }

            if (apiPoliceMasterData.get(i).getPhoneNumber().contains(userInput)) {
                newList.add(apiPoliceMasterData.get(i));
            }

        }

        mAdapter = new AdapterPhoneList(getActivity(), newList);
        recyclerView.setAdapter(mAdapter);

        if (userInput.equals("")) {
            callApi();
        }

        return true;
    }
}
