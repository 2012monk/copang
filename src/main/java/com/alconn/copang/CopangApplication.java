package com.alconn.copang;

import com.alconn.copang.config.MvcConfig;
import com.alconn.copang.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@SpringBootApplication
public class CopangApplication {

    public static void main(String[] args) {
        SpringApplication.run(CopangApplication.class, args);
    }

}
