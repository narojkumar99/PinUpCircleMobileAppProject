package com.pinupcircle.model;

import android.content.Context;
import android.content.SharedPreferences;

public class RegisteredUserModel {
    Context context;
    SharedPreferences sharedPreferences;
    private Integer userRegId;
    private String userName;

    public RegisteredUserModel(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("User Details",Context.MODE_PRIVATE);
    }

    public Integer getUserRegId() {
        userRegId=sharedPreferences.getInt("regId",1);
        return userRegId;
    }

    public void setUserRegId(Integer userRegId) {
        this.userRegId = userRegId;
        sharedPreferences.edit().putInt("regId",userRegId);
        sharedPreferences.edit().apply();
    }

    public String getUserName() {
        userName=sharedPreferences.getString("userName","None");
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        sharedPreferences.edit().putString("userName",userName);
        sharedPreferences.edit().apply();
    }
    public void logOut(){
        sharedPreferences.edit().clear().commit();
    }
}
