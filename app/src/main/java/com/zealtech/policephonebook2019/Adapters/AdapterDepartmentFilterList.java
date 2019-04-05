package com.zealtech.policephonebook2019.Adapters;

import android.app.Activity;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Model.Department;

import java.util.ArrayList;

public class AdapterDepartmentFilterList {

    private static final String TAG = "AdapterDepartmentFilterList";

    private Activity activity;
    private ArrayList<Department> mDepartment;

    int resId = R.mipmap.policestation_ic;
    int level = 1;

    public AdapterDepartmentFilterList(Activity activity, ArrayList<Department> mDepartment) {
        this.activity = activity;
        this.mDepartment = mDepartment;
    }

}
