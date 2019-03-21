package com.zealtech.policephonebook2019.Config;

import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceMasterData;
import com.zealtech.policephonebook2019.Model.response.ResponseProfile;
import com.zealtech.policephonebook2019.Model.response.ResponseProvince;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("getProvince")
    Call<ResponseProvince> getProvince();

    @GET("getDepartment")
    Call<ResponseDepartment> getDepartment();

    @GET("getDepartment")
    Call<ResponseDepartment> getDepartmentTail(@Query("parentId") String parentId);

    @GET("getPoliceMasterData")
    Call<ResponsePoliceMasterData> getPoliceMasterData();

    @POST("login")
    Call<ResponseProfile> login(@Query("username") String username, @Query("password") String password);
}
