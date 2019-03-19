package com.zealtech.policephonebook2019.Config;

import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceMasterData;
import com.zealtech.policephonebook2019.Model.response.ResponseProvince;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("getProvince")
    Call<ResponseProvince> getProvince();

    @GET("getDepartment")
    Call<ResponseDepartment> getDepartment();

    @GET("getPoliceMasterData")
    Call<ResponsePoliceMasterData> getPoliceMasterData();
}
