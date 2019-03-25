package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;

public class DepartmentPhoneNumbers implements Serializable {
    String tel;
    String telTo;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelTo() {
        return telTo;
    }

    public void setTelTo(String telTo) {
        this.telTo = telTo;
    }
}
