package com.zealtech.policephonebook2019.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.R;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainSearchFragmentV2 extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainSearchFragmentV2";
    Api api = AppUtils.getApiService();
    // Paging
    ArrayList<Police> polices = new ArrayList<>();
    private SearchView textKeyword;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Police> mPolice;
    private ArrayList<Rank> ranks;
    private EditText editText;
    private ImageView imgLogo;
    private String keyword;
    private int totalElements = 0;
    private int mCurrentPage = 0;
    private int mItemPerRow = 30;
    private int page = 1;
    private int sizeContents = 30;
    private boolean isRefresh = true;

    public MainSearchFragmentV2() {
        // Required empty public constructor
    }

    public static MainSearchFragmentV2 newInstance() {
        MainSearchFragmentV2 mainSearchFragmentV2 = new MainSearchFragmentV2();

        return mainSearchFragmentV2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_search_fragment_v2, container, false);
        textKeyword = view.findViewById(R.id.search_view);
//        list = view.findViewById(R.id.listview_suggestions);
        editText = textKeyword.findViewById(R.id.search_src_text);
        recyclerView = view.findViewById(R.id.listview_suggestions);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        imgLogo = view.findViewById(R.id.img_logo);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgLogo.setAlpha(127);

        textKeyword.setIconifiedByDefault(false);
        ImageView searchIcon = textKeyword.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageDrawable(null);
        ImageView searchXIcon = textKeyword.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

        searchXIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                textKeyword.setQuery("", false);
                recyclerView.setVisibility(View.GONE);
                mPolice.clear();
                polices.clear();
            }
        });

        textKeyword.setOnQueryTextListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManagerLastItem = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManagerLastItem.getItemCount();
                int lastVisible = layoutManagerLastItem.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalElements > totalItemCount && isRefresh) {
                    if (totalItemCount > 0 && endHasBeenReached) {
                        refreshList(keyword);
                        isRefresh = false;
                    }
                }
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String s) {
        keyword = s;
        if (keyword != "" && keyword.length() > 1) {
            mCurrentPage = 0;
            polices.clear();
            refreshList(keyword);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        return false;
    }

    private void refreshList(String stringKeyWord) {
        Call<ResponsePoliceList> call = api.getPoliceSortByName(stringKeyWord, mCurrentPage, sizeContents);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mPolice = new ArrayList<>();
                            mPolice.addAll(response.body().getData().getContent());
                            totalElements = Integer.parseInt(response.body().getData().getTotalElements());
                            page = Integer.parseInt(response.body().getData().getTotalPages());
                            checkColor();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePoliceList> call, Throwable t) {
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
                            ranks = new ArrayList<>();
                            ranks.addAll(response.body().getData());
                            for (int x = 0; x < mPolice.size(); x++) {
                                for (int i = 0; i < ranks.size(); i++) {
                                    if (mPolice.get(x).getRankId() == ranks.get(i).getRankId()) {
                                        mPolice.get(x).setColor(ranks.get(i).getColor());
                                    }
                                }
                            }
//                            setAdapter(mPolice);
                            getData(mPolice);
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

    private void setAdapter(ArrayList<Police> dataSet) {
        if (getActivity() != null) {
            mAdapter = new AdapterPhoneListFilter(getActivity(), dataSet);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            if (dataSet.size() > 30) {
                recyclerView.scrollToPosition(dataSet.size() - 29);
            }
        }
    }

    private void getData(ArrayList<Police> mPolices) {
        // ถ้า item ที่แสดงใน listview มีค่ามากกว่าหรือเท่ากับจำนวนทั้งหมด ให้แสดงข้อความว่า ไม่มีข้อมูลแล้ว
//        if (mItemPerRow * mCurrentPage >= totalElements) {
//            Toast.makeText(getActivity(), "ไม่มีข้อมูล", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // ถ้า item ยังแสดงไม่หมด ให้เพิ่มข้อมูลเข้าไปใน mCurrentData เพื่อแสดงข้อมูลเพิ่ม
//        for (int i = 0; i < mItemPerRow; i++) {
////            mCurrentData.add(mTotalData.get(i + ((mCurrentPage - 1) * mItemPerRow)));
//            mPolice.add(mPolices.get(i + ((mCurrentPage - 1) * mItemPerRow)));
//        }

        polices.addAll(mPolice);
        mCurrentPage += 1;
        if (mCurrentPage < page) {
            isRefresh = true;
        }
        setAdapter(polices);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        intentToDetailPage();

        return false;
    }

    private void intentToDetailPage() {

    }

}
