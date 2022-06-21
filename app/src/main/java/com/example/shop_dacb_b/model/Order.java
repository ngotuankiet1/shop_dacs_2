package com.example.shop_dacb_b.model;

import java.util.ArrayList;

public class Order {
    String nameUser;
    String phone;
    String address;
    String userId;
    ArrayList<Product> products;

    public Order() {}

    public Order(String nameUser, String phone, String address, String userId, ArrayList<Product> products) {
        this.nameUser = nameUser;
        this.phone = phone;
        this.address = address;
        this.userId = userId;
        this.products = products;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
