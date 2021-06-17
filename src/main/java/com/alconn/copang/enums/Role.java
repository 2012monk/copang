package com.alconn.copang.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{

    GUEST("ROLE_GUEST", "guest"),
    CLIENT("ROLE_CLIENT", "client"),
    SELLER("ROLE_SELLER", "seller"),
    ADMIN("ROLE_ADMIN", "admin");

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public final String key;
    public final String title;

    public String key() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthority() {
        return null;
    }


}
