package com.zealtech.policephonebook2019.Adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.zealtech.policephonebook2019.Fragments.StationDetailFragment;
import com.zealtech.policephonebook2019.Fragments.StationStaffFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitles = new ArrayList<>();
    private String departmentId;

    private StationDetailFragment stationDetailFragment;
    private StationStaffFragment stationStaffFragment;

    public PagerAdapter(FragmentManager fm, String departmentId) {
        super(fm);

        this.departmentId = departmentId;

    }

    @Override
    public Fragment getItem(int i) {

        if (i == 0) {
            //StationDetail
            stationDetailFragment = new StationDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("message", departmentId);
            stationDetailFragment.setArguments(bundle);
            return stationDetailFragment;
        } else {
            //StationStaff
            stationStaffFragment = new StationStaffFragment();
            Bundle bundle = new Bundle();
            bundle.putString("message", departmentId);
            stationStaffFragment.setArguments(bundle);
            return stationStaffFragment;
        }
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String Title) {
        fragmentList.add(fragment);
        FragmentListTitles.add(Title);
    }

}
