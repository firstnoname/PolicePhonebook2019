package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.policephonebook2019.R;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Spinner spinner = findViewById(R.id.spnRankText);

        List<String> test = new ArrayList<>();
        test.add("พลตำรวจเอก (พล.ต.อ.)");
        test.add("พลตำรวจโท (พล.ต.ท.๗)");
        test.add("พลตำรวจตรี (พล.ต.ต.)");
        test.add("พันตำรวจเอก (พ.ต.อ.)");

        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(this, R.layout.spinner_text, test );
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
    }
}
