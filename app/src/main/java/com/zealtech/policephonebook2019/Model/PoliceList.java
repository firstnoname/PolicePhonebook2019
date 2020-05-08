package com.zealtech.policephonebook2019.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PoliceList implements Serializable {

    @SerializedName("content")
    ArrayList<Police> content;
    String totalPages;
    String totalElements;

    public ArrayList<Police> getContent() {
        return content;
    }

    public void setContent(ArrayList<Police> content) {
        this.content = content;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(String totalElements) {
        this.totalElements = totalElements;
    }
}
