package com.example.potholeadmin;

public class Data {
    String date;
    Double longitude;
    Double latitude;
    String status;

    public Data(String date, Double longitude, Double latitude, String status) {
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    public Data() {

    }

    public String getDate() {
        return date;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

