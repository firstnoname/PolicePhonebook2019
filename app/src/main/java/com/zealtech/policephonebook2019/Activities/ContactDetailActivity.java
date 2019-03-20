package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.PoliceMasterData;

import java.util.ArrayList;

public class ContactDetailActivity extends AppCompatActivity {

    private static final String TAG = "ContactDetailActivity";

    private String fullName, strPosition, department, tel1, tel2;
    private TextView tvName, tvPosition, tvDepartment, tvTel1, tvTel2, tvBack;
    private ImageView imgFavorite, imgClose;

    ArrayList<PoliceMasterData> policeMasterData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        tvName = findViewById(R.id.tv_contact_name);
        tvPosition = findViewById(R.id.tv_contact_position);
        tvDepartment = findViewById(R.id.tv_contact_department);
        tvTel1 = findViewById(R.id.tv_contact_tel);
        tvTel2 = findViewById(R.id.tv_contact_phone);
        imgFavorite = findViewById(R.id.imgFavorite);
        imgClose = findViewById(R.id.imgBack);
        tvBack = findViewById(R.id.tvBack);

        policeMasterData = (ArrayList<PoliceMasterData>) getIntent().getSerializableExtra("contact_detail");
        int position = getIntent().getIntExtra("position", 0);

        fullName = policeMasterData.get(position).getFirstName() + "  " + policeMasterData.get(position).getLastName();
        strPosition = policeMasterData.get(position).getPositionName();
        department = policeMasterData.get(position).getDepartmentName();

        tvName.setText(fullName);
        tvPosition.setText(strPosition);
        tvDepartment.setText(department);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFavorite.setImageResource(R.drawable.star_ac);
            }
        });
    }

}
