package com.nawinc27.mac.findbuffet.Model;

public class Buffet {
    private String name_th;
    private String name_en;
    private String address;
    private String telephone;
    private String time;
    private String lat;
    private String lng;
    private String[] image_url;

    public Buffet() {
    }

    public Buffet(String name_th, String name_en, String address, String telephone, String time, String lat, String lng, String[] image_url) {
        this.name_th = name_th;
        this.name_en = name_en;
        this.address = address;
        this.telephone = telephone;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.image_url = image_url;
    }

    public String[] getImage_url() {
        return image_url;
    }

    public void setImage_url(String[] image_url) {
        this.image_url = image_url;
    }

    public String getName_th() {
        return name_th;
    }

    public void setName_th(String name_th) {
        this.name_th = name_th;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


}
