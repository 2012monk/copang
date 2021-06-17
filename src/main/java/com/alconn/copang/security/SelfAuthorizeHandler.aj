package com.alconn.copang.security;

import com.alconn.copang.models.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;

@Slf4j
@Component
@Aspect
public class SelfAuthorizeHandler {



    @Around("com.alconn.copang.config.PointcutConfig.selfCheckAnnotations()")
    public Object selfCheck(ProceedingJoinPoint pjp) throws Throwable {
        log.warn("================================authorization unit working!=============================");
        return pjp.proceed(pjp.getArgs());
    }

    @PostLoad
    public void checkCoffee(Coffee coffee){
        log.warn("==========================post load workings======================================");
        log.warn("coffee loaded {}", coffee.getUser().getUid());
        log.warn("user class {}",coffee.getUser().getClass().getSimpleName());
    }
}
