package com.alconn.copang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/name/{name}")
    public String myNameIs(@PathVariable String name) {
        return "my name is "+name;
    }

    @GetMapping("/")
    public String home() {
        return "hello world!";
    }

    @GetMapping("/1")
    public int one(){
        return 1;
    }

    @PostMapping("/")
    public String post(){
        return "ok";
    }
}
