package com.zealtech.policephonebook2019.Activities;

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
import com.zealtech.policephonebook2019.Model.ProfileH;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    private EditText edtName, edtLastname, edtPhone;
    private Button btnEdit;
    private ImageView imgInfo;

    private String IMAGE_URL = "http://ztidev.com:8081/phonebook/download?file=";

    private ProfileH mProfile = new ProfileH();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgInfo = findViewById(R.id.img_profile);
        edtName = findViewById(R.id.edt_name);
        edtLastname = findViewById(R.id.edt_lastname);
        edtPhone = findViewById(R.id.edt_phone);
        btnEdit = findViewById(R.id.btn_edit);

        //Get value from UserDetailActivity.
        mProfile = (ProfileH) getIntent().getSerializableExtra("user_profile");

        //Set value to view.
        Glide.with(this).load(IMAGE_URL + mProfile.getImageProfile()).fitCenter().into(imgInfo);
        edtName.setText(mProfile.getFirstName());
        edtLastname.setText(mProfile.getLastName());
        edtPhone.setText(mProfile.getPhoneNumber());
        

    }
    
    public void selectRank(View view) {
        Toast.makeText(this, "Rank", Toast.LENGTH_SHORT).show();

        //Call api.

        //Set ArrayList to adapter.

    }
    
    public void selectPosition(View view) {
        Toast.makeText(this, "Position", Toast.LENGTH_SHORT).show();

        //Call api.

        //Set ArrayList to adapter.

    }
    
    public void selectDepartment(View view) {
        Toast.makeText(this, "Department", Toast.LENGTH_SHORT).show();

        //Call api.

        //Set ArrayList to adapter.

    }
}