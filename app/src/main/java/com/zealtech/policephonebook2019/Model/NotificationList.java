package com.zealtech.policephonebook2019.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationList implements Serializable {
    ArrayList<Notification> content;

    public ArrayList<Notification> getContent() {
        return content;
    }

    public void setContent(ArrayList<Notification> content) {
        this.content = content;
    }
}
