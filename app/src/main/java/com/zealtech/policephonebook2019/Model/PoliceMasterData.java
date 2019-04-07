package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class PoliceMasterData implements Serializable {
    ArrayList<PoliceMasterData> content;
    PoliceMasterData policeMasterData;

    String imageProfile;
    String firstName;
    String lastName;
    String departmentName;
    String positionName;
    String rankName;
    ArrayList<String> tag;
    String id;

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

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
