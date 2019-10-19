package com.pinupcircle.model;

import java.util.ArrayList;
import java.util.List;

class UserPin {
    private Integer userPin;

    public UserPin(Integer userPin) {
        this.userPin = userPin;
    }

    public Integer getUserPin() {
        return userPin;
    }

    public void setUserPin(Integer userPin) {
        this.userPin = userPin;
    }
}

public class UserSubscriberModel {
    private String userName;
    private String userEmail;
    private String userPickRef;
    private String userCountryCode;
    private String userPicName;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    private String userNickName;
    private Long userPhone;
    private List<UserPin> userPins;

    public String getUserPicName() {
        return userPicName;
    }

    public void setUserPicName(String userPicName) {
        this.userPicName = userPicName;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public UserSubscriberModel() {
        this.userPins = new ArrayList<>();
    }

    public List<UserPin> getUserPins() {
        return userPins;
    }

    public void addUserPins(Integer userPin) {
        UserPin userPinObj = new UserPin(userPin);
        this.userPins.add(userPinObj);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPickRef() {
        return userPickRef;
    }

    public void setUserPickRef(String userPickRef) {
        this.userPickRef = userPickRef;
    }

    public String getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(String userCountryCode) {
        this.userCountryCode = userCountryCode;
    }
}