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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Log.d(TAG, "loadDepartment method called");

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
//                            Log.d(TAG, String.valueOf(mDepartmentList.get(0).getFlagTail()));
                            setAdapter(view, mDepartmentList);
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

    private void setAdapter(View view, ArrayList<Department> mDepartmentList) {
        Log.d(TAG, String.valueOf(mDepartmentList.size()));

        RecyclerView recyclerView = view.findViewById(R.id.lvStation);
        AdapterMapList adapter = new AdapterMapList(getActivity(), mDepartmentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
