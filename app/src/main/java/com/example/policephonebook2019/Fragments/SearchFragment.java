package com.example.policephonebook2019.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.policephonebook2019.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ArrayList<String> mProvince = new ArrayList<>();

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

        //createProvinceData();

        String[] mProvince = getResources().getStringArray(R.array.province);
        Spinner spinner = v.findViewById(R.id.spnProvince);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, mProvince);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        return v;
    }

    private void createProvinceData() {
        mProvince.add("Hello");
        mProvince.add("Hi");
        mProvince.add("Pikachu");
    }

}
