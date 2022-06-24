package com.example.shop_dacb_b.model;

import java.util.ArrayList;
import java.util.List;

public class Donhang {
    int id;
    boolean status;
    List<SanPhamDonHang> products;

    public Donhang() {
    }

    public Donhang(int id, boolean status, List<SanPhamDonHang> products) {
        this.id = id;
        this.status = status;
        this.products = products;
    }

    public void initProducts() {
        products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<SanPhamDonHang> getProducts() {
        return products;
    }

    public void setProducts(List<SanPhamDonHang> products) {
        this.products = products;
    }
}

