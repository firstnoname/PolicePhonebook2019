package com.example.policephonebook2019.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.policephonebook2019.Fragments.MapListFragment;
import com.example.policephonebook2019.Fragments.PhoneListFragment;
import com.example.policephonebook2019.Fragments.SearchFragment;
import com.example.policephonebook2019.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Activity activity;
    Context context;

    public static FragmentManager fragmentManager;

    PhoneListFragment phoneListFragment;
    SearchFragment searchFragment;
    MapListFragment mapListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activity = MainActivity.this;
        context = MainActivity.this;
    }


    @OnClick(R.id.imgNoti)
    public void onImgNotiClicked() {
        Toast.makeText(this, "Noti has clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imgUser)
    public void onImgUserClicked() {
        Toast.makeText(this, "User login has clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.container)
    public void onContainerClicked() {
    }

    @OnClick(R.id.btn_menu_phone_list)
    public void onBtnMenuPhoneListClicked() {

        phoneListFragment = PhoneListFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, phoneListFragment, "PhoneListFragment");
        transaction.commit();

        setMenuIcon("phoneList");
    }

    @OnClick(R.id.btn_menu_search)
    public void onBtnMenuSearchClicked() {
        searchFragment = SearchFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, searchFragment, "SearchFragment");
        transaction.commit();
    }

    @OnClick(R.id.btn_menu_map)
    public void onBtnMenuMapClicked() {
        mapListFragment = MapListFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mapListFragment, "MapListFragment");
        transaction.commit();
    }

    @OnClick(R.id.btn_menu_favorite)
    public void onBtnMenuFavoriteClicked() {
    }

    @OnClick(R.id.btn_menu_contact_us)
    public void onBtnMenuContactUsClicked() {
    }

    private void setMenuIcon(String frag) {
        //Set icon color when was clicked

    }

}
