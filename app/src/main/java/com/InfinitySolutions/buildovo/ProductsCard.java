package com.InfinitySolutions.buildovo;

import java.util.ArrayList;

public class ProductsCard {


    private int image;
    private String title;
    private String description;
    private String units;
    private Boolean availability;
    private double pricePerUnit;
    private ArrayList<ProductsCard> peoples;
    private double charge;

    public ProductsCard(int image, String title, String description, String units, Boolean availability, double pricePerUnit) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.units = units;
        this.availability = availability;
        this.pricePerUnit = pricePerUnit;
    }

    public ProductsCard(ArrayList<ProductsCard> peoples,double charge) {
        this.peoples = peoples;
        this.charge=charge;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public ArrayList<ProductsCard> getPeoples() {
        return peoples;
    }

    public void setPeoples(ArrayList<ProductsCard> peoples) {
        this.peoples = peoples;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
