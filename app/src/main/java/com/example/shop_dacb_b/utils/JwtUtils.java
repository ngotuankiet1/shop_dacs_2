package com.example.shop_dacb_b.utils;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class JwtUtils {
//    private static final String TAG = "JwtUtils";

    public static void decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));

            String bodyJson = getJson(split[1]);
            JSONObject bodyObj = new JSONObject(bodyJson);

            Log.d("JWT_DECODED", "Body: " + bodyObj.toString());

            GlobalVar.getInstance().emailAddress = bodyObj.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress");
            GlobalVar.getInstance().uid = bodyObj.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
            GlobalVar.getInstance().name = bodyObj.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
            GlobalVar.getInstance().role = bodyObj.getString("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");

        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
