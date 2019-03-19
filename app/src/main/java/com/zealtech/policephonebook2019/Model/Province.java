package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Province implements Serializable {

    ArrayList<Province> content;
    Province province;

    String provinceId = "";
    String provinceName = "";

    public ArrayList<Province> getContent() {
        return content;
    }

    public void setContent(ArrayList<Province> content) {
        this.content = content;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
