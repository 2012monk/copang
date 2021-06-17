package com.alconn.copang.exceptions;

public class LoginFailedException extends UnauthorizedException{
    private static final String code = "사용자 정보가 올바르지 않습니다";
    private static final String message = "login_failed";

    public LoginFailedException(){
        super(message,code);
    }
}
