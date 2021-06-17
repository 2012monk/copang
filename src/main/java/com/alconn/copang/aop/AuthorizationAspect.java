package com.alconn.copang.aop;


import com.alconn.copang.enums.Headers;
import com.alconn.copang.provider.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
@Aspect
public class AuthorizationAspect {

    private final JwtTokenProvider tokenProvider;

    @Pointcut("within(com.alconn.copang.controller..*)")
    public void onRequest(){}

    @Around("com.alconn.copang.aop.AuthorizationAspect.onRequest()")
    public Object checkAuthority(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("authorization unit working...");

//        Optional<String> token = getToken(request);
//
//        if (token.isPresent()) {
//            tokenProvider.validateAccessToken(token.get());
//        }

        return pjp.proceed(pjp.getArgs());
    }


    private Optional<String> getToken(HttpServletRequest request) {
        String token = request.getHeader(Headers.AUTHORIZATION.name());

        return Optional.ofNullable(token);
    }
}
