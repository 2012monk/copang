package com.alconn.copang.controller;

import com.alconn.copang.common.WeUser;
import com.alconn.copang.exceptions.LoginFailedException;
import com.alconn.copang.common.AuthToken;
import com.alconn.copang.common.LoginToken;
import com.alconn.copang.common.ResponseMessage;

import com.alconn.copang.repo.UserRepo;
import com.alconn.copang.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {


    private final UserService service;

    private final UserRepo repo;

    @PostMapping("/login")
    public ResponseMessage<AuthToken> login(@RequestBody LoginToken loginToken) throws LoginFailedException {
        Optional<AuthToken> token = service.login(loginToken);
        token.orElseThrow(LoginFailedException::new);
            return ResponseMessage.<AuthToken>builder()
                    .message("login_success")
                    .code("success")
                    .data(token.orElseThrow(LoginFailedException::new))
                    .build();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<WeUser> registerUser(@RequestBody WeUser user) {
        log.info("user info {}", user);
        return ResponseMessage.<WeUser>builder()
                .message("created")
                .data(repo.save(user))
                .build();
    }

}
