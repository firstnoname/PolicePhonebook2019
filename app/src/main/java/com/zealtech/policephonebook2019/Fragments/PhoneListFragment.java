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
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceMasterData;
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
    public ArrayList<PoliceMasterData> apiPoliceMasterData = new ArrayList<>();

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

                            apiPoliceMasterData.addAll(response.body().getData());
                            setAdapter(apiPoliceMasterData);

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


    private void setAdapter(ArrayList<PoliceMasterData> dataSet) {
        this.apiPoliceMasterData = dataSet;
        Log.d(TAG, String.valueOf(apiPoliceMasterData.size()));

        mAdapter = new AdapterPhoneList(getActivity(), apiPoliceMasterData);

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
        }


        mAdapter = new AdapterPhoneList(getActivity(), newList);
        recyclerView.setAdapter(mAdapter);

        if (userInput.equals("")) {
            callApi();
        }

        return true;
    }
}
