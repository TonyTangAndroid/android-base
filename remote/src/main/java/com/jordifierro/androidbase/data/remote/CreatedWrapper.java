package com.jordifierro.androidbase.data.remote;

class CreatedWrapper {

    private String createdAt;
    private String objectId;
    private String sessionToken;

    public CreatedWrapper(String createdAt, String objectId, String sessionToken) {
        this.createdAt = createdAt;
        this.objectId = objectId;
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
