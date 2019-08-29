package com.pinupcircle.model;

import java.util.ArrayList;
import java.util.List;

class UserPin {
    private String userPin;

    public UserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
}

class UserNewsPreference {
    private String userNewsPreference;

    public UserNewsPreference(String userNewsPreference) {
        this.userNewsPreference = userNewsPreference;
    }

    public String getUserNewsPreference() {
        return userNewsPreference;
    }
    public void setUserNewsPreference(String userNewsPreference) {
        this.userNewsPreference = userNewsPreference;
    }
}

class UserOTP {
    private String userOTP;
    public UserOTP(String userOTP) {
        this.userOTP = userOTP;
    }
    public String getUserOTP() {
        return userOTP;
    }

    public void setUserOTP(String userOTP) {
        this.userOTP = userOTP;
    }
}

class UserSocialInterest {
    private String userSocialInterest;

    public UserSocialInterest(String userSocialInterest) {
        this.userSocialInterest = userSocialInterest;
    }

    public String getUserSocialInterest() {
        return userSocialInterest;
    }

    public void setUserSocialInterest(String userSocialInterest) {
        this.userSocialInterest = userSocialInterest;
    }
}

class UserAddress {
    private String streetAddress, city, state, zip;

    public UserAddress(String streetAddress, String city, String state, String zip) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}

public class UserSubscriberModel {
    private String userName, userEmail, userPickRef, userCountryCode, bonusPoints;
    private Long userPhone;
    private Integer userAge;
    private List<UserPin> userPins;
    private List<UserNewsPreference> userNewsPreferences;
    private UserOTP otpEntity;
    private List<UserAddress> userAddresses;
    private List<UserSocialInterest> userSocialInterests;

    public UserSubscriberModel() {
        this.userPins = new ArrayList<>();
        this.userNewsPreferences = new ArrayList<>();
        this.userAddresses = new ArrayList<>();
        this.userSocialInterests = new ArrayList<>();
    }

    public List<UserPin> getUserPins() {
        return userPins;
    }

    public void addUserPins(String userPin) {
        UserPin userPinObj = new UserPin(userPin);
        this.userPins.add(userPinObj);
    }

    public List<UserNewsPreference> getUserNewsPreferences() {
        return userNewsPreferences;
    }

    public void addUserNewsPreferences(String newsPreference) {
        UserNewsPreference userNewsPreference = new UserNewsPreference(newsPreference);
        userNewsPreferences.add(userNewsPreference);
    }

    public UserOTP getOtpEntity() {
        return otpEntity;
    }

    public void setOtpEntity(String otp) {
        UserOTP userOTP = new UserOTP(otp);
        this.otpEntity = userOTP;
    }

    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void addUserAddresses(String streetAddress, String city, String state, String zip) {
        UserAddress address = new UserAddress(streetAddress, city, state, zip);
        userAddresses.add(address);
    }

    public List<UserSocialInterest> getUserSocialInterests() {
        return userSocialInterests;
    }

    public void addUserSocialInterests(String userSocialInterest) {
        UserSocialInterest socialInterest = new UserSocialInterest(userSocialInterest);
        userSocialInterests.add(socialInterest);
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

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }
    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = Integer.parseInt(userAge);
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

    public String getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(String bonusPoints) {
        this.bonusPoints = bonusPoints;
    }
}