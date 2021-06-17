package com.alconn.copang.util;

import com.alconn.copang.common.WeUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ListenerUtils {

    public static Optional<String> getUserEmailFromContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeUser user = (WeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(user.getEmail());
    }
}
