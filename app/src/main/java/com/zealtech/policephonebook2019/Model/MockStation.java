package com.zealtech.policephonebook2019.Model;

public class MockStation {
    private String station, subStation, stationTel, stationTel2, stationAddress,
            stationLat, stationLon;

    public MockStation(String station, String subStation, String stationTel, String stationTel2, String stationAddress, String stationLat, String stationLon) {
        this.station = station;
        this.subStation = subStation;
        this.stationTel = stationTel;
        this.stationTel2 = stationTel2;
        this.stationAddress = stationAddress;
        this.stationLat = stationLat;
        this.stationLon = stationLon;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getSubStation() {
        return subStation;
    }

    public void setSubStation(String subStation) {
        this.subStation = subStation;
    }

    public String getStationTel() {
        return stationTel;
    }

    public void setStationTel(String stationTel) {
        this.stationTel = stationTel;
    }

    public String getStationTel2() {
        return stationTel2;
    }

    public void setStationTel2(String stationTel2) {
        this.stationTel2 = stationTel2;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationLat() {
        return stationLat;
    }

    public void setStationLat(String stationLat) {
        this.stationLat = stationLat;
    }

    public String getStationLon() {
        return stationLon;
    }

    public void setStationLon(String stationLon) {
        this.stationLon = stationLon;
    }
}
