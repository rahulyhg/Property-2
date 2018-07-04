package com.pivotalsoft.property.Response;

/**
 * Created by USER on 1/11/2018.
 */

public class FavItem {

    String title;
    String mobile;
    String price;
    String image;

    public FavItem(String title, String mobile, String price, String image) {
        this.title = title;
        this.mobile = mobile;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
