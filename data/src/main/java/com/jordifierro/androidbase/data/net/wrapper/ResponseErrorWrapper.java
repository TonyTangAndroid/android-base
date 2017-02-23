package com.jordifierro.androidbase.data.net.wrapper;

public class ResponseErrorWrapper {


    private String error;
    private int code;

    public ResponseErrorWrapper(String error, int code) {
        this.error = error;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
