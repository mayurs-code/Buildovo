package com.InfinitySolutions.buildovo;

public class Advertize_data_class {

    private int image;
    private String description;

    public int getImage() {
        return image;
    }

    public Advertize_data_class(int image, String description) {
        this.image = image;
        this.description = description;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
