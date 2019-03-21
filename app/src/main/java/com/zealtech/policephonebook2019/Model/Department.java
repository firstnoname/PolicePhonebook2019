package com.zealtech.policephonebook2019.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Department {


    String editBy;
    int level;
    int parentId;
    String departmentName;
    String shortName;
    ArrayList<String> tag;
    ArrayList<DepartmentPhoneNumbers> phoneNumbers;
    ArrayList<DepartmentFaxes> faxes;
    String address;
    String provinceId;
    String districtId;
    String latitude;
    String longitude;
    int views;
    Boolean flagTail;
    String id;
    String createDate;
    String updateDate;

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public ArrayList<DepartmentPhoneNumbers> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<DepartmentPhoneNumbers> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<DepartmentFaxes> getFaxes() {
        return faxes;
    }

    public void setFaxes(ArrayList<DepartmentFaxes> faxes) {
        this.faxes = faxes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Boolean getFlagTail() {
        return flagTail;
    }

    public void setFlagTail(Boolean flagTail) {
        this.flagTail = flagTail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
