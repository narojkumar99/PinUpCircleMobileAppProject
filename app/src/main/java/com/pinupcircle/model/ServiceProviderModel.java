package com.pinupcircle.model;

import java.util.ArrayList;

//{
//        "userRegId":1211,
//        "userName":"Amit Saigal",
//        "shopName":"Ma tara seweets distributor",
//        "serviceSpecification":"Consumer Service",
//        "businessType":"Provider",
//        "businessAddress":{
//        "street_address": "P101 Motijheel Avenue",
//        "city": "Kolkata",
//        "state": "WB"
//
//        },
//        "businessPhone":915678906543,
//        "businessEmail":"abc@abc.com",
//        "businessPins":["700074", "700076"],
//        "userExpertise":["Crafting","Designing"],
//        "userWorkView":["abc.jpeg","xyz.png"]
//}

class BusinessAddress{
    private String streetAddress,city,state;

    public BusinessAddress(String streetAddress, String city, String state) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
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
}
public class ServiceProviderModel {
    private String userName,shopName,serviceSpecification,businessType,businessEmail;
    private ArrayList<String> businessPins,userExpertise,userWorkView;
    private BusinessAddress businessAddress;
    private Long businessPhone;
    private Integer userRegId;

    public ServiceProviderModel() {
        businessPins=new ArrayList<>();
        userExpertise=new ArrayList<>();
        userWorkView=new ArrayList<>();
    }

    public ArrayList<String> getBusinessPins() {
        return businessPins;
    }

    public void addBusinessPins(String pin) {
        businessPins.add(pin);
    }

    public ArrayList<String> getUserExpertise() {
        return userExpertise;
    }

    public void addUserExpertise(String expertise) {
        userExpertise.add(expertise);
    }

    public ArrayList<String> getUserWorkView() {
        return userWorkView;
    }

    public void addUserWorkView(String workView) {
        userWorkView.add(workView);
    }

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getServiceSpecification() {
        return serviceSpecification;
    }

    public void setServiceSpecification(String serviceSpecification) {
        this.serviceSpecification = serviceSpecification;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public BusinessAddress getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String streetAddress,String city,String state) {
        BusinessAddress businessAddress=new BusinessAddress(streetAddress,city,state);
        this.businessAddress=businessAddress;
    }

    public Long getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(Long businessPhone) {
        this.businessPhone = businessPhone;
    }
}
