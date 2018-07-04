package com.pivotalsoft.property.Response;

/**
 * Created by USER on 1/9/2018.
 */

public class BuyerSearchItem {
    String cost;
    String sqft;
    String address;
    String image;

    public BuyerSearchItem(String cost, String sqft, String address, String image) {
        this.cost = cost;
        this.sqft = sqft;
        this.address = address;
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSqft() {
        return sqft;
    }

    public void setSqft(String sqft) {
        this.sqft = sqft;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
