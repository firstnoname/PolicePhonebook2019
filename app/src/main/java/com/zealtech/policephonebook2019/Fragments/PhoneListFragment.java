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
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterFavoriteList;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.MockPolistInfo;
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
public class PhoneListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Api api = AppUtils.getApiService();

    public ArrayList<PoliceMasterData> apiPoliceMasterData = new ArrayList<>();

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


        //Fetch data from api.
        Call<ResponsePoliceMasterData> call = api.getPoliceMasterData();
        call.enqueue(new Callback<ResponsePoliceMasterData>() {
            @Override
            public void onResponse(Call<ResponsePoliceMasterData> call, Response<ResponsePoliceMasterData> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiPoliceMasterData.addAll(response.body().getData());
//                            Log.d("response-policemaster", String.valueOf(apiPoliceMasterData.size()));
                            /*for (int i = 0; i < apiPoliceMasterData.size(); i++) {
                                Log.d("response-police", apiPoliceMasterData.get(i).getFirstName());
                                for (int x = 0; x < apiPoliceMasterData.get(i).getTag().size(); x++) {
                                    Log.d("response-police-tag", apiPoliceMasterData.get(i).getTag().get(x));
                                }
                            }*/
                            mAdapter = new AdapterFavoriteList(getActivity(), apiPoliceMasterData);
                            recyclerView.setAdapter(mAdapter);
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

                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {

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

            }
        });

    }

    private List<MockPolistInfo> initPlayer() {
        MockPolistInfo m1 = new MockPolistInfo("พล.ต.ท.ยสเอก รัษาสุวรรณ", "ผกก. ผ่ายอำนวยการ 1", "กองบังคับการอำนวยการ สำนักงานตรวจคนเข้าเมือง", "1", "1");
        MockPolistInfo m2 = new MockPolistInfo("พล.ต.ท.ยสเอก รัษาสุวรรณ", "ผกก. ผ่ายอำนวยการ 1", "กองบังคับการอำนวยการ สำนักงานตรวจคนเข้าเมือง","", "1");
        MockPolistInfo m3 = new MockPolistInfo("พล.ต.ท.ยสเอก รัษาสุวรรณ", "ผกก. ผ่ายอำนวยการ 1", "กองบังคับการอำนวยการ สำนักงานตรวจคนเข้าเมือง","1", "");
        MockPolistInfo m4 = new MockPolistInfo("พล.ต.ท.ยสเอก รัษาสุวรรณ", "ผกก. ผ่ายอำนวยการ 1", "กองบังคับการอำนวยการ สำนักงานตรวจคนเข้าเมือง","", "");

        List<MockPolistInfo> dataSet = new ArrayList<>();

        dataSet.add(m1);
        for (int i = 0; i < 10; i++) {
            dataSet.add(m1);
            dataSet.add(m2);
            dataSet.add(m3);
            dataSet.add(m4);
        }

        return dataSet;
    }
}
