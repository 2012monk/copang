package com.alconn.copang.provider.jwt;

import com.alconn.copang.common.WeUser;
import com.alconn.copang.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider provider;

    @Value("${spring.jwt.secret}")
    String keyString;

    @Test
    void name()  {

        Optional<String> token = provider.createAccessToken(WeUser.builder().email("123").password("44321").build());
        log.info(String.valueOf(token.isPresent()));
        if (token.isPresent()) {
            log.warn("token{}", token.get());

            SecretKey key = new SecretKeySpec(Base64.encodeBase64(keyString.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256.getJcaName());

            String iss = "alconn.co";

            Claims claims = Jwts.parserBuilder()
//                    .requireIssuer(iss)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.get())
                    .getBody();

            log.info("cliams iss : {}", claims.getIssuer());
            Optional<WeUser> user = provider.resolveUserFromToken(token.get());
            user.ifPresent(System.out::println);
            log.warn("user : {}", user.get());
        }
    }


    @Test
    void jwt() {
        String keyString = "secretkeyis321$@!$!@$!@$!@$$";
        SecretKey key = new SecretKeySpec(Base64.encodeBase64(keyString.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256.getJcaName());

        String iss = "alconn.co";
        Date now = new Date();
        Date expDate = new Date(now.getTime() + 60 * 60 * 60);

        String token = Jwts.builder()
                .signWith(key)
                .setIssuer(iss)
                .setIssuedAt(now)
                .setExpiration(expDate)
                .compact();
        assertNotNull(token);

        Claims claims = Jwts.parserBuilder()
                .requireIssuer(iss)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertNotNull(claims);
        log.warn(claims.getIssuer());
    }
}