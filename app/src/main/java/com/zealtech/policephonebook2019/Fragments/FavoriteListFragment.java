package com.zealtech.policephonebook2019.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.policephonebook2019.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteListFragment extends Fragment {


    public FavoriteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        return view;
    }

}
