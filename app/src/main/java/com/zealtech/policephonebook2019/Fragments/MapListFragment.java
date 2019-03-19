package com.zealtech.policephonebook2019.Fragments;


import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MapListFragment extends Fragment {

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

        int[] resId = {R.drawable.policestation_ic, R.drawable.policestation_ic, R.drawable.policestation_ic};
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
        });
    }
}
