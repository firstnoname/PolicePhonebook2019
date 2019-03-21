package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileH implements Serializable {


    ArrayList<Profile> content;

    public ArrayList<Profile> getContent() {
        return content;
    }

    public void setContent(ArrayList<Profile> content) {
        this.content = content;
    }
}
