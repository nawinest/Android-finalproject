package com.nawinc27.mac.findbuffet;

public class Customer {
    private String name;

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
}
