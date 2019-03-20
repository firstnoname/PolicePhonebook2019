package com.zealtech.policephonebook2019.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Department {

    @SerializedName("editBy")
    String editBy;

    @SerializedName("level")
    int level;

    @SerializedName("parentId")
    int parentId;

    @SerializedName("departmentId")
    int departmentId;

    @SerializedName("departmentName")
    String departmentName;

    @SerializedName("shortName")
    String shortName;

    @SerializedName("tag")
    ArrayList<String> tag;

    @SerializedName("phoneNumbers")
    ArrayList<String> phoneNumbers;

    @SerializedName("faxes")
    ArrayList<String> faxes;

    @SerializedName("address")
    String address;

    @SerializedName("provinceId")
    int provinceId;

    @SerializedName("districtId")
    int districtId;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("flagTail")
    Boolean flagTail;

    @SerializedName("id")
    String id;

    @SerializedName("createDate")
    String createDate;

    @SerializedName("updateDate")
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
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

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getFaxes() {
        return faxes;
    }

    public void setFaxes(ArrayList<String> faxes) {
        this.faxes = faxes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
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
