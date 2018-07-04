package com.pivotalsoft.property.Response;

/**
 * Created by USER on 1/12/2018.
 */

public class SellerLeadsItem {
    String name;
    String mobile;
    String email;
    String image;

    public SellerLeadsItem(String name, String mobile, String email, String image) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
