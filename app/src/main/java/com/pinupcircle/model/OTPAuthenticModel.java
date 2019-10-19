package com.pinupcircle.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class OTPAuthenticModel {
    private Long mobileNum;
    public OTPAuthenticModel() {

    }

    public OTPAuthenticModel(Long mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Long getMobileNum() {
        return mobileNum;
    }
    public void setMobileNum(Long mobileNum) {
        this.mobileNum = mobileNum;
    }

    @NonNull
    @Override
    public String toString() {
        return "OTPAuthenticationModel: "+mobileNum;
    }
}
