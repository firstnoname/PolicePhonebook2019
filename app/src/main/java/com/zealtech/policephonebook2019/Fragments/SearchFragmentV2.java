package com.zealtech.policephonebook2019.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.squareup.otto.Subscribe;
import com.zealtech.policephonebook2019.Activities.AdvanceSearchResultActivity;
import com.zealtech.policephonebook2019.Activities.ContactDetailFilterActivity;
import com.zealtech.policephonebook2019.Activities.FilterActivity;
import com.zealtech.policephonebook2019.Activities.FilterDepartmentActivity;
import com.zealtech.policephonebook2019.Adapters.AdapterPhoneListFilter;
import com.zealtech.policephonebook2019.Adapters.AdapterSearchviewSuggestion;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;
import com.zealtech.policephonebook2019.Util.BusProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragmentV2 extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener {

    private static final String TAG = "SearchFragment";

    String departmentId = "";
    String provinceId = "";
    String positionId = "";
    String rankId = "";

    Province selectProvince = null;
    Department selectDepartment = null;
    Rank selectRank = null;
    Position selectPosition = null;

    CardView cvProvince, cvRank, cvPosition, cvDepartment;
    TextView tvProvince, tvDepartment, tvRank, tvPosition, tvReset;
    SearchView textKeyword;
    Button btnSearch;
    private EditText editText;

    Api api = AppUtils.getApiService();

    private Boolean isNameChecked = false;
    private Boolean isLastnameChecked = false;
    private Boolean isRankChecked = false;
    private Boolean isPositionChecked = false;
    private Boolean isDepartmentChecked = false;
    private Boolean isPhoneNumberChecked = false;
    private int page = 0;
    private int sizeContents = 30;

    private String keyWord="";

    //Adapter
    ListView list;
    AdapterSearchviewSuggestion adapterSuggestion;
    ArrayList<Police> mPolice;
    ArrayList<Rank> ranks = new ArrayList<>();

    public SearchFragmentV2() {
        // Required empty public constructor
    }

    public static SearchFragmentV2 newInstance() {
        SearchFragmentV2 searchFragment = new SearchFragmentV2();

        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "create view");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_v2, container, false);

        btnSearch = v.findViewById(R.id.btn_search);
        tvProvince = v.findViewById(R.id.tvProvince);
        tvDepartment = v.findViewById(R.id.tvDepartment);
        tvPosition = v.findViewById(R.id.tv_position);
        tvRank = v.findViewById(R.id.tvRank);
        tvReset = v.findViewById(R.id.tv_reset);

        textKeyword = v.findViewById(R.id.search_view);
        editText = textKeyword.findViewById(R.id.search_src_text);

        list = v.findViewById(R.id.listview_suggestions);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "view created");

        btnSearch.setOnClickListener(this);
        tvReset.setOnClickListener(this);

        //Hide search icon in SearchView.
        textKeyword.setIconifiedByDefault(false);
        ImageView searchIcon = textKeyword.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageDrawable(null);
        ImageView searchXIcon = textKeyword.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

        searchXIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                textKeyword.setQuery("", false);
                list.setVisibility(View.GONE);
            }
        });

        // Locate the EditText in listview_main.xml
        textKeyword.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Police> selectedPolice = new ArrayList<>();
                Police selectedItem;
                selectedItem = (Police) parent.getAdapter().getItem(position);
                selectedPolice.add(selectedItem);
                Intent intent = new Intent(getActivity(), ContactDetailFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact_detail", selectedPolice);
                intent.putExtra("position", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                list.setVisibility(View.GONE);
            }
        });

        cvProvince = view.findViewById(R.id.cardViewProvince);
        cvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iProvince = new Intent(getActivity(), FilterActivity.class);
                iProvince.putExtra("tag", "province");
                getActivity().startActivityForResult(iProvince, 1);
            }
        });

        cvRank = view.findViewById(R.id.cardViewRank);
        cvRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRank = new Intent(getActivity(), FilterActivity.class);
                iRank.putExtra("tag", "rank");
                getActivity().startActivityForResult(iRank, 1);
            }
        });

        cvPosition = view.findViewById(R.id.cardViewPosition1);
        cvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPosition = new Intent(getActivity(), FilterActivity.class);
                iPosition.putExtra("tag", "position");
                getActivity().startActivityForResult(iPosition, 1);
            }
        });

        cvDepartment = view.findViewById(R.id.cardViewDepartment);
        cvDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDepartment = new Intent(getActivity(), FilterDepartmentActivity.class);
                iDepartment.putExtra("tag", "department");
                iDepartment.putExtra("provinceId", provinceId);
                getActivity().startActivityForResult(iDepartment, 2);
            }
        });

