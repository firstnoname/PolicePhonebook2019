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
import android.widget.TextView;
import android.widget.Toast;

import com.zealtech.policephonebook2019.Adapters.AdapterFavoriteList;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.PoliceHistory;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteRecentFragment extends Fragment {

    private static final String TAG = "FavoriteRecentFragment";
    public static final String KEY_MESSAGE = "message";

    private String history = "";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<PoliceMasterData> apiPoliceRecent = new ArrayList<>();


    public FavoriteRecentFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_recent, container, false);

        history = getArguments().getString(KEY_MESSAGE);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvHistory = view.findViewById(R.id.tvHistory);
        recyclerView = view.findViewById(R.id.favorite_recent_list);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<PoliceHistory> policeHistories = realm.where(PoliceHistory.class).findAll();

        if (policeHistories.size() != 0) {
            tvHistory.setVisibility(View.GONE);
            Police mPolice;
            ArrayList<Police> mPolices = new ArrayList<>();

            for (int i = 0; i < policeHistories.size(); i++) {
                mPolice = new Police();
                mPolice.setImageProfile(policeHistories.get(i).getImageProfile());
                mPolice.setRankName(policeHistories.get(i).getRankName());
                mPolice.setFirstName(policeHistories.get(i).getFirstName());
                mPolice.setLastName(policeHistories.get(i).getLastName());
                mPolice.setPositionName(policeHistories.get(i).getPositionName());
                mPolice.setDepartmentName(policeHistories.get(i).getDepartmentName());
                mPolice.setRankId(policeHistories.get(i).getRankId());
                mPolice.setPhoneNumber(policeHistories.get(i).getPhoneNumber());
                mPolice.setWorkPhoneNumber(policeHistories.get(i).getWorkPhoneNumber());
                mPolice.setUpdateDate(policeHistories.get(i).getUpdateDate());
                mPolice.setId(policeHistories.get(i).getId());

                mPolices.add(mPolice);

            }

            setAdapter(mPolices);

        } else {
            tvHistory.setText(R.string.dont_have_data);
        }


    }

    private void setAdapter(ArrayList<Police> content) {
        mAdapter = new AdapterPhoneListFilter(getActivity(), content);
        recyclerView.setAdapter(mAdapter);
    }

}
