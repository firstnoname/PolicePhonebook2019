package com.zealtech.policephonebook2019.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Activities.ContactDetailFilterActivity;
import com.zealtech.policephonebook2019.Activities.SearchResultActivity;
import com.zealtech.policephonebook2019.Adapters.AdapterSearchviewSuggestion;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Police;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainSearchFragment extends Fragment implements View.OnClickListener,SearchView.OnQueryTextListener{

    private static final String TAG = "MainSearchFragment";

    private SearchView textKeyword;
    private CardView cvName, cvLastname, cvRank, cvPosition, cvDepartment, cvPhoneNumber;
    private ImageView imgCheckName, imgCheckLastname, imgCheckRank, imgCheckPosition,
            imgCheckDepartment, imgCheckPhoneNumber;
    private Button btnSearch;
    private EditText editText;

    private Api api = AppUtils.getApiService();

    private Boolean isNameChecked = true;
    private Boolean isLastnameChecked = true;
    private Boolean isRankChecked = false;
    private Boolean isPositionChecked = false;
    private Boolean isDepartmentChecked = false;
    private Boolean isPhoneNumberChecked = false;
    private int page = 0;
    private int sizeContents = 30;

    private String keyWord;

    ListView list;
    AdapterSearchviewSuggestion adapterSuggestion;
    private ArrayList<Police> mPolice;
    private ArrayList<Rank> ranks;

    public MainSearchFragment() {
        // Required empty public constructor
    }

    public static MainSearchFragment newInstance() {
        MainSearchFragment mainSearchFragment = new MainSearchFragment();

        return mainSearchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_search, container, false);

        textKeyword = view.findViewById(R.id.search_view);
        cvName = view.findViewById(R.id.checkbox_name);
        cvLastname = view.findViewById(R.id.checkbox_lastname);
        cvRank = view.findViewById(R.id.checkbox_rank);
        cvPosition = view.findViewById(R.id.checkbox_position);
        cvDepartment = view.findViewById(R.id.checkbox_department);
        cvPhoneNumber = view.findViewById(R.id.checkbox_phone_number);
        btnSearch = view.findViewById(R.id.btn_search);
        list = view.findViewById(R.id.listview_suggestions);
        editText = textKeyword.findViewById(R.id.search_src_text);

        imgCheckName = view.findViewById(R.id.img_check_name);
        imgCheckLastname = view.findViewById(R.id.img_check_lastname);
        imgCheckRank = view.findViewById(R.id.img_check_rank);
        imgCheckPosition = view.findViewById(R.id.img_check_position);
        imgCheckDepartment = view.findViewById(R.id.img_check_department);
        imgCheckPhoneNumber = view.findViewById(R.id.img_check_phone);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvName.setOnClickListener(this);
        cvLastname.setOnClickListener(this);
        cvRank.setOnClickListener(this);
        cvPosition.setOnClickListener(this);
        cvDepartment.setOnClickListener(this);
        cvPhoneNumber.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

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

        //Set listener to listview.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AnimalNames objAnimal;
//                objAnimal = (AnimalNames) parent.getAdapter().getItem(position);
//                editText.setText(objAnimal.getAnimalNames());
                ArrayList<Police> selectedPolice = new ArrayList<>();
                Police selectedItem;
                selectedItem = (Police) parent.getAdapter().getItem(position);
                if (selectedItem != null) {
                    selectedPolice.add(selectedItem);
                    Intent intent = new Intent(getActivity(), ContactDetailFilterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contact_detail", selectedPolice);
                    intent.putExtra("position", 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    list.setVisibility(View.GONE);
                } else {
                    intentResultSearch();
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkbox_name:
                if (isNameChecked == false) {
                    imgCheckName.setImageResource(R.mipmap.checked);
                    isNameChecked = true;
                } else {
                    imgCheckName.setImageResource(R.mipmap.check);
                    isNameChecked = false;
                }
                break;
            case R.id.checkbox_lastname:
                if (isLastnameChecked == false) {
                    imgCheckLastname.setImageResource(R.mipmap.checked);
                    isLastnameChecked = true;
                } else {
                    imgCheckLastname.setImageResource(R.mipmap.check);
                    isLastnameChecked = false;
                }
                break;
            case R.id.checkbox_rank:
                if (isRankChecked == false) {
                    imgCheckRank.setImageResource(R.mipmap.checked);
                    isRankChecked = true;
                } else {
                    imgCheckRank.setImageResource(R.mipmap.check);
                    isRankChecked = false;
                }
                break;
            case R.id.checkbox_position:
                if (isPositionChecked == false) {
                    imgCheckPosition.setImageResource(R.mipmap.checked);
                    isPositionChecked = true;
                } else {
                    imgCheckPosition.setImageResource(R.mipmap.check);
                    isPositionChecked = false;
                }
                break;
            case R.id.checkbox_department:
                if (isDepartmentChecked == false) {
                    imgCheckDepartment.setImageResource(R.mipmap.checked);
                    isDepartmentChecked = true;
                } else {
                    imgCheckDepartment.setImageResource(R.mipmap.check);
                    isDepartmentChecked = false;
                }
                break;
            case R.id.checkbox_phone_number:
                if (isPhoneNumberChecked == false) {
                    imgCheckPhoneNumber.setImageResource(R.mipmap.checked);
                    isPhoneNumberChecked = true;
                } else {
                    imgCheckPhoneNumber.setImageResource(R.mipmap.check);
                    isPhoneNumberChecked = false;
                }
                break;
            case R.id.btn_search:
                intentResultSearch();
        }
    }

    private void intentResultSearch() {
//        keyWord = textKeyword.getQuery().toString().trim();

        Intent i = new Intent(getActivity(), SearchResultActivity.class);
        i.putExtra("isNameChecked", isNameChecked);
        i.putExtra("isLastnameChecked", isLastnameChecked);
        i.putExtra("isRankChecked", isRankChecked);
        i.putExtra("isPositionChecked", isPositionChecked);
        i.putExtra("isDepartmentChecked", isDepartmentChecked);
        i.putExtra("isPhoneNumberChecked", isPhoneNumberChecked);
        i.putExtra("keyWord", keyWord);
        startActivity(i);
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

    private void setAdapter(ArrayList<Police> mPolice) {
        int i = 0;

        ArrayList<Police> suggestionLists = new ArrayList<>();

        if (mPolice.size() <= 5) {
            for (i = 0; i < mPolice.size(); i++) {
                if (mPolice.get(i) != null) {
                    suggestionLists.add(mPolice.get(i));
                }
            }
        } else {
            for (i = 0; i < 5; i++) {
                if (mPolice.get(i) != null) {
                    suggestionLists.add(mPolice.get(i));
                }
            }
        }

        suggestionLists.add(null);

        if (getActivity() != null) {
            adapterSuggestion = new AdapterSearchviewSuggestion(getActivity(), suggestionLists, keyWord);
            list.setAdapter(adapterSuggestion);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        intentResultSearch();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s != "") {
            keyWord = textKeyword.getQuery().toString().trim();
            isNameChecked = false;
            isLastnameChecked = false;
            refreshList(s);
            list.setVisibility(View.VISIBLE);
        } else {
            list.setVisibility(View.GONE);
        }

        if (s.equals("")) {
            list.setVisibility(View.GONE);
        }

        return false;
    }
}
