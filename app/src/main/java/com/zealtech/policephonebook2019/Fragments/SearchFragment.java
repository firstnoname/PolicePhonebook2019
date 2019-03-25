package com.zealtech.policephonebook2019.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.FilterActivity;
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

    private static final String TAG = "SearchFragment";

    String tagFilter = "";
    String tagValue = "";
    int level = 1;
    String departmentId = "";

    CardView cvProvince, cvRank, cvPosition;
    TextView tvProvince, tvDepartment, tvRank, tvPosition;

    ArrayList<Province> apiProvince = new ArrayList<>();
    ArrayList<Department> apiDepartment = new ArrayList<>();

    ArrayList<String> mProvince = new ArrayList<>();

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
        Call<ResponseDepartment> call = api.getDepartment(level, departmentId);
        call.enqueue(new Callback<ResponseDepartment>() {
            @Override
            public void onResponse(Call<ResponseDepartment> call, Response<ResponseDepartment> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
//                            apiDepartment.addAll(response.body().getData().getContent());
//                            Log.d("response-department", );

                            Log.d(TAG, String.valueOf(apiDepartment.size()));
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
                Log.d(TAG, String.valueOf(t));
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

        tvProvince = view.findViewById(R.id.tvProvince);
        tvDepartment = view.findViewById(R.id.tvDepartment);
        tvPosition = view.findViewById(R.id.tvPosition);
        tvRank = view.findViewById(R.id.tvRank);

        cvProvince = view.findViewById(R.id.cardViewProvince);
        cvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iProvince = new Intent(getActivity(), FilterActivity.class);
                iProvince.putExtra("tag", "province");
//                startActivity(iProvince);
                startActivityForResult(iProvince, 1);
            }
        });

        cvRank = view.findViewById(R.id.cardViewRank);
        cvRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRank = new Intent(getActivity(), FilterActivity.class);
                iRank.putExtra("tag", "rank");
                startActivity(iRank);
            }
        });

        cvPosition = view.findViewById(R.id.cardViewPosition);
        cvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPosition = new Intent(getActivity(), FilterActivity.class);
                iPosition.putExtra("tag", "position");
                startActivity(iPosition);
            }
        });

        //If this activity has been call by adapter.
        tagFilter = getActivity().getIntent().getStringExtra("tagFilter");
        tagValue = getActivity().getIntent().getStringExtra("valueFilter");
        if (tagFilter == "province") {
            tvProvince.setText(tagValue);
        } else {
            tvPosition.setText(tagValue);
        }
    }
}
