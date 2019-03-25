package com.zealtech.policephonebook2019.Config;

import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartmentRoot;
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
    Call<ResponseDepartment> getDepartment(@Query("level") int level, @Query("parentId") String departmentId);

    @GET("getDepartmentRoot")
    Call<ResponseDepartmentRoot> getDepartmentRoot(@Query("departmentId") String departmentId);

    @GET("getPoliceMasterData")
    Call<ResponsePoliceMasterData> getPoliceMasterData();

    @POST("login")
    Call<ResponseProfile> login(@Query("username") String username, @Query("password") String password);


}
