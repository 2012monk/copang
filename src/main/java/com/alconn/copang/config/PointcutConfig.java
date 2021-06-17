package com.alconn.copang.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


public class PointcutConfig {

    @Pointcut("within(com.alconn.copang.controller..*)")
    public void controllers(){}

    @Pointcut("@annotation(com.alconn.copang.annotations.CheckIdentity)")
    public void selfCheckAnnotations(){}

}
