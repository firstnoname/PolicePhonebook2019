package com.zealtech.policephonebook2019.Config;

import com.zealtech.policephonebook2019.Model.response.ResponseDepartment;
import com.zealtech.policephonebook2019.Model.response.ResponseDepartmentRoot;
import com.zealtech.policephonebook2019.Model.response.ResponseEditPassword;
import com.zealtech.policephonebook2019.Model.response.ResponseFavorite;
import com.zealtech.policephonebook2019.Model.response.ResponseNotification;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceList;
import com.zealtech.policephonebook2019.Model.response.ResponsePoliceMasterData;
import com.zealtech.policephonebook2019.Model.response.ResponsePosition;
import com.zealtech.policephonebook2019.Model.response.ResponseProfile;
import com.zealtech.policephonebook2019.Model.response.ResponseProvince;
import com.zealtech.policephonebook2019.Model.response.ResponseRank;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    @GET("getProvince")
    Call<ResponseProvince> getProvince();

    @GET("getDepartment")
    Call<ResponseDepartment> getDepartment(@Query("level") int level,
                                           @Query("parentId") String departmentId,
                                           @Query("sizeContents") Integer sizeContents);

    @GET("getDepartment")
    Call<ResponseDepartment> getDepartment(@Query("level") int level,
                                           @Query("parentId") String departmentId,
                                           @Query("provinceId") String provinceId);

    @GET("getDepartmentRoot")
    Call<ResponseDepartmentRoot> getDepartmentRoot(@Query("departmentId") String departmentId);

    @GET("getPoliceMasterData")
    Call<ResponsePoliceMasterData> getPoliceMasterData();

    @POST("login")
    Call<ResponseProfile> login(@Query("username") String username,
                                @Query("password") String password);

    @POST("editPassword")
    Call<ResponseEditPassword> editPassword(@Query("newPassword") String newPassword,
                                            @Query("newPasswordConfirm") String newPasswordConfirm,
                                            @Query("oldPassword") String oldPassword,
                                            @Header("token") String token);

    @GET("getNotifications")
    Call<ResponseNotification> getNotifications(@Query("id") String id);

    @GET("getPolice")
    Call<ResponsePoliceList> getPoliceList(@Query("departmentId") String departmentId,
                                           @Query("provinceId") String provinceId,
                                           @Query("positionId") String positionId,
                                           @Query("rankId") String rankId,
                                           @Query("keyWord") String keyword,
                                           @Query("id") String id,
                                           @Query("sizeContents") Integer sizeContents,
                                           @Query("sort") int sort);

    @GET("getPolice")
    Call<ResponsePoliceList> getPoliceListFilter(@Query("departmentId") String departmentId,
                                                 @Query("id") String id,
                                                 @Query("searchName") Boolean searchName,
                                                 @Query("keyWord") String keyword,
                                                 @Query("keyWordDepartmentName") Boolean keyWordDepartmentName,
                                                 @Query("keyWordFirstName") Boolean keyWordFirstName,
                                                 @Query("keyWordLastName") Boolean keyWordLastName,
                                                 @Query("keyWordPhoneNumber") Boolean keyWordPhoneNumber,
                                                 @Query("keyWordPositionName") Boolean keyWordPositionName,
                                                 @Query("keyWordRankName") Boolean keyWordRankName,
                                                 @Query("page") Integer page,
                                                 @Query("role") Integer role,
                                                 @Query("sizeContents") Integer sizeContents,
                                                 @Query("sort") Integer sort);

    @GET("getPoliceOrderByKeyWord")
    Call<ResponsePoliceList> getPoliceSortByName(@Query("keyWord") String keyWord,
                                                 @Query("page") int page,
                                                 @Query("sizeContents") int sizeContents);

    @GET("getRankMasterData")
    Call<ResponseRank> getRankMasterData(@Query("id") String id);

    @GET("getPositionMasterData")
    Call<ResponsePosition> getPositionMasterData(@Query("id") String id);

    @GET("getFavorite")
    Call<ResponseFavorite> getFavorite(@Header("token") String token);

    @POST("addFavorite")
    Call<ResponseFavorite> addFavorite(@Query("id") String id, @Header("token") String token);

    @POST("removeFavorite")
    Call<ResponseFavorite> removeFavorite(@Query("id") String id, @Header("token") String token);

    @Multipart
    @POST("editProfile")
    Call<ResponseProfile> editProfile(@Query("departmentId") int departmentId,
                                      @Query("firstName") String firstName,
                                      @Query("id") String id,
                                      @Part MultipartBody.Part imageProfile,
                                      @Query("lastName") String lastName,
                                      @Query("PhoneNumber") String phoneNumber,
                                      @Query("positionId") int positionId,
                                      @Query("rankId") int rankId,
                                      @Header("token") String token);

    @POST("editProfile")
    Call<ResponseProfile> editProfileWithoutImageProfile(@Query("departmentId") int departmentId,
                                                         @Query("firstName") String firstName,
                                                         @Query("id") String id,
                                                         @Query("lastName") String lastName,
                                                         @Query("phoneNumber") String phoneNumber,
                                                         @Query("workPhoneNumber") String workPhoneNumber,
                                                         @Query("positionId") int positionId,
                                                         @Query("rankId") int rankId,
                                                         @Header("token") String token);


}
