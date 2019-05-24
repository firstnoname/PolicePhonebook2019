package com.zealtech.policephonebook2019.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zealtech.policephonebook2019.Activities.StationDetailActivity;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.DepartmentRoot;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartmentRoot;
import com.zealtech.policephonebook2019.Util.AppUtils;

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
public class StationDetailFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "StationDetailFragment";
    public static final String KEY_MESSAGE = "message";

    private TextView tvStationPhone1, tvStationPhone2, tvStationAddress, btnOpenMap, tvAbTitle;
    private TextView tvStationEmail, tvStationWeb, tvDepartmentName;
    private ImageView imgPhoneCall;

    private String departmentId = "";
    String title;
    private ArrayList<DepartmentRoot> mDepartmentRoot = new ArrayList<>();
    private Double latitude = 10.5204512;
    private Double longitude = 99.1936077;

    Api api = AppUtils.getApiService();

    private MapView mMapView;

    public StationDetailFragment() {
        // Required empty public constructor
    }

    public static StationDetailFragment newInstance(String departmentId) {
        StationDetailFragment fragment = new StationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, departmentId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_station_detail, container, false);

        tvStationPhone1 = view.findViewById(R.id.tv_statoin_phone1);
        tvStationPhone2 = view.findViewById(R.id.tv_station_phone2);
        tvStationAddress = view.findViewById(R.id.tv_station_address);
        btnOpenMap = view.findViewById(R.id.btn_station_open_map);
        tvAbTitle = view.findViewById(R.id.tv_actionbar_back);
        imgPhoneCall = view.findViewById(R.id.img_station_call);
        tvStationEmail = view.findViewById(R.id.tv_station_email);
        tvStationWeb = view.findViewById(R.id.tv_station_web);
        tvDepartmentName = view.findViewById(R.id.tv_department_name);

        departmentId = getArguments().getString(KEY_MESSAGE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Call api
        Call<ResponseDepartmentRoot> call = api.getDepartmentRoot(departmentId);
        call.enqueue(new Callback<ResponseDepartmentRoot>() {
            @Override
            public void onResponse(Call<ResponseDepartmentRoot> call, Response<ResponseDepartmentRoot> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mDepartmentRoot.addAll(response.body().getData());
//                            Log.d(TAG, String.valueOf(mDepartmentRoot.get(0).getDepartmentName()));
                            setDataToLayout(mDepartmentRoot);
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, "ไม่พบผู้ใช้งาน");
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, "ไม่พบผู้ใช้งาน");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, e.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDepartmentRoot> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });

//        Set map
        mMapView = view.findViewById(R.id.map_station_detail);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap = googleMap;

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                LatLng contactUsLocation = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(contactUsLocation).title("Contact us").snippet(""));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(contactUsLocation).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        imgPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPhoneCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:" + mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTel()));
                        startActivity(i);
                    }
                });
            }
        });

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = mDepartmentRoot.get(0).getDepartmentName();
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + title + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }

    private void setDataToLayout(ArrayList<DepartmentRoot> dataDepartmentRoot) {

        this.mDepartmentRoot = dataDepartmentRoot;

//        String fullAddress = mDepartmentRoot.get(0).getAddress() + " ถ." + mDepartmentRoot.get(0).getRoad() + " แขวง " + mDepartmentRoot.get(0).getPostcode();
        String fullAddress = mDepartmentRoot.get(0).getAddress();

        tvDepartmentName.setText(mDepartmentRoot.get(0).getDepartmentName());

        if (!mDepartmentRoot.get(0).getPhoneNumbers().isEmpty()) {
            if (mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTelTo() != null || mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTelTo() != "") {
                tvStationPhone1.setText(mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTel() + " ต่อ " + mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTelTo());
            } else {
                tvStationPhone1.setText(mDepartmentRoot.get(0).getPhoneNumbers().get(0).getTel());
            }

        } else {
            tvStationPhone1.setText(R.string.dont_have_data);
        }

        if (!mDepartmentRoot.get(0).getFaxes().isEmpty()) {
            tvStationPhone2.setText(mDepartmentRoot.get(0).getFaxes().get(0).getFaxNo());
        } else {
            tvStationPhone2.setText(R.string.dont_have_data);
        }

        if (mDepartmentRoot.get(0).getAddress() != null) {
            tvStationAddress.setText(fullAddress);
        } else {
            tvStationAddress.setText(R.string.dont_have_data);
        }

        if (mDepartmentRoot.get(0).getLatitude() != null && mDepartmentRoot.get(0).getLongitude() != null) {
            if (!mDepartmentRoot.get(0).getLatitude().isEmpty() && !mDepartmentRoot.get(0).getLongitude().isEmpty()) {
                latitude = Double.valueOf(mDepartmentRoot.get(0).getLatitude());
                longitude = Double.valueOf(mDepartmentRoot.get(0).getLongitude());
            }
        } else {
            latitude = 18.7988609;
            longitude = 99.0238646;
        }

        if (mDepartmentRoot.get(0).getWebsite() != null) {
            tvStationWeb.setText(mDepartmentRoot.get(0).getWebsite());
        } else {
            tvStationWeb.setText(R.string.dont_have_data);
        }

        if (mDepartmentRoot.get(0).getEmail() != null) {
            tvStationEmail.setText(mDepartmentRoot.get(0).getEmail());
        } else {
            tvStationEmail.setText(R.string.dont_have_data);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;

        LatLng stationLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(stationLocation).title(mDepartmentRoot.get(0).getDepartmentName()));

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(stationLocation));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(stationLocation);
        final LatLngBounds bounds = builder.build();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        googleMap.animateCamera(cameraUpdate);
    }


}
