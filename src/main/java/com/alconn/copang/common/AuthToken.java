package com.alconn.copang.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthToken {

    public String access_token;

    public WeUser user;

    public AuthToken(String access_token, WeUser user) {
        this.access_token = access_token;
        this.user = user;
    }

    public AuthToken() {
    }
}
