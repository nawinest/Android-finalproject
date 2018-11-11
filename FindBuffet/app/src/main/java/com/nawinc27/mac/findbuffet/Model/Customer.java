package com.nawinc27.mac.findbuffet.Model;

public class Customer {

    private String name;
    private String email;
    private String phone = "กรุณาตั้งค่าเบอร์โทรของท่าน";
    private String imgProfileUrl;

    public Customer(String name, String email, String phone, String imgProfileUrl) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imgProfileUrl = imgProfileUrl;
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgProfileUrl() {
        return imgProfileUrl;
    }

    public void setImgProfileUrl(String imgProfileUrl) {
        this.imgProfileUrl = imgProfileUrl;
    }
}
