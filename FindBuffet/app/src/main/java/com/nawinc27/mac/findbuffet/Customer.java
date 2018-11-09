package com.nawinc27.mac.findbuffet;

public class Customer {
    private String name;
    private String phone = "0932223322";

    public Customer(){}

    public Customer(String name){
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
