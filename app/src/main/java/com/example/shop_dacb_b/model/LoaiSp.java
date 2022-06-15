package com.example.shop_dacb_b.model;

public class LoaiSp {
    int id;
    String tenmenu;
    String hinhanh;

    public LoaiSp(String tenmenu, String hinhanh) {
        this.tenmenu = tenmenu;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmenu() {
        return tenmenu;
    }

    public void setTenmenu(String tenmenu) {
        this.tenmenu = tenmenu;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
