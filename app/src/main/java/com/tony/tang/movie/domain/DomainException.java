package com.tony.tang.movie.domain;

import javax.annotation.Nullable;

public class DomainException extends RuntimeException {

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int INVALID_SESSION_TOKEN = 209;
    public static final int NOT_FOUND = 404;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int UPGRADE_REQUIRED = 426;
    public static final int INTERNAL_SERVER_ERROR = 500;

    private int code;

    public DomainException(int code, @Nullable String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
