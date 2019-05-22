package com.zealtech.policephonebook2019.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.PhoneNumber;
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
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    public static final int PERMISSION_REQUEST_STORAGE = 88;
    private EditText edtName, edtLastname, edtPhone, edtTelWork;
    private Button btnEdit;
    private ImageView imgInfo, imgClose;
    private TextView tvSelectPhoto, tvRank, tvDepartment, tvPosition, tvUpdateDate, tvTitle;
    private ProgressBar mProgressBar;

    private String IMAGE_URL = ApplicationConfig.getImageUrl();

    private String updateDate, updateTime;

//    Selected list
    private String editedName, id, editedLastname, editedPhoneNumber, editedWorkPhoneNumber, token;
    private int selectedDepartment, positionId, rankId;
    MultipartBody.Part imgProfile;

    private ProfileH mProfile = new ProfileH();
    private Department mDepartment = null;

    private static final int PICK_IMAGE = 100;
    private static final int PICK_DEPARTMENT = 2;
    private String img_path;
    Uri imageUri;
    private File f;

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
        mProgressBar = findViewById(R.id.progress_bar);
        tvTitle = findViewById(R.id.tv_actionbar_close);
        imgClose = findViewById(R.id.img_close);

        tvTitle.setText("แก้ไขข้อมูล");

        //Get value from UserDetailActivity.
        mProfile = (ProfileH) getIntent().getSerializableExtra("user_profile");

        //Set value to view.
        Glide.with(this).load(IMAGE_URL + mProfile.getImageProfile()).fitCenter().into(imgInfo);
        edtName.setText(mProfile.getFirstName());
        edtLastname.setText(mProfile.getLastName());
        //edtPhone.setText(mProfile.getPhoneNumber().get(0).getTel());
        tvRank.setText(mProfile.getRankName());
        tvPosition.setText(mProfile.getPositionName());
        tvDepartment.setText(mProfile.getDepartmentName());
        updateDate = mProfile.getUpdateDate().substring(0,10);
        updateTime = mProfile.getUpdateDate().substring(0, 0);
        tvUpdateDate.setText("อัพเดตข้อมูลล่าสุดเมื่อ " + updateDate + " เวลา " + updateTime);
        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermission();
            }
        });

        if (mProfile.getPhoneNumber().size() != 0) {
            edtPhone.setText(mProfile.getPhoneNumber().get(0).getTel());
        }else {
            edtPhone.setText("");
        }

        if (mProfile.getWorkPhoneNumber().size() != 0) {
            edtTelWork.setText(mProfile.getWorkPhoneNumber().get(0).getTel());
        } else {
            edtTelWork.setText("");
        }


        //Set default user data.
        selectedDepartment = mProfile.getDepartmentId();
        editedName = mProfile.getFirstName();
        id = mProfile.getId();
        editedLastname = mProfile.getLastName();
        if (mProfile.getPhoneNumber().size() != 0) {
            editedPhoneNumber = mProfile.getPhoneNumber().get(0).getTel();
        }

        if (mProfile.getWorkPhoneNumber().size() != 0) {
            editedWorkPhoneNumber = mProfile.getWorkPhoneNumber().get(0).getTel();
        }

        positionId = mProfile.getPositionId();
        rankId = mProfile.getRankId();
        token = mProfile.getToken();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedName = edtName.getText().toString().trim();
                editedLastname = edtLastname.getText().toString().trim();
//                ArrayList<PhoneNumber> phones = new ArrayList<>();
//                PhoneNumber phoneNumber = new PhoneNumber();
//                phoneNumber.setTel(edtPhone.getText().toString().trim());
//                phones.add(phoneNumber);
                editedPhoneNumber = "[{\"tel\":\"" + edtPhone.getText().toString().trim() + "\",\"telTo\":\"\"}]";
                editedWorkPhoneNumber = "[{\"tel\":\"" + edtTelWork.getText().toString().trim() + "\",\"telTo\":\"\"}]";
                if (f == null) {
                    pushEditedProfileWithoutImgProfile();
                } else {
                    pushEditedProfile();
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkStoragePermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("ต้องการใช้งานตำแหน่ง")
                        .setMessage("แอพพลิเคชั่นนี้ต้องการใช้งานข้อมูลตำแหน่ง เพื่อแสดงแผนที่ของสถานีตำรวจ")
                        .setPositiveButton("ต่อไป", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(EditProfileActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSION_REQUEST_STORAGE);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
        }
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
                    editedLastname, editedPhoneNumber, editedWorkPhoneNumber, positionId, rankId, token);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            Toast.makeText(EditProfileActivity.this, "บันทึกข้อมูล สำเร็จ", Toast.LENGTH_SHORT).show();
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
                            mProfile.setWorkPhoneNumber(response.body().getData().getWorkPhoneNumber());
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
        mProgressBar.setVisibility(View.VISIBLE);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageProfile", f.getName(), requestFile);
//        RequestBody fileReqBody = MultipartBody.create(MediaType.parse("image/*"), f);
//        imgProfile = MultipartBody.Part.createFormData("editProfile", f.getName(), fileReqBody);

        Call<ResponseProfile> call = api.editProfile(selectedDepartment, editedName, id, body,
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

                            mProgressBar.setVisibility(View.INVISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {
//
//            imgInfo.setImageURI(data.getData());
//
//            imageUri = data.getData();
//            img_path = getRealPathFromURI(imageUri);
//            if (img_path != null) {
//                File f = new File(img_path);
//                imageUri = Uri.fromFile(f);
//                setValueImage(imageUri);
//            }
//        }

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == PICK_IMAGE) {
                    Uri selectedImageUri = data.getData();
                    //Get the path from the Uri.
                    final String path = getPathFromURI(selectedImageUri);
                    //Set the image in ImageView.
                    imgInfo.setImageURI(selectedImageUri);
                    if (path != null) {
                        f = new File(path);
//                        selectedImageUri = Uri.fromFile(f);
                    }
                }
            }
        } catch (Exception e) {

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

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();

        return res;
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
