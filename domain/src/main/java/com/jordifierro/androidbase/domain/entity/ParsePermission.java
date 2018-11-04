package com.jordifierro.androidbase.domain.entity;

import java.util.Objects;

public class ParsePermission {

    private boolean read;
    private boolean write;
    private String objectId;


    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsePermission that = (ParsePermission) o;
        return read == that.read &&
                write == that.write &&
                Objects.equals(objectId, that.objectId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(read, write, objectId);
    }
}