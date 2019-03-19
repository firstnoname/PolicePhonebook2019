package com.zealtech.policephonebook2019.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Model.response.ResponseProvince;
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
public class SearchFragment extends Fragment {

    ArrayList<Province> apiProvince = new ArrayList<>();
    ArrayList<Department> apiDepartment = new ArrayList<>();

    List<String> mProvince = new ArrayList<>();

    Api api = AppUtils.getApiService();

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

        fetchProvince();
        fetchDepartment();

        return v;
    }

    private void fetchDepartment() {
        Call<ResponseDepartment> call = api.getDepartment();
        call.enqueue(new Callback<ResponseDepartment>() {
            @Override
            public void onResponse(Call<ResponseDepartment> call, Response<ResponseDepartment> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
//                            apiDepartment.setContent(response.body().getData());
//                            Log.d("response-department", );
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
            public void onFailure(Call<ResponseDepartment> call, Throwable t) {
                Log.d("response-department", String.valueOf(t));
            }
        });
    }

    private void fetchProvince() {
        Call<ResponseProvince> call = api.getProvince();
        call.enqueue(new Callback<ResponseProvince>() {
            @Override
            public void onResponse(Call<ResponseProvince> call, Response<ResponseProvince> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            apiProvince.addAll(response.body().getData());
//                            Log.d("response", String.valueOf(response.body().getData()));
//                            Log.d("response", String.valueOf(response));
//                            Log.d("response", String.valueOf(apiProvince.size()));
                            for (int i = 0; i < apiProvince.size(); i++) {
//                                Log.d("response", apiProvince.get(i).getProvinceName());
                                mProvince.add(apiProvince.get(i).getProvinceName());
                            }
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
            public void onFailure(Call<ResponseProvince> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        String[] mProvince = getResources().getStringArray(R.array.province);
        String[] mDeparture = getResources().getStringArray(R.array.departure);
        String[] mRank = getResources().getStringArray(R.array.rank);
        String[] mPosition = getResources().getStringArray(R.array.position);

//        Log.d("test", String.valueOf(test.size()));
       Spinner spinner = view.findViewById(R.id.spnProvince);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, test);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);*/
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text, mProvince);
        provinceAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(provinceAdapter);

        Spinner spinnerDep = view.findViewById(R.id.spnDeparture);
        ArrayAdapter<String> adapterDep = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, mDeparture);
        adapterDep.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDep.setAdapter(adapterDep);

        Spinner spinnerRank = view.findViewById(R.id.spnRank);
        ArrayAdapter<String> adapterRank = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, mRank);
        adapterRank.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerRank.setAdapter(adapterRank);

        Spinner spinnerPosition = view.findViewById(R.id.spnPosition);
        ArrayAdapter<String> adapterPosition = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, mPosition);
        adapterPosition.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPosition.setAdapter(adapterPosition);


    }
}
