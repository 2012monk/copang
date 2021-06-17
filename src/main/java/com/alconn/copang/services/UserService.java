package com.alconn.copang.services;

import com.alconn.copang.common.AuthToken;
import com.alconn.copang.common.LoginToken;
import com.alconn.copang.common.WeUser;
import com.alconn.copang.repo.UserRepo;
import com.alconn.copang.provider.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider provider;

    private final UserRepo repo;

    public Optional<AuthToken> login(LoginToken loginToken) {

        WeUser user = repo.findByEmailAndPassword(loginToken.getEmail(), loginToken.getPassword());
        AuthToken token = null;
        if (user != null) {
            Optional<String> accessToken = provider.createAccessToken(user);

            if (accessToken.isPresent()) {
                token = new AuthToken(accessToken.get(), user);
            }
        }

        return Optional.ofNullable(token);
    }
}
