package com.zealtech.policephonebook2019.Fragments;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zealtech.policephonebook2019.Activities.StationSubListActivity;
import com.zealtech.policephonebook2019.Adapters.AdapterMapList;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
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
public class MapListFragment extends Fragment {

    private static final String TAG = "MapListFragment";

    ArrayList<Department> mDepartmentList = new ArrayList<>();

    Api api = AppUtils.getApiService();

    public MapListFragment() {
        // Required empty public constructor
    }

    public static MapListFragment newInstance() {
        MapListFragment mapListFragment = new MapListFragment();

        return mapListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*int[] resId = {R.drawable.policestation_ic, R.drawable.policestation_ic, R.drawable.policestation_ic};
        String[] list = {"ตำรวจภูธรภาค ๑-๘(ภใ๑-๘)", "ตำรวจภูธรภาค ๑-๘(ภใ๑-๘)", "ตำรวจภูธรภาค ๑-๘(ภใ๑-๘)"};
        String[] desc = {"นครบาล, ทั่วไป", "นครบาล, ทั่วไป", "นครบาล, ทั่วไป"};

        final AdapterMapList adapterMapList = new AdapterMapList(getActivity(), list, desc, resId);

        final ListView listView = getActivity().findViewById(R.id.lvStation);
        listView.setAdapter(adapterMapList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentSubStation = new Intent(getActivity(), StationSubListActivity.class);
                startActivity(intentSubStation);
            }
        });*/

        Log.d(TAG, "loadDepartment method called");


        Call<ResponseDepartment> call = api.getDepartment();
        call.enqueue(new Callback<ResponseDepartment>() {
            @Override
            public void onResponse(Call<ResponseDepartment> call, Response<ResponseDepartment> response) {
                /*Log.d(TAG, "Inside call method");
                Log.d(TAG, response.message());*/
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mDepartmentList.addAll(response.body().getData().getContent());
//                            Log.d(TAG, mDepartmentList.get(0).getDepartmentName());
                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
//                            Log.d("TAG", response.errorBody().string());
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")){
//                            Log.d("TAG", response.errorBody().string());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDepartment> call, Throwable t) {
                Log.d(TAG, String.valueOf(t));
                Log.d(TAG, String.valueOf(call));
            }
        });
    }

}
