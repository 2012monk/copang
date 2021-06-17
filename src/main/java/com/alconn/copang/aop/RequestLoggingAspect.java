package com.alconn.copang.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Component
@Aspect
public class RequestLoggingAspect {

    @Around("com.alconn.copang.config.PointcutConfig.controllers()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            sb.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }
        log.info("global Logger working");
        log.info("header \n{}", sb.toString());

        return joinPoint.proceed(joinPoint.getArgs());
    }


}
