package com.alconn.copang.security;

import com.alconn.copang.common.WeUser;
import com.alconn.copang.enums.Role;
import com.alconn.copang.provider.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService {

    private final JwtTokenProvider provider;

    public Authentication getAuthentication(String token) {
        Optional<WeUser> user = provider.resolveUserFromToken(token);
        WeUser u = user.orElseThrow(() -> new UsernameNotFoundException("token does not exist"));
        return new UsernamePasswordAuthenticationToken(
                u, "", u.getAuthorities()
        );
    }

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = loadUserByToken(token);
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    public UserDetails loadUserByToken(String token) {
        Optional<WeUser> user = provider.resolveUserFromToken(token);
        user.orElseGet(() -> WeUser.builder().role(Role.GUEST).build());
        return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(user.get().getRole().key())
                .build();

    }
}
