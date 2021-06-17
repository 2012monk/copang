package com.alconn.copang.controller;

import com.alconn.copang.common.ResponseMessage;
import com.alconn.copang.common.WeUser;
import com.alconn.copang.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo repo;


    @GetMapping("/{id}")
    public ResponseMessage<WeUser> getUserInfo(@PathVariable Long id) {
        return ResponseMessage.<WeUser>builder()
                .message("success")
                .data(repo.getById(id))
                .build();
    }

    @GetMapping
    public ResponseMessage<WeUser> getUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
//        WeUser user = (WeUser) authentication.getPrincipal();
//        Long id = user.getUid();
        log.info(name);
        return ResponseMessage.<WeUser>builder()
                .message("success")
                .data(repo.findByEmail(name).orElseThrow(Exception::new))
                .build();
    }


}
