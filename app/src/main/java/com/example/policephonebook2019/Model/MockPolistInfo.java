package com.example.policephonebook2019.Model;

public class MockPolistInfo {
    private String name;
    private String position;
    private String departure;

    public MockPolistInfo(String name, String position, String departure) {
        this.name = name;
        this.position = position;
        this.departure = departure;
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
}
