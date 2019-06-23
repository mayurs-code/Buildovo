package com.InfinitySolutions.buildovo;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class StoreCard {


    private int id;
    private String title;
    private String description;
    private String descriptionShort;
    private String distance;
    private int image;
    private LatLng location;

    public StoreCard(int id, String title, String description, String descriptionShort, String distance, int image, LatLng location) {
        this.id = id;
        this.location=location;
        this.title = title;
        this.description = description;
        this.descriptionShort = descriptionShort;
        this.distance = distance;
        this.image = image;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
