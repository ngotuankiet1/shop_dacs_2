package com.example.shop_dacb_b.utils;

public class GlobalVar {
    public static GlobalVar mInstance = null;

    public String token;
    public String emailAddress;
    public String uid;
    public String name;
    public String role;

    protected GlobalVar() {}

    public static synchronized GlobalVar getInstance() {
        if (mInstance == null) {
            mInstance = new GlobalVar();
        }
        return mInstance;
    }
}
