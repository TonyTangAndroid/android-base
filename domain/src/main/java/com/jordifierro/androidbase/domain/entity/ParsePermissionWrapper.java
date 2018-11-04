package com.jordifierro.androidbase.domain.entity;

import java.util.ArrayList;
import java.util.Objects;

public class ParsePermissionWrapper {

    private ArrayList<ParsePermission> permissionArrayList;

    public ArrayList<ParsePermission> getPermissionArrayList() {
        return permissionArrayList;
    }

    public void setPermissionArrayList(ArrayList<ParsePermission> permissionArrayList) {
        this.permissionArrayList = permissionArrayList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsePermissionWrapper that = (ParsePermissionWrapper) o;
        return Objects.equals(permissionArrayList, that.permissionArrayList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(permissionArrayList);
    }
}