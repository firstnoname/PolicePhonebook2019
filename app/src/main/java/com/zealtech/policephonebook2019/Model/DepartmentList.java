package com.zealtech.policephonebook2019.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DepartmentList implements Serializable {

    @SerializedName("content")
    ArrayList<Department> content;

    public ArrayList<Department> getContent() {
        return content;
    }

    public void setContent(ArrayList<Department> content) {
        this.content = content;
    }
}
