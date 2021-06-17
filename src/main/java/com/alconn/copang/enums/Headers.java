package com.alconn.copang.enums;

public enum Headers {

    AUTHORIZATION("Authorization");

    String value;

    Headers(String val) {
        this.value = val;
    }

    public String val(){
        return value;
    }
}
