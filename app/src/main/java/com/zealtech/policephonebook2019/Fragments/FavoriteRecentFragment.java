package com.zealtech.policephonebook2019.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Model.PhoneNumber;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Realm.PoliceHistory;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;
import com.zealtech.policephonebook2019.Model.WorkPhoneNumber;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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


        Realm.init(getActivity());
        RealmConfiguration config = new RealmConfiguration.Builder().name("sample1.realm")
                .schemaVersion(1).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);

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
                ArrayList<PhoneNumber> phoneNumber = new ArrayList<>();
                PhoneNumber phoneNumber1 = new PhoneNumber();
                phoneNumber1.setTel(policeHistories.get(i).getPhoneNumber());
                phoneNumber.add(phoneNumber1);
                mPolice.setPhoneNumber(phoneNumber);
                ArrayList<WorkPhoneNumber> workPhoneNumber = new ArrayList<>();
                WorkPhoneNumber workPhoneNumber1 = new WorkPhoneNumber();
                workPhoneNumber1.setTel(policeHistories.get(i).getWorkPhoneNumber());
                mPolice.setWorkPhoneNumber(workPhoneNumber);
                mPolice.setUpdateDate(policeHistories.get(i).getUpdateDate());
                mPolice.setId(policeHistories.get(i).getId());
                mPolice.setColor(policeHistories.get(i).getColor());
                mPolice.setUpdateDate(policeHistories.get(i).getUpdateDate());

                mPolices.add(mPolice);

            }

            setAdapter(mPolices);

        } else {
            tvHistory.setText(R.string.dont_have_data2);
        }


    }

    private void setAdapter(ArrayList<Police> content) {
        mAdapter = new AdapterPhoneListFilter(getActivity(), content);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }
}
