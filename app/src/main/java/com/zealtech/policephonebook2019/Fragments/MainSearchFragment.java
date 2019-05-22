package com.zealtech.policephonebook2019.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.SearchResultActivity;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceMasterData;
import com.zealtech.policephonebook2019.Util.AppUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainSearchFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainSearchFragment";

    private EditText textKeyword;
    private CardView cvName, cvLastname, cvRank, cvPosition, cvDepartment, cvPhoneNumber;
    private ImageView imgCheckName, imgCheckLastname, imgCheckRank, imgCheckPosition,
            imgCheckDepartment, imgCheckPhoneNumber;

    private Button btnSearch;

    private Api api = AppUtils.getApiService();

    private Boolean isNameChecked = false;
    private Boolean isLastnameChecked = false;
    private Boolean isRankChecked = false;
    private Boolean isPositionChecked = false;
    private Boolean isDepartmentChecked = false;
    private Boolean isPhoneNumberChecked = false;

    private String keyWord;

    public MainSearchFragment() {
        // Required empty public constructor
    }

    public static MainSearchFragment newInstance() {
        MainSearchFragment mainSearchFragment = new MainSearchFragment();

        return mainSearchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_search, container, false);

        textKeyword = view.findViewById(R.id.search_view);
        cvName = view.findViewById(R.id.checkbox_name);
        cvLastname = view.findViewById(R.id.checkbox_lastname);
        cvRank = view.findViewById(R.id.checkbox_rank);
        cvPosition = view.findViewById(R.id.checkbox_position);
        cvDepartment = view.findViewById(R.id.checkbox_department);
        cvPhoneNumber = view.findViewById(R.id.checkbox_phone_number);
        btnSearch = view.findViewById(R.id.btn_search);

        imgCheckName = view.findViewById(R.id.img_check_name);
        imgCheckLastname = view.findViewById(R.id.img_check_lastname);
        imgCheckRank = view.findViewById(R.id.img_check_rank);
        imgCheckPosition = view.findViewById(R.id.img_check_position);
        imgCheckDepartment = view.findViewById(R.id.img_check_department);
        imgCheckPhoneNumber = view.findViewById(R.id.img_check_phone);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvName.setOnClickListener(this);
        cvLastname.setOnClickListener(this);
        cvRank.setOnClickListener(this);
        cvPosition.setOnClickListener(this);
        cvDepartment.setOnClickListener(this);
        cvPhoneNumber.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkbox_name:
                if (isNameChecked == false) {
                    imgCheckName.setImageResource(R.mipmap.checked);
                    isNameChecked = true;
                } else {
                    imgCheckName.setImageResource(R.mipmap.check);
                    isNameChecked = false;
                }
                break;
            case R.id.checkbox_lastname:
                if (isLastnameChecked == false) {
                    imgCheckLastname.setImageResource(R.mipmap.checked);
                    isLastnameChecked = true;
                } else {
                    imgCheckLastname.setImageResource(R.mipmap.check);
                    isLastnameChecked = false;
                }
                break;
            case R.id.checkbox_rank:
                if (isRankChecked == false) {
                    imgCheckRank.setImageResource(R.mipmap.checked);
                    isRankChecked = true;
                } else {
                    imgCheckRank.setImageResource(R.mipmap.check);
                    isRankChecked = false;
                }
                break;
            case R.id.checkbox_position:
                if (isPositionChecked == false) {
                    imgCheckPosition.setImageResource(R.mipmap.checked);
                    isPositionChecked = true;
                } else {
                    imgCheckPosition.setImageResource(R.mipmap.check);
                    isPositionChecked = false;
                }
                break;
            case R.id.checkbox_department:
                if (isDepartmentChecked == false) {
                    imgCheckDepartment.setImageResource(R.mipmap.checked);
                    isDepartmentChecked = true;
                } else {
                    imgCheckDepartment.setImageResource(R.mipmap.check);
                    isDepartmentChecked = false;
                }
                break;
            case R.id.checkbox_phone_number:
                if (isPhoneNumberChecked == false) {
                    imgCheckPhoneNumber.setImageResource(R.mipmap.checked);
                    isPhoneNumberChecked = true;
                } else {
                    imgCheckPhoneNumber.setImageResource(R.mipmap.check);
                    isPhoneNumberChecked = false;
                }
                break;
            case R.id.btn_search:
                keyWord = textKeyword.getText().toString().trim();

                Intent i = new Intent(getActivity(), SearchResultActivity.class);
                i.putExtra("isNameChecked", isNameChecked);
                i.putExtra("isLastnameChecked", isLastnameChecked);
                i.putExtra("isRankChecked", isRankChecked);
                i.putExtra("isPositionChecked", isPositionChecked);
                i.putExtra("isDepartmentChecked", isDepartmentChecked);
                i.putExtra("isPhoneNumberChecked", isPhoneNumberChecked);
                i.putExtra("keyWord", keyWord);
                startActivity(i);
        }
    }
}
