package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {

    ArrayList<Department> content;

    public ArrayList<Department> getContent() {
        return content;
    }

    public void setContent(ArrayList<Department> content) {
        this.content = content;
    }

}
