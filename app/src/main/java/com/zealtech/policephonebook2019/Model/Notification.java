package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Notification implements Serializable {
    String editBy;
    String title;
    String description;
    ArrayList<String> readGroups;
    ArrayList<String> picturesPath;
    String author;
    String id;
    String createDate;
    String updateDate;

    Police authorProfile;

    public Police getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(Police authorProfile) {
        this.authorProfile = authorProfile;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public ArrayList<String> getReadGroups() {
        return readGroups;
    }

    public void setReadGroups(ArrayList<String> readGroups) {
        this.readGroups = readGroups;
    }

    public ArrayList<String> getPicturesPath() {
        return picturesPath;
    }

    public void setPicturesPath(ArrayList<String> picturesPath) {
        this.picturesPath = picturesPath;
    }
}
