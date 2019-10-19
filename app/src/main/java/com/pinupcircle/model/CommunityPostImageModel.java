package com.pinupcircle.model;

import android.media.Image;

public class CommunityPostImageModel {
    private Integer postId;
    private String base64Media;
    private String fileName;
    private Integer userId;
    private String mediaType;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        return "CommunityPostImageModel{" +
                "postId=" + postId +
                ", base64Media='" + base64Media + '\'' +
                ", fileName='" + fileName + '\'' +
                ", userId=" + userId +
                ", mediaType='" + mediaType + '\'' +
                '}';
    }
}
