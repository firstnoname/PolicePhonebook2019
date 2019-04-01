package com.zealtech.policephonebook2019.Activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.Department;
import com.zealtech.policephonebook2019.Model.Position;
import com.zealtech.policephonebook2019.Model.ProfileH;
import com.zealtech.policephonebook2019.Model.Province;
import com.zealtech.policephonebook2019.Model.Rank;
import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    private EditText edtName, edtLastname, edtPhone;
    private Button btnEdit;
    private ImageView imgInfo;
    private TextView tvSelectPhoto, tvRank, tvDepartment, tvPosition, tvUpdateDate;

    private String IMAGE_URL = "http://ztidev.com:8081/phonebook/download?file=";

    private String updateDate, updateTime;

    private ProfileH mProfile = new ProfileH();

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

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

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgInfo.setImageURI(imageUri);
        }

        if (data != null) {
            BaseFilterItem item = (BaseFilterItem) data.getSerializableExtra("valueFilter");

            if(item instanceof Province){

            } else if (item instanceof Department) {

            } else if (item instanceof Rank) {
                tvRank.setText(((Rank) item).getRankName());
            } else if (item instanceof Position) {

            }

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
        Intent iDepartment = new Intent(this, FilterActivity.class);
        iDepartment.putExtra("tag", "department");
        this.startActivityForResult(iDepartment, 1);
    }

}
