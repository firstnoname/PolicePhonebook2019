package com.zealtech.policephonebook2019.Model;

import com.zealtech.policephonebook2019.Model.base.BaseItem;

import java.io.Serializable;
import java.util.ArrayList;

public class PoliceMasterData extends BaseItem implements Serializable {
    ArrayList<PoliceMasterData> content;
    PoliceMasterData policeMasterData;

    String imageProfile;
    String firstName;
    String lastName;
    String departmentName;
    String positionName;
    String rankName;
    String rankFullName;
    int rankId;
    int positionId;
    String workPhoneNumber;
    String phoneNumber;
    ArrayList<String> tag;
    String id;
    String color;

    public ArrayList<PoliceMasterData> getContent() {
        return content;
    }

    public void setContent(ArrayList<PoliceMasterData> content) {
        this.content = content;
    }

    public PoliceMasterData getPoliceMasterData() {
        return policeMasterData;
    }

    public void setPoliceMasterData(PoliceMasterData policeMasterData) {
        this.policeMasterData = policeMasterData;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankFullName() {
        return rankFullName;
    }

    public void setRankFullName(String rankFullName) {
        this.rankFullName = rankFullName;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    @Override
    protected String setId() {
        return id;
    }

    @Override
    protected String setName() {
        return firstName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getFirstNameAlphabetOnly() {
        return getFirstName().replaceAll("[^a-zA-Z0-9ก-ฮ]", "");
    }



}
