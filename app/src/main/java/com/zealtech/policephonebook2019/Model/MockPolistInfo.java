package com.zealtech.policephonebook2019.Model;

public class MockPolistInfo {
    private String name;
    private String position;
    private String departure;
    private String position1;
    private String positoin2;

    public MockPolistInfo(String name, String position, String departure, String position1, String positoin2) {
        this.name = name;
        this.position = position;
        this.departure = departure;
        this.position1 = position1;
        this.positoin2 = positoin2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getPositoin2() {
        return positoin2;
    }

    public void setPositoin2(String positoin2) {
        this.positoin2 = positoin2;
    }
}
