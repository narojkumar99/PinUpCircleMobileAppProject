package com.pinupcircle.model;

import java.util.ArrayList;
import java.util.List;

public class CommunityPostTextModel {
    private Integer userId;
    private String postText;
    private List<String> communityPins;
    private String base64Media;
    private String fileName;
    private String mediaType;

    public void setCommunityPins(List<String> communityPins) {
        this.communityPins = communityPins;
    }

    public String getBase64Media() {
        return base64Media;
    }

    public void setBase64Media(String base64Media) {
        this.base64Media = base64Media;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

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
