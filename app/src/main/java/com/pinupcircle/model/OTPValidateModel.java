package com.pinupcircle.model;

import android.content.Context;

public class OTPValidateModel {
    Context context;
    private Integer userRegId;
    private String userOTP;

    public OTPValidateModel() {

    }

    public Integer getUserRegId() {
        return userRegId;
    }

    public void setUserRegId(Integer userRegId) {
        this.userRegId = userRegId;
    }

    public String getUserOTP() {
        return userOTP;
    }

    public void setUserOTP(String userOTP) {
        this.userOTP = userOTP;
    }
}
