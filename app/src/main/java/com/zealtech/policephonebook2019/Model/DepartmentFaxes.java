package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;

public class DepartmentFaxes implements Serializable {
    String faxNo;
    String faxTo;

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getFaxTo() {
        return faxTo;
    }

    public void setFaxTo(String faxTo) {
        this.faxTo = faxTo;
    }
}
