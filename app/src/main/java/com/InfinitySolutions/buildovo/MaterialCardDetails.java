package com.InfinitySolutions.buildovo;

public class MaterialCardDetails {

    private int image;

    private String title;
    private String tokenID;
    private String description;
    private String price;
    private String unitSize;
    private String quantity;
    private String availability;

    public MaterialCardDetails(int image, String title, String tokenID, String description, String price, String unitSize, String quantity, String availability, String dealers) {
        this.image = image;
        this.title = title;
        this.tokenID = tokenID;
        this.description = description;
        this.price = price;
        this.unitSize = unitSize;
        this.quantity = quantity;
        this.availability = availability;
        this.dealers = dealers;
    }

    private String dealers;

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

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDealers() {
        return dealers;
    }

    public void setDealers(String dealers) {
        this.dealers = dealers;
    }
}
