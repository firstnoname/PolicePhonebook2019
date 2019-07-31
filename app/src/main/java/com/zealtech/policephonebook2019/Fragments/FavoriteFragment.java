package com.zealtech.policephonebook2019.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zealtech.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.PagerAdapterFavorite;
import com.zealtech.policephonebook2019.Model.ProfileH;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapterFavorite adapter;
    TabItem tabRecent, tabFavoriteList;

    ProfileH mProfile = new ProfileH();
    private int subscription;
    private String token = "";
    private String history = "History";

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {

        FavoriteFragment favoriteFragment = new FavoriteFragment();

        return favoriteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabRecent = view.findViewById(R.id.tabRecent);
        viewPager = view.findViewById(R.id.viewPager);
        tabFavoriteList = view.findViewById(R.id.tabList);

        checkLogin();
        callFragment();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkLogin();
//
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void callFragment() {
            adapter = new PagerAdapterFavorite(getChildFragmentManager(), token, history);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
    }

    private void checkLogin() {
        SharedPreferences mPref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString("ProfileObject", "");
        subscription = mPref.getInt("Subscription", 0);
        mProfile = gson.fromJson(json, ProfileH.class);

        if (json.equals("")) {

        } else {
            token = mProfile.getToken();
        }
    }
}
