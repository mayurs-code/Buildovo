package com.InfinitySolutions.buildovo;

public class ServicesCard {

    private int id;
    private String title;
    private String description;
    private String descriptionShort;
    private double price;
    private String button_string;
    private int image;

    public ServicesCard(int id, String title, String description, String descriptionShort, double price, String button_string, int image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.descriptionShort = descriptionShort;
        this.price = price;
        this.button_string = button_string;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public double getPrice() {
        return price;
    }

    public String getButton_string() {
        return button_string;
    }

    public int getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setButton_string(String button_string) {
        this.button_string = button_string;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
