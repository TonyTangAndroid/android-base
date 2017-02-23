package com.gson.test;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ParseErrorWrapper {


    //{"code":101,"error":"Invalid username/password."}
    //
    @SerializedName("error")
    private Object errorContent;
    private int code;

    public void setErrorBean(Object error) {
        this.errorContent = error;
    }

    public String getErrorMessage() {
        final boolean errorType = errorContent instanceof ErrorBean;
        if (errorType) {
            return ((ErrorBean) errorContent).getMessage();
        } else {
            return errorContent.toString();
        }
    }

    public int getCode() {
        return errorContent instanceof ErrorBean ? ((ErrorBean) errorContent).getCode() : code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
