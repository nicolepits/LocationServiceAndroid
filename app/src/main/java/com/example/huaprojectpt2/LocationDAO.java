package com.example.huaprojectpt2;

public class LocationDAO {

    private int id;
    private double longitude;
    private double latitude;
    private long unix_timestamp;

    public LocationDAO(double longitude, double latitude, long unix_timestamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.unix_timestamp = unix_timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getUnix_timestamp() {
        return unix_timestamp;
    }

    public void setUnix_timestamp(long unix_timestamp) {
        this.unix_timestamp = unix_timestamp;
    }
}
