package com.alconn.copang.common;

import lombok.Builder;
@Builder
public class ResponseMessage<T> {



    public String message;

    public T data;

    public String code;

    public ResponseMessage(String message, T data, String code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ResponseMessage() {
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }
}
