package com.alconn.copang.security;


import com.alconn.copang.common.WeUser;
import com.alconn.copang.models.Coffee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@Transactional
//@AutoConfigureRestDocs
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
public class CoffeeControllerAuthTest {

    static final String email = "test@test.com";
    static final String pass = "1234";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    String token;

    @DisplayName("Login Attempt and return access_token")
    @BeforeEach
    @Test
    void loginTest() throws Exception {
        WeUser user = WeUser.builder()
                .email(email)
                .password(pass)
                .build();
        String result =
                mvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(user)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$..access_token").exists())
                        .andReturn().getResponse().getContentAsString();

        JacksonJsonParser parser = new JacksonJsonParser();
        token = ((Map) parser.parseMap(result).get("data")).get("access_token").toString();
        log.info("access token id {}", token);
        assertNotNull(token);
    }

    @DisplayName("Login 실패시 login fail message")
    @Test
    void loginFail() throws Exception {
        String email = "-1";
        String pass = "1";
        WeUser user = WeUser.builder()
                .email(email)
                .password(pass)
                .build();
        mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Check token is valid")
    @Test
    void tokenCheck() throws Exception {
        mvc.perform(
                get("/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("CLIENT Role 이 있으면 통과")
    @Test
    void roleTest() throws Exception {
        mvc.perform(get("/api/coffee/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("Entity 의 유저정보와 같으면 Return")
    @Test
    void selfCheck() throws Exception {
        mvc.perform(get("/api/coffee/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("coffee1"))
                .andDo(print());
    }


    //    @Transactional
    @DisplayName("Access Token이 있으면 유저 정보와 함께 저장")
    @Test
    void entityListenerTest() throws Exception {
        Coffee coffee =
                Coffee.builder()
                        .name("coffee444")
                        .price(9999)
                        .build();

        String json = mvc.perform(
                post("/api/coffee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(coffee))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$..user").isNotEmpty())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        JacksonJsonParser parser = new JacksonJsonParser();
        String id = ((Map) parser.parseMap(json).get("data")).get("id").toString();
        log.info("access token id {}", id);

        mvc.perform(
                get("/api/coffee/" + id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        ).andDo(print())
                .andExpect(jsonPath("$..data.name").exists())
                .andExpect(status().isOk());
    }

    @DisplayName("Update 요청시 response")
    @Test
    void updateTest() throws Exception {
        Coffee coffee =
                Coffee.builder()
                        .name("coffee444")
                        .price(1001)
                        .build();

        mvc.perform(
                put("/api/coffee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(coffee))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @Transactional
    @DisplayName("Delete with id")
    @Test
    void deleteById() throws Exception {
        mvc.perform(
                delete("/api/coffee/1")
                        .header(HttpHeaders.AUTHORIZATION, "Beaer " + token)
        ).andExpect(status().isOk())
                .andDo(print());
    }
}
