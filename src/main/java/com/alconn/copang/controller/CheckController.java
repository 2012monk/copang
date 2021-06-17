package com.alconn.copang.controller;

import com.alconn.copang.enums.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckController {

    @Secured("ROLE_CLIENT")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String bye() {
        return "bye";
    }
}
