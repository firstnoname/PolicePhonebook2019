package com.zealtech.policephonebook2019.Model;

import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;

import java.io.Serializable;

public class Province extends BaseFilterItem implements Serializable {

    String provinceId = "";
    String provinceName = "";

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

    @Override
    protected String setId() {
        return provinceId;
    }

    @Override
    protected String setName() {
        return provinceName;
    }

    @Override
    public BaseFilterItem createTotalItem() {
        Province province = new Province();
        province.provinceId = "0";
        province.provinceName = "ทั้งหมด";
        return province;
    }
}
