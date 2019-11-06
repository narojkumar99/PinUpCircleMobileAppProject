package com.pinupcircle.model;

public class UserSubscriberModel {


    private Integer userRegId;
    private String userName;
    private String userNickName;
    private String userPicRef;
    private String userPicName;

    public Integer getUserRegId() {
        return userRegId;
    }

    public void setUserRegId(Integer userRegId) {
        this.userRegId = userRegId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPicRef() {
        return userPicRef;
    }

    public void setUserPicRef(String userPicRef) {
        this.userPicRef = userPicRef;
    }

    public String getUserPicName() {
        return userPicName;
    }

    public void setUserPicName(String userPicName) {
        this.userPicName = userPicName;
    }
}
