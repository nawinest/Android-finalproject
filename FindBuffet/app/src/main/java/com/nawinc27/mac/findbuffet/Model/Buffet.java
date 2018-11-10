package com.nawinc27.mac.findbuffet.Model;

public class Buffet {
    private String name_th;
    private String name_en;
    private String descrition;
    private String contact;
    private String time;
    private String lat;
    private String lng;
    private int type_buffet;

    public Buffet() {
    }

    public Buffet(String name_th, String name_en, String descrition, String contact, String time, String lat, String lng, int type_buffet) {
        this.name_th = name_th;
        this.name_en = name_en;
        this.descrition = descrition;
        this.contact = contact;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.type_buffet = type_buffet;
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

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public int getType_buffet() {
        return type_buffet;
    }

    public void setType_buffet(int type_buffet) {
        this.type_buffet = type_buffet;
    }
}
