package com.zealtech.policephonebook2019.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Fragments.ContactUsFragment;
import com.zealtech.policephonebook2019.Fragments.FavoriteFragment;
import com.zealtech.policephonebook2019.Fragments.MapListFragment;
import com.zealtech.policephonebook2019.Fragments.PhoneListFragment;
import com.zealtech.policephonebook2019.Fragments.SearchFragment;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Activity activity;
    Context context;

    String current_frag = "";
    ProfileH mProfile = new ProfileH();
    private int subscription;

    PhoneListFragment phoneListFragment;
    SearchFragment searchFragment;
    MapListFragment mapListFragment;
    ContactUsFragment contactUsFragment;
    FavoriteFragment favoriteFragment;

    //Icons menu.
    @BindView(R.id.img_phone_list)
    ImageView imgPhoneList;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_map)
    ImageView imgMap;
    @BindView(R.id.img_favorite)
    ImageView imgFavorite;
    @BindView(R.id.img_contact_us)
    ImageView imgContactUs;

    @BindView(R.id.tv_actionbar)
    TextView tvActionbarTitle;

    @BindView(R.id.imgUser)
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activity = MainActivity.this;
        context = MainActivity.this;

        phoneListFragment = PhoneListFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, phoneListFragment, "PhoneListFragment");
        transaction.commit();

        //Set icon bottom of the view
        current_frag = "PhoneList";
        setMenuIcon();

        //Check login
        checkLogin();


    }

    @Override
    protected void onResume() {
        super.onResume();

        checkLogin();
    }

    private void checkLogin() {
        SharedPreferences mPref = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPref.getString("ProfileObject", "");
        subscription = mPref.getInt("Subscription", 0);
        mProfile = gson.fromJson(json, ProfileH.class);

        if (json.equals("")) {
            imgProfile.setImageResource(R.mipmap.userfrofiledefualt);
        } else {
//            Toast.makeText(activity, "User " + mProfile.getFirstName() + "has been log in", Toast.LENGTH_SHORT).show();
            String image_url = ApplicationConfig.getImageUrl() + mProfile.getImageProfile();
            Glide.with(this).load(image_url).into(imgProfile);
        }
    }


    @OnClick(R.id.imgNoti)
    public void onImgNotiClicked() {
        Intent iNoti = new Intent(this, NotificationDetail.class);
        startActivity(iNoti);
    }

    @OnClick(R.id.imgUser)
    public void onImgUserClicked() {
//        Toast.makeText(this, "User login has clicked", Toast.LENGTH_SHORT).show();
        if (subscription == 2) {
            //User are loged in.
            Intent iUserDetail = new Intent(this, UserDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user_profile", mProfile);
            iUserDetail.putExtras(bundle);
            startActivity(iUserDetail);
        } else {
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
        }

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

        current_frag = "PhoneList";
        setMenuIcon();
    }

    @OnClick(R.id.btn_menu_search)
    public void onBtnMenuSearchClicked() {
        searchFragment = SearchFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, searchFragment, "SearchFragment");
        transaction.commit();

        current_frag = "Search";
        setMenuIcon();
    }

    @OnClick(R.id.btn_menu_map)
    public void onBtnMenuMapClicked() {
        mapListFragment = MapListFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mapListFragment, "MapListFragment");
        transaction.commit();

        current_frag = "Map";
        setMenuIcon();
    }

    @OnClick(R.id.btn_menu_favorite)
    public void onBtnMenuFavoriteClicked() {
        favoriteFragment = FavoriteFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, favoriteFragment, "FavoriteFragment");
        transaction.commit();

        current_frag = "Favorite";
        setMenuIcon();
    }

    @OnClick(R.id.btn_menu_contact_us)
    public void onBtnMenuContactUsClicked() {
        contactUsFragment = ContactUsFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, contactUsFragment, "ContactUsFragment");
        transaction.commit();

        current_frag = "ContactUs";
        setMenuIcon();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            BaseFilterItem item = (BaseFilterItem) data.getSerializableExtra("valueFilter");

            if(item instanceof Province){
                searchFragment.setDropDownProvince((Province) item);
            } else if (item instanceof Department) {
                searchFragment.setDropDownDepartment((Department) item);
            } else if (item instanceof Rank) {
                searchFragment.setDropDownRank((Rank) item);
            } else if (item instanceof Position) {
                searchFragment.setDropDownPosition((Position) item);
            }

        }
    }

    private void setMenuIcon() {
        //Set icon color when was clicked
        switch (current_frag) {
            case "PhoneList":
                imgPhoneList.setImageResource(R.mipmap.search_ac);

                imgSearch.setImageResource(R.mipmap.advancesearch);
                imgMap.setImageResource(R.mipmap.map);
                imgFavorite.setImageResource(R.mipmap.fav);
                imgContactUs.setImageResource(R.mipmap.contact);
                tvActionbarTitle.setText(R.string.toolbarTitle);
                break;
            case "Search":
                imgSearch.setImageResource(R.mipmap.advancesearch_ac);

                imgPhoneList.setImageResource(R.mipmap.search);
                imgMap.setImageResource(R.mipmap.map);
                imgFavorite.setImageResource(R.mipmap.fav);
                imgContactUs.setImageResource(R.mipmap.contact);
                tvActionbarTitle.setText(R.string.toolbarTitle);
                break;
            case "Map":
                imgMap.setImageResource(R.mipmap.map_ac);
                imgSearch.setImageResource(R.mipmap.advancesearch);
                imgPhoneList.setImageResource(R.mipmap.search);
                imgFavorite.setImageResource(R.mipmap.fav);
                imgContactUs.setImageResource(R.mipmap.contact);
                tvActionbarTitle.setText(R.string.toolbarTitleMap);
                break;
            case "Favorite":
                imgFavorite.setImageResource(R.mipmap.fav_ac);

                imgSearch.setImageResource(R.mipmap.advancesearch);
                imgPhoneList.setImageResource(R.mipmap.search);
                imgMap.setImageResource(R.mipmap.map);
                imgContactUs.setImageResource(R.mipmap.contact);
                tvActionbarTitle.setText(R.string.toolbarTitle);
                break;
            case "ContactUs":
                imgContactUs.setImageResource(R.mipmap.contact_ac);

                imgSearch.setImageResource(R.mipmap.advancesearch);
                imgPhoneList.setImageResource(R.mipmap.search);
                imgMap.setImageResource(R.mipmap.map);
                imgFavorite.setImageResource(R.mipmap.fav);
                tvActionbarTitle.setText(R.string.toolbarTitle);
                break;
        }
    }

}
