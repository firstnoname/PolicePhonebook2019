package com.zealtech.policephonebook2019.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;
import com.zealtech.policephonebook2019.Model.response.ResponseProfile;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    private EditText edtName, edtLastname, edtPhone, edtTelWork;
    private Button btnEdit;
    private ImageView imgInfo;
    private TextView tvSelectPhoto, tvRank, tvDepartment, tvPosition, tvUpdateDate;

    private String IMAGE_URL = ApplicationConfig.getImageUrl();

    private String updateDate, updateTime;

//    Selected list
    private String editedName, id, editedLastname, editedPhoneNumber, token;
    private int selectedDepartment, positionId, rankId;
    MultipartBody.Part imgProfile;

    private ProfileH mProfile = new ProfileH();
    private Department mDepartment = null;

    private static final int PICK_IMAGE = 100;
    private static final int PICK_DEPARTMENT = 2;
    private String img_path;
    Uri imageUri;

    private Api api = AppUtils.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgInfo = findViewById(R.id.img_profile);
        edtName = findViewById(R.id.edt_name);
        edtLastname = findViewById(R.id.edt_lastname);
        edtPhone = findViewById(R.id.edt_phone);
        btnEdit = findViewById(R.id.btn_edit);
        tvSelectPhoto = findViewById(R.id.tv_select_photo);
        tvRank = findViewById(R.id.tv_rank);
        tvPosition = findViewById(R.id.tv_position);
        tvDepartment = findViewById(R.id.tv_department);
        tvUpdateDate = findViewById(R.id.tv_update_date);
        edtTelWork = findViewById(R.id.edt_tel_work);

        //Get value from UserDetailActivity.
        mProfile = (ProfileH) getIntent().getSerializableExtra("user_profile");

        //Set value to view.
        Glide.with(this).load(IMAGE_URL + mProfile.getImageProfile()).fitCenter().into(imgInfo);
        edtName.setText(mProfile.getFirstName());
        edtLastname.setText(mProfile.getLastName());
        edtPhone.setText(mProfile.getPhoneNumber());
        tvRank.setText(mProfile.getRankName());
        tvPosition.setText(mProfile.getPositionName());
        tvDepartment.setText(mProfile.getDepartmentName());
        updateDate = mProfile.getUpdateDate().substring(0,10);
        updateTime = mProfile.getUpdateDate().substring(0, 0);
        tvUpdateDate.setText("อัพเดตข้อมูลล่าสุดเมื่อ " + updateDate + " เวลา " + updateTime);
        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        edtTelWork.setText(mProfile.getWorkPhoneNumber());


        selectedDepartment = mProfile.getDepartmentId();
        editedName = mProfile.getFirstName();
        id = mProfile.getId();
        editedLastname = mProfile.getLastName();
        editedPhoneNumber = mProfile.getPhoneNumber();
        positionId = mProfile.getPositionId();
        rankId = mProfile.getRankId();
        token = mProfile.getToken();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedName = edtName.getText().toString().trim();
                editedLastname = edtLastname.getText().toString().trim();
                editedPhoneNumber = edtPhone.getText().toString().trim();

                if (imgProfile == null) {
                    pushEditedProfileWithoutImgProfile();
                } else {
                    pushEditedProfile();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Department mDepartment) {
        tvDepartment.setText(mDepartment.getDepartmentName());
        selectedDepartment = mDepartment.getDepartmentId();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void pushEditedProfileWithoutImgProfile() {
        Call<ResponseProfile> call = api.editProfileWithoutImageProfile(selectedDepartment, editedName, id,
                    editedLastname, editedPhoneNumber, positionId, rankId, token);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
//                              Get data from api.
                            mProfile.setEditBy(response.body().getData().getEditBy());
                            mProfile.setImageProfile(response.body().getData().getImageProfile());
                            mProfile.setUserName(response.body().getData().getUserName());
                            mProfile.setFirstName(response.body().getData().getFirstName());
                            mProfile.setLastName(response.body().getData().getLastName());
                            mProfile.setSuperAdmin(response.body().getData().getSuperAdmin());
                            mProfile.setGender(response.body().getData().getGender());
                            mProfile.setDepartmentId(response.body().getData().getDepartmentId());
                            mProfile.setPositionId(response.body().getData().getPositionId());
                            mProfile.setRankId(response.body().getData().getRankId());
                            mProfile.setPhoneNumber(response.body().getData().getPhoneNumber());
                            mProfile.setTag(response.body().getData().getTag());
                            mProfile.setDepartmentName(response.body().getData().getDepartmentName());
                            mProfile.setPositionName(response.body().getData().getPositionName());
                            mProfile.setRankName(response.body().getData().getRankName());
                            mProfile.setViews(response.body().getData().getViews());
                            mProfile.setFavorites(response.body().getData().getFavorites());
                            mProfile.setToken(token);
                            mProfile.setWorkPhoneNumber(response.body().getData().getWorkPhoneNumber());
                            mProfile.setDepartmentPhoneNumber(response.body().getData().getDepartmentPhoneNumber());
                            mProfile.setSuperAdmin(response.body().getData().getSuperAdmin());
                            mProfile.setId(response.body().getData().getId());
                            mProfile.setCreateDate(response.body().getData().getCreateDate());
                            mProfile.setUpdateDate(response.body().getData().getUpdateDate());
                            mProfile.setEnable(response.body().getData().getEnable());

//                                Save shared preferences.
                            SharedPreferences mPrefs = getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(mProfile);
                            prefsEditor.putString("ProfileObject", json);
                            prefsEditor.putInt("Subscription", 2);
                            prefsEditor.commit();

                            FirebaseMessaging.getInstance().unsubscribeFromTopic("1");
                            FirebaseMessaging.getInstance().subscribeToTopic("2");

//                              Display user detail page.
                            Intent iUserDetail = new Intent(getApplicationContext(), UserDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user_profile", mProfile);
                            iUserDetail.putExtras(bundle);
                            startActivity(iUserDetail);

                            finish();

                        } else {
                            Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, response.errorBody().string());
                        } else {
                            Log.d(TAG, response.errorBody().string());
                        }
                    } catch (JSONException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {

            }
        });
    }

    private void pushEditedProfile() {
        Call<ResponseProfile> call = api.editProfile(selectedDepartment, editedName, id, imgProfile,
                editedLastname, editedPhoneNumber, positionId, rankId, token);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
//                              Get data from api.
                            mProfile.setEditBy(response.body().getData().getEditBy());
                            mProfile.setImageProfile(response.body().getData().getImageProfile());
                            mProfile.setUserName(response.body().getData().getUserName());
                            mProfile.setFirstName(response.body().getData().getFirstName());
                            mProfile.setLastName(response.body().getData().getLastName());
                            mProfile.setSuperAdmin(response.body().getData().getSuperAdmin());
                            mProfile.setGender(response.body().getData().getGender());
                            mProfile.setDepartmentId(response.body().getData().getDepartmentId());
                            mProfile.setPositionId(response.body().getData().getPositionId());
                            mProfile.setRankId(response.body().getData().getRankId());
                            mProfile.setPhoneNumber(response.body().getData().getPhoneNumber());
                            mProfile.setTag(response.body().getData().getTag());
                            mProfile.setDepartmentName(response.body().getData().getDepartmentName());
                            mProfile.setPositionName(response.body().getData().getPositionName());
                            mProfile.setRankName(response.body().getData().getRankName());
                            mProfile.setViews(response.body().getData().getViews());
                            mProfile.setFavorites(response.body().getData().getFavorites());
                            mProfile.setToken(token);
                            mProfile.setWorkPhoneNumber(response.body().getData().getWorkPhoneNumber());
                            mProfile.setDepartmentPhoneNumber(response.body().getData().getDepartmentPhoneNumber());
                            mProfile.setSuperAdmin(response.body().getData().getSuperAdmin());
                            mProfile.setId(response.body().getData().getId());
                            mProfile.setCreateDate(response.body().getData().getCreateDate());
                            mProfile.setUpdateDate(response.body().getData().getUpdateDate());
                            mProfile.setEnable(response.body().getData().getEnable());

//                                Save shared preferences.
                            SharedPreferences mPrefs = getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(mProfile);
                            prefsEditor.putString("ProfileObject", json);
                            prefsEditor.putInt("Subscription", 2);
                            prefsEditor.commit();

                            FirebaseMessaging.getInstance().unsubscribeFromTopic("1");
                            FirebaseMessaging.getInstance().subscribeToTopic("2");

//                              Display user detail page.
                            Intent iUserDetail = new Intent(getApplicationContext(), UserDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user_profile", mProfile);
                            iUserDetail.putExtras(bundle);
                            startActivity(iUserDetail);

                            finish();

                        } else {
                            Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, response.errorBody().string());
                        } else {
                            Log.d(TAG, response.errorBody().string());
                        }
                    } catch (JSONException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Log.d(TAG, call.toString());
                Log.d(TAG, t.toString());
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {

            imgInfo.setImageURI(data.getData());

            imageUri = data.getData();
            img_path = getRealPathFromURI(imageUri);
            if (img_path != null) {
                File f = new File(img_path);
                imageUri = Uri.fromFile(f);
                setValueImage(imageUri);
            }
        }

        //Check return dropdown result.
        if (data != null) {
            BaseFilterItem item = (BaseFilterItem) data.getSerializableExtra("valueFilter");

            if(item instanceof Province){

            } else if (item instanceof Department) {

            } else if (item instanceof Rank) {
                tvRank.setText(((Rank) item).getRankName());
                rankId = ((Rank) item).getRankId();
            } else if (item instanceof Position) {
                tvPosition.setText(((Position) item).getPositionName());
                positionId = ((Position) item).getPositionId();
            }

        }

//        if (resultCode == RESULT_OK && requestCode == PICK_DEPARTMENT) {
//            if (data != null) {
////                tvDepartment.setText(data.getStringExtra("departmentName"));
////                selectedDepartment = data.getIntExtra("departmentId", 0);
//                mDepartment = new Department();
//                mDepartment = (Department) data.getSerializableExtra("departmentSelected");
//                tvDepartment.setText(mDepartment.getDepartmentName());
//            }
//        }

    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }

        return path;
    }

    private void setValueImage(Uri uri) {

        String path = uri.getPath();

//        Create temp file to write new bitmap(scale/compress image) for upload
        try {
            File uploadFile = new File(getApplicationContext().getCacheDir(), path);

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), uploadFile);
            imgProfile = MultipartBody.Part.createFormData("imageProfile", uploadFile.getName(), requestBody);

        } catch (Exception e) {

        }

    }

    public void selectRank(View view) {
        //Call api.
        Intent iRank = new Intent(this, FilterActivity.class);
        iRank.putExtra("tag", "rank");
        this.startActivityForResult(iRank, 1);
    }
    
    public void selectPosition(View view) {
        //Call api.
        Intent iPosition = new Intent(this, FilterActivity.class);
        iPosition.putExtra("tag", "position");
        this.startActivityForResult(iPosition, 1);
    }
    
    public void selectDepartment(View view) {
        //Call api.
        Intent iDepartment = new Intent(this, FilterDepartmentActivity.class);
        iDepartment.putExtra("tag", "department");
        iDepartment.putExtra("provinceId", "");
        iDepartment.putExtra("level", 1);
        iDepartment.putExtra("departmentId", "");
        this.startActivityForResult(iDepartment, PICK_DEPARTMENT);
    }

}
