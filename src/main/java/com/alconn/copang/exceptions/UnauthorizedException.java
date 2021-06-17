package com.alconn.copang.exceptions;

public class UnauthorizedException extends Throwable {
    private String message = "Unauthorized 권한이 없습니다 ";

    private String code;

    public UnauthorizedException(String code) {
        this.code = code;
    }

    public UnauthorizedException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public UnauthorizedException(){}

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
