package com.zealtech.policephonebook2019.Adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zealtech.policephonebook2019.Fragments.FavoriteListFragment;
import com.zealtech.policephonebook2019.Fragments.FavoriteRecentFragment;

public class PagerAdapterFavorite extends FragmentPagerAdapter {

    private String token, history;
    public PagerAdapterFavorite(FragmentManager fm, String token, String history) {
        super(fm);
        this.token = token;
        this.history = history;
    }


    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            //Recent
            FavoriteRecentFragment recentFragment = new FavoriteRecentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("message", history);
            recentFragment.setArguments(bundle);
            return recentFragment;
        } else {
            //Favorite
            FavoriteListFragment favoriteListFragment = new FavoriteListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("message", token);
            favoriteListFragment.setArguments(bundle);
            return favoriteListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "รายการค้นหาล่าสุด";
            case 1:
                return "รายการโปรด";
            default:
                throw new Resources.NotFoundException();
        }
    }

//    public void AddFragment(Fragment fragment, String Title) {
//        fragmentList.add(fragment);
//        FragmentListTitles.add(Title);
//    }
}
