package com.example.smartwastebinmanagementsystem.models;

import java.util.Date;

/**
 * Model class representing a Smart Waste Bin.
 */
public class Bin {
    private String id;
    private String name;
    private String status; // Empty, Half-full, Full
    private double latitude;
    private double longitude;
    private long lastUpdated; // timestamp in milliseconds

    public Bin() {
        // Default constructor required for calls to DataSnapshot.getValue(Bin.class)
    }

    public Bin(String id, String name, String status, double latitude, double longitude, long lastUpdated) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