//        searchView.setOnQueryTextListener(this);

        onRefreshView(departmentId, positionId, rankId, keyWord);
    }

    private void setAdapter(ArrayList<Police> dataSet) {
        int i;
        ArrayList<Police> suggestionLists = new ArrayList<>();

        if (dataSet.size() <= 5) {
            for (i = 0; i < dataSet.size(); i++) {
                if (dataSet.get(i) != null) {
                    suggestionLists.add(dataSet.get(i));
                }
            }
        } else {
            for (i = 0; i < 5; i++) {
                if (dataSet.get(i) != null) {
                    suggestionLists.add(dataSet.get(i));
                }
            }
        }

        adapterSuggestion = new AdapterSearchviewSuggestion(getActivity(), suggestionLists);
        list.setAdapter(adapterSuggestion);
    }


    public void setDropDownProvince(Province item) {
        selectProvince = item;
        tvProvince.setText(selectProvince.getProvinceName());

        if (selectProvince.getProvinceId() == "0") {
            provinceId = "";
        } else {
            provinceId = selectProvince.getProvinceId();
        }
        //Set department dropdown.
    }

    public void setDropDownDepartment(Department item) {
        if (item != null) {
            selectDepartment = item;
            tvDepartment.setText(selectDepartment.getDepartmentName());
            if (selectDepartment.getDepartmentId() == 0) {
                departmentId = "";
            } else {
                departmentId = String.valueOf(selectDepartment.getDepartmentId());
            }
            onRefreshView(departmentId, positionId, rankId, keyWord);
        } else {
            Toast.makeText(getActivity(), "Department empty", Toast.LENGTH_SHORT).show();
        } 
    }

    public void setDropDownRank(Rank item) {
        selectRank = item;
        tvRank.setText(selectRank.getRankName());
        if (selectRank.getRankId() == 0) {
            rankId = "";
        } else {
            rankId = String.valueOf(selectRank.getRankId());
        }

        onRefreshView(departmentId, positionId, rankId, keyWord);
    }

    public void setDropDownPosition(Position item) {
        selectPosition = item;
        tvPosition.setText(selectPosition.getPositionName());
        if (selectPosition.getPositionId() == 0) {
            positionId = "";
        } else {
            positionId = String.valueOf(selectPosition.getPositionId());
        }

        onRefreshView(departmentId, positionId, rankId, keyWord);
    }

    private void onRefreshView(String departmentId, String positionId, String rankId, String keyword) {
        mPolice = new ArrayList<>();
        Call<ResponsePoliceList> call = api.getPoliceList(departmentId, positionId, rankId, keyword, "", "",4);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            //tvListSize.setText(response.body().getData().getContent().size() + "รายการ");
                            mPolice.addAll(response.body().getData().getContent());
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
            public void onFailure(Call<ResponsePoliceList> call, Throwable t) {
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

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        BusProvider.getInstance().register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "pause");
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void recievedMessage(String data) {
        Log.d(TAG, data);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {

        intentResultSearch();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        keyWord = s;
        //onRefreshView(departmentId, positionId, rankId, keyword);
        if (s != "") {
            refreshList(s);
            list.setVisibility(View.VISIBLE);
        } else {
            list.setVisibility(View.GONE);
        }

        if (s.equals("")) {
            list.setVisibility(View.GONE);
        }
        return true;
    }

    private void refreshList(String stringKeyWord) {
        Call<ResponsePoliceList> call = api.getPoliceListFilter("","",
                stringKeyWord, isDepartmentChecked, isNameChecked, isLastnameChecked, isPhoneNumberChecked,
                isPositionChecked, isRankChecked, page, sizeContents, 4);
        call.enqueue(new Callback<ResponsePoliceList>() {
            @Override
            public void onResponse(Call<ResponsePoliceList> call, Response<ResponsePoliceList> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mPolice = new ArrayList<>();
                            mPolice.addAll(response.body().getData().getContent());
                            checkColorSuggestion();
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

    private void checkColorSuggestion() {

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
                            setAdapter(mPolice);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                intentResultSearch();
                break;
            case R.id.tv_reset:
                //reset filter
                tvProvince.setText("ทั้งหมด");
                provinceId = "";
                tvDepartment.setText("ทั้งหมด");
                departmentId = "";
                tvRank.setText("ทั้งหมด");
                rankId = "";
                tvPosition.setText("ทั้งหมด");
                positionId = "";
                break;
        }
    }

    private void intentResultSearch() {
        keyWord = textKeyword.getQuery().toString().trim();
        Intent i = new Intent(getActivity(), AdvanceSearchResultActivity.class);
        i.putExtra("keyWord", keyWord);
        i.putExtra("province", provinceId);
        i.putExtra("department", departmentId);
        i.putExtra("rank", rankId);
        i.putExtra("position", positionId);
        startActivity(i);
    }
}
