package com.pinupcircle.model;

import java.util.ArrayList;
import java.util.List;

public class CommunityPostTextModel {
    private Integer userId;
    private String postText;
    private List<String> communityPins;

    public CommunityPostTextModel() {
        communityPins=new ArrayList<>();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }


    public List<String> getCommunityPins() {
        return communityPins;
    }

    public void addCommunityPin(String communityPin) {
        this.communityPins.add(communityPin);
    }

}
