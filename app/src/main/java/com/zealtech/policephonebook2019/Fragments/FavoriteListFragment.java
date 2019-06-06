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

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Adapters.AdapterFavoriteList;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.PhoneNumber;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.Realm.PoliceHistory;
import com.zealtech.policephonebook2019.Model.WorkPhoneNumber;
import com.zealtech.policephonebook2019.Model.response.ResponseFavorite;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteListFragment extends Fragment {

    private static final String TAG = "FavoriteListFragment";
    public static final String KEY_MESSAGE = "message";

    private String token = "";

    private TextView tvHistory;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Api api = AppUtils.getApiService();

    private ArrayList<Rank> ranks = new ArrayList<>();
    private ArrayList<Police> mPolice = new ArrayList<>();

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        tvHistory = view.findViewById(R.id.tvHistory);

        token = getArguments().getString(KEY_MESSAGE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.favorite_list);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if (token != "") {
            callApi();
        } else {
            callRealm();
        }

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void callRealm() {
        Realm.init(getActivity());
        RealmConfiguration config = new RealmConfiguration.Builder().name("sample1.realm")
                .schemaVersion(1).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<PoliceHistory> policeHistories = realm.where(PoliceHistory.class)
                .contains("favoriteType", "favorite")
                .findAll();

        if (policeHistories.size() != 0) {
            tvHistory.setVisibility(View.GONE);
            Police mPoliceOneRow;
            ArrayList<Police> mPolices = new ArrayList<>();

            for (int i = 0; i < policeHistories.size(); i++) {
                mPoliceOneRow = new Police();
                mPoliceOneRow.setImageProfile(policeHistories.get(i).getImageProfile());
                mPoliceOneRow.setRankName(policeHistories.get(i).getRankName());
                mPoliceOneRow.setFirstName(policeHistories.get(i).getFirstName());
                mPoliceOneRow.setLastName(policeHistories.get(i).getLastName());
                mPoliceOneRow.setPositionName(policeHistories.get(i).getPositionName());
                mPoliceOneRow.setDepartmentName(policeHistories.get(i).getDepartmentName());
                mPoliceOneRow.setRankId(policeHistories.get(i).getRankId());
                ArrayList<PhoneNumber> phoneNumber = new ArrayList<>();
                PhoneNumber phoneNumber1 = new PhoneNumber();
                phoneNumber1.setTel(policeHistories.get(i).getPhoneNumber());
                phoneNumber.add(phoneNumber1);
                mPoliceOneRow.setPhoneNumber(phoneNumber);
                ArrayList<WorkPhoneNumber> workPhoneNumber = new ArrayList<>();
                WorkPhoneNumber workPhoneNumber1 = new WorkPhoneNumber();
                workPhoneNumber1.setTel(policeHistories.get(i).getWorkPhoneNumber());
                mPoliceOneRow.setWorkPhoneNumber(workPhoneNumber);
                mPoliceOneRow.setUpdateDate(policeHistories.get(i).getUpdateDate());
                mPoliceOneRow.setId(policeHistories.get(i).getId());
                mPoliceOneRow.setColor(policeHistories.get(i).getColor());
                mPoliceOneRow.setUpdateDate(policeHistories.get(i).getUpdateDate());

//                Log.d(TAG, policeHistories.get(i).getFavoriteType());

                mPolices.add(mPoliceOneRow);
                mPolice.add(mPoliceOneRow);

                checkColor();


            }

            setAdapter(mPolices);

        } else {
            tvHistory.setText(R.string.dont_have_data2);
        }
    }

    private void callApi() {
        Call<ResponseFavorite> call = api.getFavorite(token);
        call.enqueue(new Callback<ResponseFavorite>() {
            @Override
            public void onResponse(Call<ResponseFavorite> call, Response<ResponseFavorite> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mPolice.addAll(response.body().getData());
//                            setAdapter(response.body().getData());
                            checkColor();
                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("code").equals("no_user_found")) {
                            Log.d(TAG, String.valueOf(jObjError.get("code")));
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, String.valueOf(jObjError.get("code")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFavorite> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });
    }

    private void checkColor() {
        Call<ResponseRank> call = api.getRankMasterData("");
        call.enqueue(new Callback<ResponseRank>() {
            @Override
            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            ranks.addAll(response.body().getData());
                            for (int x = 0; x < mPolice.size(); x++) {
                                for (int i = 0; i < ranks.size(); i++) {
                                    if (mPolice.get(x).getRankId() == ranks.get(i).getRankId()) {
                                        mPolice.get(x).setColor(ranks.get(i).getColor());
                                    }
                                }
                            }
                            setAdapter(mPolice);
//                                    AdapterPhoneList.this.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRank> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });

    }

    private void setAdapter(ArrayList<Police> content) {
        if (content.size() != 0) {
            tvHistory.setVisibility(View.GONE);
        }
        mAdapter = new AdapterPhoneListFilter(getActivity(), content);
        recyclerView.setAdapter(mAdapter);
    }
}
