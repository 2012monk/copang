package com.alconn.copang.security;


import com.alconn.copang.common.WeUser;
import com.alconn.copang.provider.jwt.JwtTokenProvider;
import com.alconn.copang.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JwtTokenProvider provider;

    private final UserRepo repo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<WeUser> optionalUser = repo.findByEmail(username);
//        List<AuthorityImpl> list = Collections.singletonList(new AuthorityImpl(user.getRole()));
//        if (optionalUser.isPresent()) {
//            WeUser user = optionalUser.get();
//            return new User(user.getEmail(), user.getPassword(), list);
//        }
//
//        return null;
//    }


    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Optional<WeUser> user = provider.resolveUserFromToken(token);
        user.orElseThrow(() -> new UsernameNotFoundException("invalid token"));
        return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(user.get().getRole().name())
                .build();

    }
}
