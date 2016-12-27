package com.gson.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParseNote {

    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String objectId;
    private ParsePermissionWrapper ACL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParsePermissionWrapper getACL() {
        return ACL;
    }

    public void setACL(ParsePermissionWrapper ACL) {
        this.ACL = ACL;
    }

    @Override
    public String toString() {
        final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter());
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}