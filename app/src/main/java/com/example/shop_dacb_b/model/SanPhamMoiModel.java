package com.example.shop_dacb_b.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SanPhamMoiModel {

//    @SerializedName()
    private List<SanPhamMoi> result;

    public SanPhamMoiModel(List<SanPhamMoi> result) {
        this.result = result;
    }

    public List<SanPhamMoi> getResult() {
        return result;
    }

    public void setResult(List<SanPhamMoi> result) {
        this.result = result;
    }
}
